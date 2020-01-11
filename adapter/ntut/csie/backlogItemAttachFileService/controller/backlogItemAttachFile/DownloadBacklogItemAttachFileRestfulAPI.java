package ntut.csie.backlogItemAttachFileService.controller.backlogItemAttachFile;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ntut.csie.backlogItemAttachFileService.ApplicationContext;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.download.DownloadBacklogItemAttachFileInput;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.download.DownloadBacklogItemAttachFileOutput;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.download.DownloadBacklogItemAttachFileUseCase;

@Path("/backlog_item_attach_files")
@Singleton
public class DownloadBacklogItemAttachFileRestfulAPI implements DownloadBacklogItemAttachFileOutput{
	private ApplicationContext applicationContext = ApplicationContext.getInstance();
	private DownloadBacklogItemAttachFileUseCase downloadBacklogItemAttachFileUseCase = applicationContext.newDownloadBacklogItemAttachFileUseCase();
	
	private boolean downloadSuccess;
	private String errorMessage;
	private Response response;
	
	@GET
	@Path("/{backlog_item_attach_file_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public synchronized DownloadBacklogItemAttachFileOutput downloadBacklogItemAttachFile(
			@PathParam("backlog_item_attach_file_id") String backlogItemAttachFileId) {
		DownloadBacklogItemAttachFileOutput output = this;
		
		DownloadBacklogItemAttachFileInput input = (DownloadBacklogItemAttachFileInput) downloadBacklogItemAttachFileUseCase;
		input.setBacklogItemAttachFileId(backlogItemAttachFileId);
		
		downloadBacklogItemAttachFileUseCase.execute(input, output);
		
		return output;
	}

	@Override
	public boolean isDownloadSuccess() {
		return downloadSuccess;
	}

	@Override
	public void setDownloadSuccess(boolean downloadSuccess) {
		this.downloadSuccess = downloadSuccess;
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public Response getResponse() {
		return response;
	}

	@Override
	public void setResponse(Response response) {
		this.response = response;
	}
}
