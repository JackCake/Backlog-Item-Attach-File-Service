package ntut.csie.backlogItemAttachFileService.model.backlogItemAttachFile;

import java.util.Date;

public class BacklogItemAttachFile {
	private String backlogItemAttachFileId;
	private String name;
	private String path;
	private String backlogItemId;
	private Date createTime;
	
	public BacklogItemAttachFile() {}
	
	public BacklogItemAttachFile(String backlogItemAttachFileId, String name, String path, String backlogItemId, Date createTime) {
		this.backlogItemAttachFileId = backlogItemAttachFileId;
		this.name = name;
		this.path = path;
		this.backlogItemId = backlogItemId;
		this.createTime = createTime;
	}

	public String getBacklogItemAttachFileId() {
		return backlogItemAttachFileId;
	}

	public void setBacklogItemAttachFileId(String backlogItemAttachFileId) {
		this.backlogItemAttachFileId = backlogItemAttachFileId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getBacklogItemId() {
		return backlogItemId;
	}

	public void setBacklogItemId(String backlogItemId) {
		this.backlogItemId = backlogItemId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
