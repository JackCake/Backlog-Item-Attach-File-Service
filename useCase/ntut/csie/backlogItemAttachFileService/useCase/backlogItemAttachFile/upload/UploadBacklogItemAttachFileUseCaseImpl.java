package ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import ntut.csie.backlogItemAttachFileService.model.backlogItemAttachFile.BacklogItemAttachFile;
import ntut.csie.backlogItemAttachFileService.model.backlogItemAttachFile.BacklogItemAttachFileBuilder;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.BacklogItemAttachFileRepository;

public class UploadBacklogItemAttachFileUseCaseImpl implements UploadBacklogItemAttachFileUseCase, UploadBacklogItemAttachFileInput {
	private BacklogItemAttachFileRepository backlogItemAttachFileRepository;
	
	private byte[] attachFileContent;
	private String name;
	private String backlogItemId;
	
	public UploadBacklogItemAttachFileUseCaseImpl(BacklogItemAttachFileRepository backlogItemAttachFileRepository) {
		this.backlogItemAttachFileRepository = backlogItemAttachFileRepository;
	}
	
	@Override
	public void execute(UploadBacklogItemAttachFileInput input, UploadBacklogItemAttachFileOutput output) {
		byte[] attachFileContent = input.getAttachFileContent();
		String backlogItemId = input.getBacklogItemId();
		String name = input.getName();
		String folderPath = "backlogItemAttachFiles" + File.separator + backlogItemId;
		String attachFilePath = folderPath + File.separator + name;
		int orderId = backlogItemAttachFileRepository.getBacklogItemAttachFilesByBacklogItemId(backlogItemId).size() + 1;
		BacklogItemAttachFile backlogItemAttachFile = BacklogItemAttachFileBuilder.newInstance()
				.orderId(orderId)
				.name(input.getName())
				.path(attachFilePath)
				.backlogItemId(backlogItemId)
				.build();
		try {
			File folder = new File(folderPath);
			if(!folder.exists()) {
				folder.mkdirs();
			}
			OutputStream uploadedAttachFileOutputStream = new FileOutputStream(new File(attachFilePath));
			uploadedAttachFileOutputStream.write(attachFileContent);
			uploadedAttachFileOutputStream.flush();
			uploadedAttachFileOutputStream.close();
			backlogItemAttachFileRepository.save(backlogItemAttachFile);
		} catch (Exception e) {
			output.setUploadSuccess(false);
			output.setErrorMessage(e.getMessage());
			return;
		}
		output.setUploadSuccess(true);
	}
	
	@Override
	public byte[] getAttachFileContent() {
		return attachFileContent;
	}

	@Override
	public void setAttachFileContent(byte[] attachFileContent) {
		this.attachFileContent = attachFileContent;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getBacklogItemId() {
		return backlogItemId;
	}

	@Override
	public void setBacklogItemId(String backlogItemId) {
		this.backlogItemId = backlogItemId;
	}
}
