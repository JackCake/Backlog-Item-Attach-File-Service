package ntut.csie.backlogItemAttachFileService.controller.backlogItemAttachFile;

import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ntut.csie.backlogItemAttachFileService.ApplicationContext;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.BacklogItemAttachFileModel;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.get.GetBacklogItemAttachFilesByBacklogItemIdInput;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.get.GetBacklogItemAttachFilesByBacklogItemIdOutput;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.get.GetBacklogItemAttachFilesByBacklogItemIdUseCase;

@Path("/backlog_items/{backlog_item_id}/backlog_item_attach_files")
@Singleton
public class GetBacklogItemAttachFilesByBacklogItemIdRestfulAPI implements GetBacklogItemAttachFilesByBacklogItemIdOutput{
	private ApplicationContext applicationContext = ApplicationContext.getInstance();
	private GetBacklogItemAttachFilesByBacklogItemIdUseCase getBacklogItemAttachFilesByBacklogItemIdUseCase = applicationContext.newGetBacklogItemAttachFilesByBacklogItemIdUseCase();
	
	private List<BacklogItemAttachFileModel> backlogItemAttachFileList;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public synchronized GetBacklogItemAttachFilesByBacklogItemIdOutput getBacklogItemAttachFilesByBacklogItemId(@PathParam("backlog_item_id") String backlogItemId) {
		GetBacklogItemAttachFilesByBacklogItemIdOutput output = this;
		
		GetBacklogItemAttachFilesByBacklogItemIdInput input = (GetBacklogItemAttachFilesByBacklogItemIdInput) getBacklogItemAttachFilesByBacklogItemIdUseCase;
		input.setBacklogItemId(backlogItemId);
		
		getBacklogItemAttachFilesByBacklogItemIdUseCase.execute(input, output);
		
		return output;
	}

	@Override
	public List<BacklogItemAttachFileModel> getBacklogItemAttachFileList() {
		return backlogItemAttachFileList;
	}

	@Override
	public void setBacklogItemAttachFileList(List<BacklogItemAttachFileModel> backlogItemAttachFileList) {
		this.backlogItemAttachFileList = backlogItemAttachFileList;
	}
}
