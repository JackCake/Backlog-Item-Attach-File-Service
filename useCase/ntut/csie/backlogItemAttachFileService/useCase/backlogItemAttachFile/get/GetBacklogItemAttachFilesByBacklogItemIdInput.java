package ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.get;

import ntut.csie.backlogItemAttachFileService.useCase.Input;

public interface GetBacklogItemAttachFilesByBacklogItemIdInput extends Input {
	public String getBacklogItemId();
	
	public void setBacklogItemId(String backlogItemId);
}
