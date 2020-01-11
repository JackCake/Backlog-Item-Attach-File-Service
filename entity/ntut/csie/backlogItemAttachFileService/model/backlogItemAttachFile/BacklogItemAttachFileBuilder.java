package ntut.csie.backlogItemAttachFileService.model.backlogItemAttachFile;

import java.util.UUID;

public class BacklogItemAttachFileBuilder {
	private String backlogItemAttachFileId;
	private int orderId;
	private String name;
	private String path;
	private String backlogItemId;
	
	public static BacklogItemAttachFileBuilder newInstance() {
		return new BacklogItemAttachFileBuilder();
	}
	
	public BacklogItemAttachFileBuilder orderId(int orderId) {
		this.orderId = orderId;
		return this;
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
	
	public BacklogItemAttachFile build() {
		this.backlogItemAttachFileId = UUID.randomUUID().toString();
		BacklogItemAttachFile backlogItemAttachFile = new BacklogItemAttachFile(backlogItemAttachFileId, name, path, backlogItemId);
		backlogItemAttachFile.setOrderId(orderId);
		return backlogItemAttachFile;
	}
}
