package ntut.csie.backlogItemAttachFileService;

import ntut.csie.backlogItemAttachFileService.gateways.repository.backlogItemAttachFile.MySqlBacklogItemAttachFileRepositoryImpl;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.BacklogItemAttachFileRepository;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.download.DownloadBacklogItemAttachFileUseCase;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.download.DownloadBacklogItemAttachFileUseCaseImpl;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.get.GetBacklogItemAttachFilesByBacklogItemIdUseCase;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.get.GetBacklogItemAttachFilesByBacklogItemIdUseCaseImpl;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.remove.RemoveBacklogItemAttachFileUseCase;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.remove.RemoveBacklogItemAttachFileUseCaseImpl;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.upload.UploadBacklogItemAttachFileUseCase;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.upload.UploadBacklogItemAttachFileUseCaseImpl;

public class ApplicationContext {
	private static ApplicationContext instance = null;
	
	private static BacklogItemAttachFileRepository backlogItemAttachFileRepository = null;
	
	private UploadBacklogItemAttachFileUseCase uploadBacklogItemAttachFileUseCase;
	private GetBacklogItemAttachFilesByBacklogItemIdUseCase getBacklogItemAttachFilesByBacklogItemIdUseCase;
	private DownloadBacklogItemAttachFileUseCase downloadBacklogItemAttachFileUseCase;
	private RemoveBacklogItemAttachFileUseCase removeBacklogItemAttachFileUseCase;
	
	private ApplicationContext() {}
	
	public static synchronized ApplicationContext getInstance() {
		if(instance == null){
			return new ApplicationContext();
		}
		return instance;
	}
	
	public BacklogItemAttachFileRepository newBacklogItemAttachFileRepository() {
		if(backlogItemAttachFileRepository == null) {
			backlogItemAttachFileRepository = new MySqlBacklogItemAttachFileRepositoryImpl();
		}
		return backlogItemAttachFileRepository;
	}
	
	public UploadBacklogItemAttachFileUseCase newUploadBacklogItemAttachFileUseCase() {
		uploadBacklogItemAttachFileUseCase = new UploadBacklogItemAttachFileUseCaseImpl(newBacklogItemAttachFileRepository());
		return uploadBacklogItemAttachFileUseCase;
	}
	
	public GetBacklogItemAttachFilesByBacklogItemIdUseCase newGetBacklogItemAttachFilesByBacklogItemIdUseCase() {
		getBacklogItemAttachFilesByBacklogItemIdUseCase = new GetBacklogItemAttachFilesByBacklogItemIdUseCaseImpl(newBacklogItemAttachFileRepository());
		return getBacklogItemAttachFilesByBacklogItemIdUseCase;
	}
	
	public DownloadBacklogItemAttachFileUseCase newDownloadBacklogItemAttachFileUseCase() {
		downloadBacklogItemAttachFileUseCase = new DownloadBacklogItemAttachFileUseCaseImpl(newBacklogItemAttachFileRepository());
		return downloadBacklogItemAttachFileUseCase;
	}
	
	public RemoveBacklogItemAttachFileUseCase newRemoveBacklogItemAttachFileUseCase() {
		removeBacklogItemAttachFileUseCase = new RemoveBacklogItemAttachFileUseCaseImpl(newBacklogItemAttachFileRepository());
		return removeBacklogItemAttachFileUseCase;
	}
}
