package ntut.csie.backlogItemAttachFileService.model.backlogItemAttachFile;

import java.util.Date;
import java.util.UUID;

import ntut.csie.backlogItemAttachFileService.model.DateProvider;

public class BacklogItemAttachFileBuilder {
	private String backlogItemAttachFileId;
	private String name;
	private String path;
	private String backlogItemId;
	private Date createTime;
	
	public static BacklogItemAttachFileBuilder newInstance() {
		return new BacklogItemAttachFileBuilder();
	}
	
	public BacklogItemAttachFileBuilder name(String name) {
		this.name = name;
		return this;
	}
	
	public BacklogItemAttachFileBuilder path(String path) {
		this.path = path;
		return this;
	}
	
	public BacklogItemAttachFileBuilder backlogItemId(String backlogItemId) {
		this.backlogItemId = backlogItemId;
		return this;
	}
	
	public BacklogItemAttachFile build() throws Exception {
		String exceptionMessage = "";
		if(name == null || name.isEmpty()) {
			exceptionMessage += "The name of the backlog item attach file should be required!\n";
		}
		if(!exceptionMessage.isEmpty()) {
			throw new Exception(exceptionMessage);
		}
		
		this.backlogItemAttachFileId = UUID.randomUUID().toString();
		this.createTime = DateProvider.now();
		BacklogItemAttachFile backlogItemAttachFile = new BacklogItemAttachFile(backlogItemAttachFileId, name, path, backlogItemId, createTime);
		return backlogItemAttachFile;
	}
}
