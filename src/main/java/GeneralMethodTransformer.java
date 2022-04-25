import dto.MethodPatchData;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class GeneralMethodTransformer implements ClassFileTransformer {

    private final String targetClassName;
    private final List<MethodPatchData> methodPatchDataList;
    private final ClassLoader targetClassLoader;
    private static final Logger LOGGER = LoggerFactory.getLogger(HolyricsAgent.class);

    public GeneralMethodTransformer(String targetClassName, List<MethodPatchData> methodPatchDataList, ClassLoader classLoader) {
        this.targetClassName = targetClassName;
        this.methodPatchDataList = methodPatchDataList;
        this.targetClassLoader = classLoader;
    }


    @Override
    public byte[] transform(
            ClassLoader loader,
            String className,
            Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain,
            byte[] classfileBuffer) {
        byte[] byteCode = classfileBuffer;
        String finalTargetClassName = this.targetClassName
                .replaceAll("\\.", "/");
        if (!className.equals(finalTargetClassName)) {
            return byteCode;
        }
        if (loader.equals(targetClassLoader)) {
            try {
                ClassPool cp = ClassPool.getDefault();
                CtClass cc = cp.get(targetClassName);
                for (MethodPatchData methodPatchData : methodPatchDataList) {
                    String targetMethodName = methodPatchData.getMethodName();
                    Consumer<CtMethod> modifyClassMethodFunction = methodPatchData.getMethodHandler();
                    LOGGER.info("[Agent] Transforming class " + targetClassName + "."  + targetMethodName);
                    CtMethod m = Arrays.stream(cc.getDeclaredMethods()).filter(el->{
                        try{
                            return el.getName().equals(targetMethodName);
                        }catch (Exception e){
                            return false;
                        }
                    }).findFirst().orElse(null);
                    if(m == null){
                        LOGGER.info("Can't find method {} in class {}", targetMethodName, targetClassName);
                        LOGGER.info(Arrays.stream(cc.getMethods()).map(CtMethod::getLongName).collect(Collectors.joining(System.lineSeparator())));
                        LOGGER.info(Arrays.stream(cc.getDeclaredMethods()).map(CtMethod::getLongName).collect(Collectors.joining(System.lineSeparator())));
                        return byteCode;
                    }
                    modifyClassMethodFunction.accept(m);
                }
                byteCode = cc.toBytecode();
                cc.detach();
            } catch (Throwable e) {
                LOGGER.error("Exception", e);
            }
        }
        return byteCode;
    }

}