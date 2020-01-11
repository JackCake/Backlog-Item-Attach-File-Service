package ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.upload;

import java.io.InputStream;

import ntut.csie.backlogItemAttachFileService.useCase.Input;

public interface UploadBacklogItemAttachFileInput extends Input {
	public InputStream getUploadedAttachFileInputStream();
	
	public void setUploadedAttachFileInputStream(InputStream uploadedAttachFileInputStream);
	
	public String getName();
	
	public void setName(String name);
	
	public String getBacklogItemId();
	
	public void setBacklogItemId(String backlogItemId);
}
