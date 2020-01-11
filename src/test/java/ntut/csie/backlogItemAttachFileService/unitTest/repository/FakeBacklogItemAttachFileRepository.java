package ntut.csie.backlogItemAttachFileService.unitTest.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ntut.csie.backlogItemAttachFileService.model.backlogItemAttachFile.BacklogItemAttachFile;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.BacklogItemAttachFileRepository;

public class FakeBacklogItemAttachFileRepository implements BacklogItemAttachFileRepository {
	private Map<String, BacklogItemAttachFile> backlogItemAttachFiles;

	public FakeBacklogItemAttachFileRepository() {
		backlogItemAttachFiles = Collections.synchronizedMap(new LinkedHashMap<String, BacklogItemAttachFile>());
	}
	
	@Override
	public void save(BacklogItemAttachFile backlogItemAttachFile) {
		backlogItemAttachFiles.put(backlogItemAttachFile.getBacklogItemAttachFileId(), backlogItemAttachFile);
	}

	@Override
	public void remove(BacklogItemAttachFile backlogItemAttachFile) {
		backlogItemAttachFiles.remove(backlogItemAttachFile.getBacklogItemAttachFileId());
	}
	
	@Override
	public BacklogItemAttachFile getBacklogItemAttachFileById(String backlogItemAttachFileId) {
		return backlogItemAttachFiles.get(backlogItemAttachFileId);
	}

	@Override
	public Collection<BacklogItemAttachFile> getBacklogItemAttachFilesByBacklogItemId(String backlogItemId) {
		List<BacklogItemAttachFile> backlogItemAttachFileList = new ArrayList<>();
		for(BacklogItemAttachFile backlogItemAttachFile : backlogItemAttachFiles.values()) {
			if(backlogItemAttachFile.getBacklogItemId().equals(backlogItemId)) {
				backlogItemAttachFileList.add(backlogItemAttachFile);
			}
		}
		return backlogItemAttachFileList;
	}
	
	public void clearAll() {
		backlogItemAttachFiles.clear();
	}
}
