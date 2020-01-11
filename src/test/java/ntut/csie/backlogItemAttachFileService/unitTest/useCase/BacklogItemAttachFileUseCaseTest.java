package ntut.csie.backlogItemAttachFileService.unitTest.useCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ntut.csie.backlogItemAttachFileService.controller.backlogItemAttachFile.UploadBacklogItemAttachFileRestfulAPI;
import ntut.csie.backlogItemAttachFileService.model.backlogItemAttachFile.BacklogItemAttachFile;
import ntut.csie.backlogItemAttachFileService.controller.backlogItemAttachFile.RemoveBacklogItemAttachFileRestfulAPI;
import ntut.csie.backlogItemAttachFileService.controller.backlogItemAttachFile.DownloadBacklogItemAttachFileRestfulAPI;
import ntut.csie.backlogItemAttachFileService.controller.backlogItemAttachFile.GetBacklogItemAttachFilesByBacklogItemIdRestfulAPI;
import ntut.csie.backlogItemAttachFileService.unitTest.repository.FakeBacklogItemAttachFileRepository;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.BacklogItemAttachFileModel;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.download.DownloadBacklogItemAttachFileInput;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.download.DownloadBacklogItemAttachFileOutput;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.download.DownloadBacklogItemAttachFileUseCase;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.download.DownloadBacklogItemAttachFileUseCaseImpl;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.get.GetBacklogItemAttachFilesByBacklogItemIdInput;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.get.GetBacklogItemAttachFilesByBacklogItemIdOutput;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.get.GetBacklogItemAttachFilesByBacklogItemIdUseCase;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.get.GetBacklogItemAttachFilesByBacklogItemIdUseCaseImpl;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.remove.RemoveBacklogItemAttachFileInput;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.remove.RemoveBacklogItemAttachFileOutput;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.remove.RemoveBacklogItemAttachFileUseCase;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.remove.RemoveBacklogItemAttachFileUseCaseImpl;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.upload.UploadBacklogItemAttachFileInput;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.upload.UploadBacklogItemAttachFileOutput;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.upload.UploadBacklogItemAttachFileUseCase;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.upload.UploadBacklogItemAttachFileUseCaseImpl;

public class BacklogItemAttachFileUseCaseTest {
	private FakeBacklogItemAttachFileRepository fakeBacklogItemAttachFileRepository;
	
	private String backlogItemId;
	
	@Before
	public void setUp() {
		fakeBacklogItemAttachFileRepository = new FakeBacklogItemAttachFileRepository();
		
		backlogItemId = "1";
	}
	
	@After
	public void tearDown() {
		fakeBacklogItemAttachFileRepository.clearAll();
		
		File folder = new File("backlogItemAttachFiles" + File.separator + backlogItemId);
		File[] attachFileList = folder.listFiles();
		if(attachFileList != null) {
			for(File attachFile : attachFileList) {
				attachFile.delete();
			}
		}
		folder.delete();
	}
	
