package interactor;

import dto.MethodPatchData;
import transformers.SlideAndTitleMuciDataTransformer;
import transformers.SlideAndTitleRemovalTransformer;

import java.util.ArrayList;
import java.util.List;

public class RemoveTitleAndArtistSlidePatchInteractor implements PatchInteractor {

    private final SlideAndTitleRemovalTransformer slideAndTitleRemovalTransformer = new SlideAndTitleRemovalTransformer();
    private final SlideAndTitleMuciDataTransformer slideAndTitleMuciDataTransformer = new SlideAndTitleMuciDataTransformer();

    @Override
    public String getClassName() {
        return "com.limagiran.holyrics.control.MusicController";
    }

    @Override
    public List<MethodPatchData> getMethodPatchDataList(){
        return new ArrayList<MethodPatchData>(){
            {
                add(new MethodPatchData("getTitleAndArtistParagraph", new String[]{"java.util.List", "com.limagiran.holyrics.model.theme.Theme"}, slideAndTitleRemovalTransformer));
                add(new MethodPatchData("getParagraphsToPresentation", new String[]{}, slideAndTitleMuciDataTransformer));
            }
        };
    }

}