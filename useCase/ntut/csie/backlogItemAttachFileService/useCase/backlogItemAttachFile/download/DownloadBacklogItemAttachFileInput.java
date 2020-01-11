package ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.download;

import ntut.csie.backlogItemAttachFileService.useCase.Input;

public interface DownloadBacklogItemAttachFileInput extends Input {
	public String getBacklogItemAttachFileId();
	
	public void setBacklogItemAttachFileId(String backlogItemAttachFileId);
}
