package ntut.csie.backlogItemAttachFileService.gateways.repository.backlogItemAttachFile;

import ntut.csie.backlogItemAttachFileService.model.backlogItemAttachFile.BacklogItemAttachFile;

public class BacklogItemAttachFileMapper {
	public BacklogItemAttachFile transformToBacklogItemAttachFile(BacklogItemAttachFileData data) {
		BacklogItemAttachFile backlogItemAttachFile = new BacklogItemAttachFile();
		backlogItemAttachFile.setBacklogItemAttachFileId(data.getBacklogItemAttachFileId());
		backlogItemAttachFile.setOrderId(data.getOrderId());
		backlogItemAttachFile.setName(data.getName());
		backlogItemAttachFile.setPath(data.getPath());
		backlogItemAttachFile.setBacklogItemId(data.getBacklogItemId());
		return backlogItemAttachFile;
	}
	
	public BacklogItemAttachFileData transformToBacklogItemAttachFileData(BacklogItemAttachFile backlogItemAttachFile) {
		BacklogItemAttachFileData data = new BacklogItemAttachFileData();
		data.setBacklogItemAttachFileId(backlogItemAttachFile.getBacklogItemAttachFileId());
		data.setOrderId(backlogItemAttachFile.getOrderId());
		data.setName(backlogItemAttachFile.getName());
		data.setPath(backlogItemAttachFile.getPath());
		data.setBacklogItemId(backlogItemAttachFile.getBacklogItemId());
		return data;
	}
}
