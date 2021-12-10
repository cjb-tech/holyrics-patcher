package interactor;

import dto.MethodPatchData;
import transformers.RemoveFirstParagraphTransformer;

import java.util.ArrayList;
import java.util.List;

public class RemoveFirstSlideInteractor implements PatchInteractor{
    @Override
    public String getClassName() {
        return "com.limagiran.holyrics.renderer.slide.MusicViewV2";
    }

    @Override
    public List<MethodPatchData> getMethodPatchDataList() {
        return new ArrayList<MethodPatchData>(){
            {
                add(new MethodPatchData("getParagraphs", new String[]{}, new RemoveFirstParagraphTransformer()));
            }
        };
    }
}
