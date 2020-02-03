package ntut.csie.backlogItemAttachFileService.gateways.repository.backlogItemAttachFile;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ntut.csie.backlogItemAttachFileService.model.backlogItemAttachFile.BacklogItemAttachFile;

public class BacklogItemAttachFileMapper {
	public BacklogItemAttachFile transformToBacklogItemAttachFile(BacklogItemAttachFileData data) throws ParseException {
		BacklogItemAttachFile backlogItemAttachFile = new BacklogItemAttachFile();
		backlogItemAttachFile.setBacklogItemAttachFileId(data.getBacklogItemAttachFileId());
		backlogItemAttachFile.setName(data.getName());
		backlogItemAttachFile.setPath(data.getPath());
		backlogItemAttachFile.setBacklogItemId(data.getBacklogItemId());
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date createTime = simpleDateFormat.parse(data.getCreateTime());
		backlogItemAttachFile.setCreateTime(createTime);
		return backlogItemAttachFile;
	}
	
	public BacklogItemAttachFileData transformToBacklogItemAttachFileData(BacklogItemAttachFile backlogItemAttachFile) {
		BacklogItemAttachFileData data = new BacklogItemAttachFileData();
		data.setBacklogItemAttachFileId(backlogItemAttachFile.getBacklogItemAttachFileId());
		data.setName(backlogItemAttachFile.getName());
		data.setPath(backlogItemAttachFile.getPath());
		data.setBacklogItemId(backlogItemAttachFile.getBacklogItemId());
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime = simpleDateFormat.format(backlogItemAttachFile.getCreateTime());
		data.setCreateTime(createTime);
		return data;
	}
}
