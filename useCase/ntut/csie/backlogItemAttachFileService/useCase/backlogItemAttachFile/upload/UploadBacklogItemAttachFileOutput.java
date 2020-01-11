package ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.upload;

import ntut.csie.backlogItemAttachFileService.useCase.Output;

public interface UploadBacklogItemAttachFileOutput extends Output {
	public boolean isUploadSuccess();
	
	public void setUploadSuccess(boolean uploadSuccess);
	
	public String getErrorMessage();
	
	public void setErrorMessage(String errorMessage);
}
