package ntut.csie.backlogItemAttachFileService.controller.backlogItemAttachFile;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

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
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public synchronized UploadBacklogItemAttachFileOutput uploadBacklogItemAttachFile(
			@PathParam("backlog_item_id") String backlogItemId, 
			String backlogItemAttachFileInfo) {
		byte[] attachFileContents = null;
		String name = "";
		
		UploadBacklogItemAttachFileOutput output = this;
		
		try {
			JSONObject backlogItemAttachFileJSON = new JSONObject(backlogItemAttachFileInfo);
			attachFileContents = Base64.decode(backlogItemAttachFileJSON.getString("attachFileContents"));
			name = backlogItemAttachFileJSON.getString("name");
		} catch (JSONException e) {
			e.printStackTrace();
			output.setUploadSuccess(false);
			output.setErrorMessage("Sorry, there is the service problem when upload the attach file of the backlog item. Please contact to the system administrator!");
			return output;
		}
		
		UploadBacklogItemAttachFileInput input = (UploadBacklogItemAttachFileInput) uploadBacklogItemAttachFileUseCase;
		input.setAttachFileContents(attachFileContents);
		input.setName(name);
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
