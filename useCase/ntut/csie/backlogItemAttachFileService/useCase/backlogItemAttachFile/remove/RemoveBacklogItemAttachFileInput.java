package ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.remove;

import ntut.csie.backlogItemAttachFileService.useCase.Input;

public interface RemoveBacklogItemAttachFileInput extends Input {
	public String getBacklogItemAttachFileId();
	
	public void setBacklogItemAttachFileId(String backlogItemAttachFileId);
}