	@Test
	public void Should_Success_When_UploadBacklogItemAttachFile() {
		String name = "Test.txt";
		InputStream uploadedAttachFileInputStream = null;
		try {
			uploadedAttachFileInputStream = new FileInputStream(name);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String backlogItemId = "1";
		
		UploadBacklogItemAttachFileOutput output = uploadBacklogItemAttachFile(uploadedAttachFileInputStream, name, backlogItemId);
		
		assertTrue(output.isUploadSuccess());
	}
	
	@Test
	public void Should_ReturnBacklogItemAttachFiles_When_GetBacklogItemAttachFilesByBacklogItemId() {
		String[] names = {"Test.txt", "測試用檔案-工作會議附件.docx", "(Test) Product Backlog Excel File 2020-1-8.xlsx"};
		String backlogItemId = "1";
		
		int numberOfBacklogItemAttachFiles = names.length;
		
		for(int i = 0; i < numberOfBacklogItemAttachFiles; i++) {
			InputStream uploadedAttachFileInputStream = null;
			try {
				uploadedAttachFileInputStream = new FileInputStream(names[i]);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			uploadBacklogItemAttachFile(uploadedAttachFileInputStream, names[i], backlogItemId);
		}
		
		GetBacklogItemAttachFilesByBacklogItemIdOutput output = getBacklogItemAttachFilesByBacklogItemId();
		List<BacklogItemAttachFileModel> backlogItemAttachFileList = output.getBacklogItemAttachFileList();
		
		assertEquals(numberOfBacklogItemAttachFiles, backlogItemAttachFileList.size());
	}
	
	@Test
	public void Should_Success_When_DownloadBacklogItemAttachFile() {
		String name = "Test.txt";
		InputStream uploadedAttachFileInputStream = null;
		try {
			uploadedAttachFileInputStream = new FileInputStream(name);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String backlogItemId = "1";
		
		uploadBacklogItemAttachFile(uploadedAttachFileInputStream, name, backlogItemId);

		List<BacklogItemAttachFile> backlogItemAttachFileList = new ArrayList<>(fakeBacklogItemAttachFileRepository.getBacklogItemAttachFilesByBacklogItemId(backlogItemId));
		String backlogItemAttachFileId = backlogItemAttachFileList.get(backlogItemAttachFileList.size() - 1).getBacklogItemAttachFileId();
		
		DownloadBacklogItemAttachFileOutput output = downloadBacklogItemAttachFile(backlogItemAttachFileId);
		
		assertTrue(output.isDownloadSuccess());
	}
	
	@Test
	public void Should_Success_When_RemoveBacklogItemAttachFile() {
		String name = "Test.txt";
		InputStream uploadedAttachFileInputStream = null;
		try {
			uploadedAttachFileInputStream = new FileInputStream(name);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String backlogItemId = "1";
		
		uploadBacklogItemAttachFile(uploadedAttachFileInputStream, name, backlogItemId);
		List<BacklogItemAttachFile> backlogItemAttachFileList = new ArrayList<>(fakeBacklogItemAttachFileRepository.getBacklogItemAttachFilesByBacklogItemId(backlogItemId));
		String backlogItemAttachFileId = backlogItemAttachFileList.get(backlogItemAttachFileList.size() - 1).getBacklogItemAttachFileId();
		
		RemoveBacklogItemAttachFileOutput output = removeBacklogItemAttachFile(backlogItemAttachFileId);
		
		assertTrue(output.isRemoveSuccess());
	}
	
	private UploadBacklogItemAttachFileOutput uploadBacklogItemAttachFile(InputStream uploadedAttachFileInputStream, String name, String backlogItemId) {
		UploadBacklogItemAttachFileUseCase addBacklogItemAttachFileUseCase = new UploadBacklogItemAttachFileUseCaseImpl(fakeBacklogItemAttachFileRepository);
		UploadBacklogItemAttachFileInput input = (UploadBacklogItemAttachFileInput) addBacklogItemAttachFileUseCase;
		input.setUploadedAttachFileInputStream(uploadedAttachFileInputStream);
		input.setName(name);
		input.setBacklogItemId(backlogItemId);
		UploadBacklogItemAttachFileOutput output = new UploadBacklogItemAttachFileRestfulAPI();
		addBacklogItemAttachFileUseCase.execute(input, output);
		return output;
	}
	
	private GetBacklogItemAttachFilesByBacklogItemIdOutput getBacklogItemAttachFilesByBacklogItemId() {
		GetBacklogItemAttachFilesByBacklogItemIdUseCase getBacklogItemAttachFilesByBacklogItemIdUseCase = new GetBacklogItemAttachFilesByBacklogItemIdUseCaseImpl(fakeBacklogItemAttachFileRepository);
		GetBacklogItemAttachFilesByBacklogItemIdInput input = (GetBacklogItemAttachFilesByBacklogItemIdInput) getBacklogItemAttachFilesByBacklogItemIdUseCase;
		input.setBacklogItemId(backlogItemId);
		GetBacklogItemAttachFilesByBacklogItemIdOutput output = new GetBacklogItemAttachFilesByBacklogItemIdRestfulAPI();
		getBacklogItemAttachFilesByBacklogItemIdUseCase.execute(input, output);
		return output;
	}
	
	private DownloadBacklogItemAttachFileOutput downloadBacklogItemAttachFile(String backlogItemAttachFileId) {
		DownloadBacklogItemAttachFileUseCase downloadBacklogItemAttachFileUseCase = new DownloadBacklogItemAttachFileUseCaseImpl(fakeBacklogItemAttachFileRepository);
		DownloadBacklogItemAttachFileInput input = (DownloadBacklogItemAttachFileInput) downloadBacklogItemAttachFileUseCase;
		input.setBacklogItemAttachFileId(backlogItemAttachFileId);
		DownloadBacklogItemAttachFileOutput output = new DownloadBacklogItemAttachFileRestfulAPI();
		downloadBacklogItemAttachFileUseCase.execute(input, output);
		return output;
	}
	
	private RemoveBacklogItemAttachFileOutput removeBacklogItemAttachFile(String backlogItemAttachFileId) {
		RemoveBacklogItemAttachFileUseCase removeBacklogItemAttachFileUseCase = new RemoveBacklogItemAttachFileUseCaseImpl(fakeBacklogItemAttachFileRepository);
		RemoveBacklogItemAttachFileInput input = (RemoveBacklogItemAttachFileInput) removeBacklogItemAttachFileUseCase;
		input.setBacklogItemAttachFileId(backlogItemAttachFileId);
		RemoveBacklogItemAttachFileOutput output = new RemoveBacklogItemAttachFileRestfulAPI();
		removeBacklogItemAttachFileUseCase.execute(input, output);
		return output;
	}
}
