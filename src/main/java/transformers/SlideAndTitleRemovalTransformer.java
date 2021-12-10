package transformers;

import javassist.CannotCompileException;
import javassist.CtMethod;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class SlideAndTitleRemovalTransformer implements Consumer<CtMethod> {

    private static final Logger logger = LoggerFactory.getLogger(SlideAndTitleRemovalTransformer.class);

    @Override
    public void accept(CtMethod ctMethod) {
        try {
            ctMethod.setBody("{" +
                    "       System.out.println(\"Hello, here we are\");" +
                    "       return com.limagiran.holyrics.model.Paragraph.create().setSlideIndex(0).setSlideType(com.limagiran.holyrics.view.slide.EnumSlideType.TITLE).setSource(com.limagiran.holyrics.model.Music.create(0L));" +
                    "}");
           /* ctMethod.instrument(new ExprEditor() {
                private int i = 0;
                public void edit(MethodCall m) throws CannotCompileException {
                    String regexPattern = ".*Exception";
                    logger.info(m.getFileName());
                    logger.info(String.valueOf(m.getLineNumber()));
                    logger.info(m.getClassName());
                    logger.info(m.getSignature());
                    logger.info(m.getMethodName());
                    i++;
                    if (m.getClassName().equals("java.util.List") && i == 3) {
                        m.replace(";");
                    }
                }

                ;
            });*/
            /*ctMethod.setBody(
                    "{" +
                    "        List<Presentation> list = new ArrayList();\n" +
                    "        Long[] backgrounds = $args[0].getSettings().getBackground();\n" +
                    "        List<Paragraph> p = $args[0].getParagraphsToPresentation();\n" +
                    "        for (int i = 0; i < p.size(); i++) {\n" +
                    "            Paragraph text = (Paragraph) p.get(i);\n" +
                    "            MusicUtils.setupToPresentation(text);\n" +
                    "            list.add(Presentation.create(text, MusicUtils.getCustomBackground(text, backgrounds, $args[1])));\n" +
                    "        }\n" +
                    "        return list;\n" +
                    "}");*/
           /* ctMethod.insertAfter("{" +
                    "   $_.remove(0);" +
                    "   System.out.println(\"Hahahhahaha, I'm here\");" +
                    "}");*/
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
    }
}
