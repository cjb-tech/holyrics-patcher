package transformers;

import javassist.CannotCompileException;
import javassist.CtMethod;

import java.util.function.Consumer;

public class CheckLibRemovalTransformer implements Consumer<CtMethod> {

    @Override
    public void accept(CtMethod ctMethod) {
        try {
            ctMethod.setBody("{return true;}");
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
    }

}
