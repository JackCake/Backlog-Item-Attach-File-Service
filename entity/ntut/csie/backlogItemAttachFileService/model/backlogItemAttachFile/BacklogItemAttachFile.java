package ntut.csie.backlogItemAttachFileService.model.backlogItemAttachFile;

public class BacklogItemAttachFile {
	private String backlogItemAttachFileId;
	private int orderId;
	private String name;
	private String path;
	private String backlogItemId;
	
	public BacklogItemAttachFile() {}
	
	public BacklogItemAttachFile(String backlogItemAttachFileId, String name, String path, String backlogItemId) {
		this.backlogItemAttachFileId = backlogItemAttachFileId;
		this.name = name;
		this.path = path;
		this.backlogItemId = backlogItemId;
	}

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
