package ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.get;

import java.util.ArrayList;
import java.util.List;

import ntut.csie.backlogItemAttachFileService.model.backlogItemAttachFile.BacklogItemAttachFile;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.BacklogItemAttachFileModel;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.BacklogItemAttachFileRepository;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.ConvertBacklogItemAttachFileToDTO;

public class GetBacklogItemAttachFilesByBacklogItemIdUseCaseImpl implements GetBacklogItemAttachFilesByBacklogItemIdUseCase, GetBacklogItemAttachFilesByBacklogItemIdInput {
	private BacklogItemAttachFileRepository backlogItemAttachFileRepository;
	
	private String backlogItemId;
	
	public GetBacklogItemAttachFilesByBacklogItemIdUseCaseImpl(BacklogItemAttachFileRepository backlogItemAttachFileRepository) {
		this.backlogItemAttachFileRepository = backlogItemAttachFileRepository;
	}
	
	@Override
	public void execute(GetBacklogItemAttachFilesByBacklogItemIdInput input,
			GetBacklogItemAttachFilesByBacklogItemIdOutput output) {
		List<BacklogItemAttachFileModel> backlogItemAttachFileList = new ArrayList<>();
		for(BacklogItemAttachFile backlogItemAttachFile : backlogItemAttachFileRepository.getBacklogItemAttachFilesByBacklogItemId(input.getBacklogItemId())) {
			backlogItemAttachFileList.add(ConvertBacklogItemAttachFileToDTO.transform(backlogItemAttachFile));
		}
		output.setBacklogItemAttachFileList(backlogItemAttachFileList);
	}

	@Override
	public String getBacklogItemId() {
		return backlogItemId;
	}

	@Override
	public void setBacklogItemId(String backlogItemId) {
		this.backlogItemId = backlogItemId;
	}
}
