package interactor;

import dto.MethodPatchData;

import java.util.List;

public interface PatchInteractor {

    String getClassName();

    List<MethodPatchData> getMethodPatchDataList();

}
