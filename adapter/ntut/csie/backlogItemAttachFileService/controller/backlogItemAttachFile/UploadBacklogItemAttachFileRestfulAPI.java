package ntut.csie.backlogItemAttachFileService.controller.backlogItemAttachFile;

import java.io.InputStream;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import ntut.csie.backlogItemAttachFileService.ApplicationContext;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.upload.UploadBacklogItemAttachFileInput;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.upload.UploadBacklogItemAttachFileOutput;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.upload.UploadBacklogItemAttachFileUseCase;

@Path("/backlog_items/{backlog_item_id}/backlog_item_attach_files")
@Singleton
public class UploadBacklogItemAttachFileRestfulAPI implements UploadBacklogItemAttachFileOutput {
	private ApplicationContext applicationContext = ApplicationContext.getInstance();
	private UploadBacklogItemAttachFileUseCase uploadBacklogItemAttachFileUseCase = applicationContext.newUploadBacklogItemAttachFileUseCase();
	
	private boolean uploadSuccess;
	private String errorMessage;
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public synchronized UploadBacklogItemAttachFileOutput uploadBacklogItemAttachFile(
			@PathParam("backlog_item_id") String backlogItemId, 
			@FormDataParam("attach_file") InputStream uploadedAttachFileInputStream, 
			@FormDataParam("attach_file") FormDataContentDisposition attachFileDetail) {
		UploadBacklogItemAttachFileOutput output = this;
		
		UploadBacklogItemAttachFileInput input = (UploadBacklogItemAttachFileInput) uploadBacklogItemAttachFileUseCase;
		input.setUploadedAttachFileInputStream(uploadedAttachFileInputStream);
		input.setName(attachFileDetail.getFileName());
		input.setBacklogItemId(backlogItemId);
		
		uploadBacklogItemAttachFileUseCase.execute(input, output);
		
		return output;
	}

	@Override
	public boolean isUploadSuccess() {
		return uploadSuccess;
	}

	@Override
	public void setUploadSuccess(boolean addSuccess) {
		this.uploadSuccess = addSuccess;
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
