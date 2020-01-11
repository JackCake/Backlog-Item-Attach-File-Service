package ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile;

import java.util.Collection;

import ntut.csie.backlogItemAttachFileService.model.backlogItemAttachFile.BacklogItemAttachFile;

public interface BacklogItemAttachFileRepository {
	public void save(BacklogItemAttachFile backlogItemAttachFile) throws Exception;
	
	public void remove(BacklogItemAttachFile backlogItemAttachFile) throws Exception;
	
	public BacklogItemAttachFile getBacklogItemAttachFileById(String backlogItemAttachFileId);
	
	public Collection<BacklogItemAttachFile> getBacklogItemAttachFilesByBacklogItemId(String backlogItemId);
}
