package transformers;

import javassist.CannotCompileException;
import javassist.CtMethod;

import java.util.function.Consumer;

public class RemoveFirstParagraphTransformer implements Consumer<CtMethod> {
    @Override
    public void accept(CtMethod ctMethod) {
        try {
            ctMethod.insertAfter("$_.remove(0);");
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
    }
}
