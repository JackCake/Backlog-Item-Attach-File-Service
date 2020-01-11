package ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.get;

import java.util.List;

import ntut.csie.backlogItemAttachFileService.useCase.Output;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.BacklogItemAttachFileModel;

public interface GetBacklogItemAttachFilesByBacklogItemIdOutput extends Output {
	public List<BacklogItemAttachFileModel> getBacklogItemAttachFileList();
	
	public void setBacklogItemAttachFileList(List<BacklogItemAttachFileModel> backlogItemAttachFileList);
}
