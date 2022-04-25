package interactor;

import dto.MethodPatchData;
import transformers.CheckLibRemovalTransformer;
import transformers.SlideAndTitleRemovalTransformer;

import java.util.ArrayList;
import java.util.List;

public class RemoveCheckLibPatchInteractor implements PatchInteractor {

    private final CheckLibRemovalTransformer checkLibRemovalTransformer = new CheckLibRemovalTransformer();

    @Override
    public String getClassName() {
        return "com.limagiran.holyrics.CheckLib";
    }

    @Override
    public List<MethodPatchData> getMethodPatchDataList(){
        return new ArrayList<MethodPatchData>(){
            {
                add(new MethodPatchData("check", new String[]{}, checkLibRemovalTransformer));
            }
        };
    }

}
