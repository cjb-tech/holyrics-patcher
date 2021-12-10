package dto;

import javassist.CtMethod;

import java.util.function.Consumer;

public class MethodPatchData {

    private final String methodName;
    private final String[] methodParameters;
    private final Consumer<CtMethod> methodHandler;

    public MethodPatchData(String methodName, String[] methodParameters, Consumer<CtMethod> methodHandler) {
        this.methodName = methodName;
        this.methodParameters = methodParameters;
        this.methodHandler = methodHandler;
    }

    public String getMethodName() {
        return methodName;
    }

    public String[] getMethodParameters() {
        return methodParameters;
    }

    public Consumer<CtMethod> getMethodHandler() {
        return methodHandler;
    }
}
