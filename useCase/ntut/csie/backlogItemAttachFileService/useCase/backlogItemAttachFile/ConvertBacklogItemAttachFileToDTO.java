package ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile;

import ntut.csie.backlogItemAttachFileService.model.backlogItemAttachFile.BacklogItemAttachFile;

public class ConvertBacklogItemAttachFileToDTO {
	public static BacklogItemAttachFileModel transform(BacklogItemAttachFile backlogItemAttachFile) {
		BacklogItemAttachFileModel dto = new BacklogItemAttachFileModel();
		dto.setBacklogItemAttachFileId(backlogItemAttachFile.getBacklogItemAttachFileId());
		dto.setOrderId(backlogItemAttachFile.getOrderId());
		dto.setName(backlogItemAttachFile.getName());
		dto.setBacklogItemId(backlogItemAttachFile.getBacklogItemId());
		return dto;
	}
}
