package ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile;

public class BacklogItemAttachFileModel {
	private String backlogItemAttachFileId;
	private int orderId;
	private String name;
	private String path;
	private String backlogItemId;

	public String getBacklogItemAttachFileId() {
		return backlogItemAttachFileId;
	}

	public void setBacklogItemAttachFileId(String backlogItemAttachFileId) {
		this.backlogItemAttachFileId = backlogItemAttachFileId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
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
}
