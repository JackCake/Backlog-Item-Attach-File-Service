package ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.remove;

import ntut.csie.backlogItemAttachFileService.useCase.Output;

public interface RemoveBacklogItemAttachFileOutput extends Output {
	public boolean isRemoveSuccess();
	
	public void setRemoveSuccess(boolean removeSuccess);
	
	public String getErrorMessage();
	
	public void setErrorMessage(String errorMessage);
}
