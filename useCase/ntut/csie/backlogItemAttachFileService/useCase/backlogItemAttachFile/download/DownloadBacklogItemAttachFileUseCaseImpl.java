package ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.download;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
		File attachFile = new File(backlogItemAttachFile.getPath());
		byte[] attachFileContents = null;
		try {
			attachFileContents = Files.readAllBytes(attachFile.toPath());
		} catch (IOException e) {
			output.setDownloadSuccess(false);
			output.setErrorMessage(e.getMessage());
			return;
		}
		output.setDownloadSuccess(true);
		output.setAttachFileContents(attachFileContents);
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
