package ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.upload;

import ntut.csie.backlogItemAttachFileService.useCase.Input;

public interface UploadBacklogItemAttachFileInput extends Input {
	public byte[] getAttachFileContent();
	
	public void setAttachFileContent(byte[] attachFileContent);
	
	public String getName();
	
	public void setName(String name);
	
	public String getBacklogItemId();
	
	public void setBacklogItemId(String backlogItemId);
}
