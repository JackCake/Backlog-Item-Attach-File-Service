package ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.remove;

import java.io.File;

import ntut.csie.backlogItemAttachFileService.model.backlogItemAttachFile.BacklogItemAttachFile;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.BacklogItemAttachFileRepository;

public class RemoveBacklogItemAttachFileUseCaseImpl implements RemoveBacklogItemAttachFileUseCase, RemoveBacklogItemAttachFileInput {
	private BacklogItemAttachFileRepository backlogItemAttachFileRepository;
	
	private String backlogItemAttachFileId;
	
	public RemoveBacklogItemAttachFileUseCaseImpl(BacklogItemAttachFileRepository backlogItemAttachFileRepository) {
		this.backlogItemAttachFileRepository = backlogItemAttachFileRepository;
	}
	
	@Override
	public void execute(RemoveBacklogItemAttachFileInput input, RemoveBacklogItemAttachFileOutput output) {
		BacklogItemAttachFile backlogItemAttachFile = backlogItemAttachFileRepository.getBacklogItemAttachFileById(input.getBacklogItemAttachFileId());
		if(backlogItemAttachFile == null) {
			output.setRemoveSuccess(false);
			output.setErrorMessage("Sorry, the attach file of the backlog item is not exist!");
			return;
		}
		File attachFile = new File(backlogItemAttachFile.getPath());
		if(!attachFile.delete()) {
			output.setRemoveSuccess(false);
			output.setErrorMessage("Sorry, there is the problem when remove the attach file!");
			return;
		}
		File folder = attachFile.getParentFile();
		if(folder.list().length == 0) {
			folder.delete();
		}
		try {
			backlogItemAttachFileRepository.remove(backlogItemAttachFile);
		} catch (Exception e) {
			output.setRemoveSuccess(false);
			output.setErrorMessage(e.getMessage());
			return;
		}
		output.setRemoveSuccess(true);
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
