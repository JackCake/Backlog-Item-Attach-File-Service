package ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.download;

import javax.ws.rs.core.Response;

import ntut.csie.backlogItemAttachFileService.useCase.Output;

public interface DownloadBacklogItemAttachFileOutput extends Output {
	public boolean isDownloadSuccess();
	
	public void setDownloadSuccess(boolean downloadSuccess);
	
	public String getErrorMessage();
	
	public void setErrorMessage(String errorMessage);
	
	public Response getResponse();
	
	public void setResponse(Response response);
}
