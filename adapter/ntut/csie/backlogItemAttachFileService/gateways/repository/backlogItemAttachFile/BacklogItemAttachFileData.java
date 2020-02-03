package ntut.csie.backlogItemAttachFileService.gateways.repository.backlogItemAttachFile;

public class BacklogItemAttachFileData {
	private String backlogItemAttachFileId;
	private String name;
	private String path;
	private String backlogItemId;
	private String createTime;
	
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
