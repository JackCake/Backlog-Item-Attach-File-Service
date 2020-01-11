package ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.download;

import java.io.File;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import ntut.csie.backlogItemAttachFileService.model.backlogItemAttachFile.BacklogItemAttachFile;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.BacklogItemAttachFileRepository;

public class DownloadBacklogItemAttachFileUseCaseImpl implements DownloadBacklogItemAttachFileUseCase, DownloadBacklogItemAttachFileInput {
	private BacklogItemAttachFileRepository backlogItemAttachFileRepository;
	
	private String backlogItemAttachFileId;
	
	public DownloadBacklogItemAttachFileUseCaseImpl(BacklogItemAttachFileRepository backlogItemAttachFileRepository) {
		this.backlogItemAttachFileRepository = backlogItemAttachFileRepository;
	}
	
	@Override
	public void execute(DownloadBacklogItemAttachFileInput input, DownloadBacklogItemAttachFileOutput output) {
		BacklogItemAttachFile backlogItemAttachFile = backlogItemAttachFileRepository.getBacklogItemAttachFileById(input.getBacklogItemAttachFileId());
		if(backlogItemAttachFile == null) {
			output.setDownloadSuccess(false);
			output.setErrorMessage("Sorry, the attach file of the backlog item is not exist!");
			return;
		}
		output.setDownloadSuccess(true);
		File attachFile = new File(backlogItemAttachFile.getPath());
		ResponseBuilder responseBuilder = Response.ok((Object) attachFile);
		responseBuilder.header("Content-Disposition", "attachment; filename=" + backlogItemAttachFile.getName());
		output.setResponse(responseBuilder.build());
	}

	@Override
	public String getBacklogItemAttachFileId() {
		return backlogItemAttachFileId;
	}

	@Override
	public void setBacklogItemAttachFileId(String backlogItemAttachFileId) {
		this.backlogItemAttachFileId = backlogItemAttachFileId;
	}
}
