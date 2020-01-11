package ntut.csie.backlogItemAttachFileService.controller.backlogItemAttachFile;

import javax.inject.Singleton;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import ntut.csie.backlogItemAttachFileService.ApplicationContext;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.remove.RemoveBacklogItemAttachFileInput;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.remove.RemoveBacklogItemAttachFileOutput;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.remove.RemoveBacklogItemAttachFileUseCase;

@Path("/backlog_item_attach_files")
@Singleton
public class RemoveBacklogItemAttachFileRestfulAPI implements RemoveBacklogItemAttachFileOutput{
	private ApplicationContext applicationContext = ApplicationContext.getInstance();
	private RemoveBacklogItemAttachFileUseCase removeBacklogItemAttachFileUseCase = applicationContext.newRemoveBacklogItemAttachFileUseCase();

	private boolean removeSuccess;
	private String errorMessage;
	
	@DELETE
	@Path("/{backlog_item_attach_file_id}")
	public synchronized RemoveBacklogItemAttachFileOutput removeBacklogItemAttachFile(
			@PathParam("backlog_item_attach_file_id") String backlogItemAttachFileId) {
		RemoveBacklogItemAttachFileOutput output = this;
		
		RemoveBacklogItemAttachFileInput input = (RemoveBacklogItemAttachFileInput) removeBacklogItemAttachFileUseCase;
		input.setBacklogItemAttachFileId(backlogItemAttachFileId);
		
		removeBacklogItemAttachFileUseCase.execute(input, output);
		
		return output;
	}

	@Override
	public boolean isRemoveSuccess() {
		return removeSuccess;
	}

	@Override
	public void setRemoveSuccess(boolean removeSuccess) {
		this.removeSuccess = removeSuccess;
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
