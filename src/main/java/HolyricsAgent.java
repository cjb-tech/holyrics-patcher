import dto.MethodPatchData;
import interactor.PatchInteractor;
import interactor.RemoveCheckLibPatchInteractor;
import interactor.RemoveTitleAndArtistSlidePatchInteractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;


public class HolyricsAgent {

    private static final Logger LOGGER = LoggerFactory.getLogger(HolyricsAgent.class);

    public static List<PatchInteractor> patchInteractors  = new ArrayList<PatchInteractor>(){{
        add(new RemoveTitleAndArtistSlidePatchInteractor());
        add(new RemoveCheckLibPatchInteractor());
    }};

    public static void premain(
            String agentArgs, Instrumentation inst) {
        LOGGER.info("[Agent] In premain method");
        transformClass(inst);
    }

    private static void transformClass(Instrumentation instrumentation) {
        for(PatchInteractor patchInteractor : patchInteractors){
            String className = patchInteractor.getClassName();
            List<MethodPatchData> methodPatchDataList = patchInteractor.getMethodPatchDataList();
            Class<?> targetCls = null;
            ClassLoader targetClassLoader = null;
            try {
                targetCls = Class.forName(className);
                targetClassLoader = targetCls.getClassLoader();

                transform(instrumentation, targetCls, targetClassLoader, className, methodPatchDataList);
                continue;
            } catch (Exception ex) {
                LOGGER.error("Class [{}] not found with Class.forName", className);
            }
            boolean foundedWithAllClassLoadedSearch = false;
            for(Class<?> clazz: instrumentation.getAllLoadedClasses()) {
                if(clazz.getName().equals(className)) {
                    targetCls = clazz;
                    targetClassLoader = targetCls.getClassLoader();
                    transform(instrumentation, targetCls, targetClassLoader, className, methodPatchDataList);
                    foundedWithAllClassLoadedSearch = true;
                }
            }
            if(!foundedWithAllClassLoadedSearch){
                throw new RuntimeException(
                        "Failed to find class [" + className + "]");
            }

            try {
                Class<?> targetClazz = Class.forName(patchInteractor.getClassName());
                transform(instrumentation,
                        targetClazz,
                        targetClazz.getClassLoader(),
                        patchInteractor.getClassName(),
                        patchInteractor.getMethodPatchDataList()
                );
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    private static void transform(Instrumentation instrumentation,
                                  Class<?> targetCls,
                                  ClassLoader targetClassLoader,
                                  String className,
                                  List<MethodPatchData> methodPatchDataList) {
        GeneralMethodTransformer dt = new GeneralMethodTransformer(className, methodPatchDataList, targetClassLoader);
        instrumentation.addTransformer(dt, true);
        try {
            instrumentation.retransformClasses(targetCls);
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Transform failed for: [" + targetCls.getName() + "]", ex);
        }
    }

    public static void agentmain(
            String agentArgs, Instrumentation inst) {
        LOGGER.info("[Agent] In agentmain method");
        transformClass(inst);

    }

}
