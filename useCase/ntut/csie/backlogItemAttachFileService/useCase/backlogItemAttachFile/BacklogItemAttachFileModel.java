package ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile;

public class BacklogItemAttachFileModel {
	private String backlogItemAttachFileId;
	private String name;
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
