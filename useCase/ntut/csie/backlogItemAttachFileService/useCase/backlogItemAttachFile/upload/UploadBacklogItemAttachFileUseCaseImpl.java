package ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;

import ntut.csie.backlogItemAttachFileService.model.backlogItemAttachFile.BacklogItemAttachFile;
import ntut.csie.backlogItemAttachFileService.model.backlogItemAttachFile.BacklogItemAttachFileBuilder;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.BacklogItemAttachFileRepository;

public class UploadBacklogItemAttachFileUseCaseImpl implements UploadBacklogItemAttachFileUseCase, UploadBacklogItemAttachFileInput {
	private BacklogItemAttachFileRepository backlogItemAttachFileRepository;
	
	private byte[] attachFileContents;
	private String name;
	private String backlogItemId;
	
	public UploadBacklogItemAttachFileUseCaseImpl(BacklogItemAttachFileRepository backlogItemAttachFileRepository) {
		this.backlogItemAttachFileRepository = backlogItemAttachFileRepository;
	}
	
	@Override
	public void execute(UploadBacklogItemAttachFileInput input, UploadBacklogItemAttachFileOutput output) {
		byte[] attachFileContents = input.getAttachFileContents();
		if(attachFileContents == null || attachFileContents.length == 0) {
			output.setUploadSuccess(false);
			output.setErrorMessage("Please upload the file!");
			return;
		}
		int maxAttachFileSize = 2097152; // (1MB = 1024 KB = 1048576 bytes)
		if(attachFileContents.length > maxAttachFileSize) {
			output.setUploadSuccess(false);
			output.setErrorMessage("The size of the file is too large! Please upload the smaller file!");
			return;
		}
		String backlogItemId = input.getBacklogItemId();
		String name = input.getName();
		String folderPath = "backlogItemAttachFiles" + File.separator + backlogItemId;
		String attachFilePath = folderPath + File.separator + UUID.randomUUID().toString() + name; //加上UUID是為了確保檔案是不重複的，以免重複上傳檔案造成檔案被覆寫的現象
		try {
			BacklogItemAttachFile backlogItemAttachFile = BacklogItemAttachFileBuilder.newInstance()
					.name(input.getName())
					.path(attachFilePath)
					.backlogItemId(backlogItemId)
					.build();
			File folder = new File(folderPath);
			if(!folder.exists()) {
				folder.mkdirs();
			}
			OutputStream uploadedAttachFileOutputStream = new FileOutputStream(new File(attachFilePath));
			uploadedAttachFileOutputStream.write(attachFileContents);
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
	public byte[] getAttachFileContents() {
		return attachFileContents;
	}

	@Override
	public void setAttachFileContents(byte[] attachFileContents) {
		this.attachFileContents = attachFileContents;
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
