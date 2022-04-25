package transformers;

import javassist.CannotCompileException;
import javassist.CtMethod;
import java.util.function.Consumer;

public class SlideAndTitleRemovalTransformer implements Consumer<CtMethod> {

    @Override
    public void accept(CtMethod ctMethod) {
        try {
            ctMethod.setBody("{" +
                    "       return com.limagiran.holyrics.model.Paragraph.create().setSlideIndex(0).setSlideType(com.limagiran.holyrics.view.slide.EnumSlideType.TITLE).setSource(com.limagiran.holyrics.model.Music.create(0L));" +
                    "}");
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
    }

}
