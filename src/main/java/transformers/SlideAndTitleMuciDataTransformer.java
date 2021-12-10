package transformers;

import javassist.CannotCompileException;
import javassist.CtMethod;

import java.util.function.Consumer;

public class SlideAndTitleMuciDataTransformer implements Consumer<CtMethod> {

    @Override
    public void accept(CtMethod ctMethod) {
        try {
            ctMethod.insertAfter("{System.out.println($_.size());$_.remove(0);}");
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
    }

}
