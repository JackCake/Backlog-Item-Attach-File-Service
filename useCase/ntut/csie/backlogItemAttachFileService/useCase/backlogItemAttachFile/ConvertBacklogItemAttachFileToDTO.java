package ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import ntut.csie.backlogItemAttachFileService.model.backlogItemAttachFile.BacklogItemAttachFile;

public class ConvertBacklogItemAttachFileToDTO {
	public static BacklogItemAttachFileModel transform(BacklogItemAttachFile backlogItemAttachFile) {
		BacklogItemAttachFileModel dto = new BacklogItemAttachFileModel();
		dto.setBacklogItemAttachFileId(backlogItemAttachFile.getBacklogItemAttachFileId());
		dto.setName(backlogItemAttachFile.getName());
		dto.setBacklogItemId(backlogItemAttachFile.getBacklogItemId());
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String createTime = simpleDateFormat.format(backlogItemAttachFile.getCreateTime());
		dto.setCreateTime(createTime);
		return dto;
	}
}
