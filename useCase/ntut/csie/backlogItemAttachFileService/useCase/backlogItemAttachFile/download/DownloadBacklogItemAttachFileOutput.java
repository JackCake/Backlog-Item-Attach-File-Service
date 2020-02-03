package ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.download;

import ntut.csie.backlogItemAttachFileService.useCase.Output;

public interface DownloadBacklogItemAttachFileOutput extends Output {
	public boolean isDownloadSuccess();
	
	public void setDownloadSuccess(boolean downloadSuccess);
	
	public String getErrorMessage();
	
	public void setErrorMessage(String errorMessage);
	
	public byte[] getAttachFileContents();
	
	public void setAttachFileContents(byte[] attachFileContents);
}
