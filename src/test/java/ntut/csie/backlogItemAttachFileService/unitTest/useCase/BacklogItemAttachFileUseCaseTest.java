package ntut.csie.backlogItemAttachFileService.unitTest.useCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
		File attachFile = new File(name);
		byte[] attachFileContents = null;
		try {
			attachFileContents = Files.readAllBytes(attachFile.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		UploadBacklogItemAttachFileOutput output = uploadBacklogItemAttachFile(attachFileContents, name, backlogItemId);
		
		boolean isUploadSuccess = output.isUploadSuccess();
		assertTrue(isUploadSuccess);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_UploadAttachFileToBacklogItemWithNullAttachFileContents() {
		byte[] attachFileContents = null;
		String name = null;
		
		UploadBacklogItemAttachFileOutput output = uploadBacklogItemAttachFile(attachFileContents, name, backlogItemId);
		
		boolean isUploadSuccess = output.isUploadSuccess();
		String errorMessage = output.getErrorMessage();
		String expectedErrorMessage = "Please upload the file!";
		assertFalse(isUploadSuccess);
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_UploadAttachFileToBacklogItemWithEmptyAttachFileContents() {
		byte[] attachFileContents = new byte[0];
		String name = "";
		
		UploadBacklogItemAttachFileOutput output = uploadBacklogItemAttachFile(attachFileContents, name, backlogItemId);
		
		boolean isUploadSuccess = output.isUploadSuccess();
		String errorMessage = output.getErrorMessage();
		String expectedErrorMessage = "Please upload the file!";
		assertFalse(isUploadSuccess);
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_UploadAttachFileToBacklogItemWithTooLargeAttachFileContents() {
		byte[] attachFileContents = new byte[2097153];
		String name = "";
		
		UploadBacklogItemAttachFileOutput output = uploadBacklogItemAttachFile(attachFileContents, name, backlogItemId);
		
		boolean isUploadSuccess = output.isUploadSuccess();
		String errorMessage = output.getErrorMessage();
		String expectedErrorMessage = "The size of the file is too large! Please upload the smaller file!";
		assertFalse(isUploadSuccess);
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_UploadAttachFileToBacklogItemWithNullName() {
		byte[] attachFileContents = new byte[2097152];
		String name = null;
		
		UploadBacklogItemAttachFileOutput output = uploadBacklogItemAttachFile(attachFileContents, name, backlogItemId);
		
		boolean isUploadSuccess = output.isUploadSuccess();
		String errorMessage = output.getErrorMessage();
		String expectedErrorMessage = "The name of the backlog item attach file should be required!\n";
		assertFalse(isUploadSuccess);
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_UploadAttachFileToBacklogItemWithEmptyName() {
		byte[] attachFileContents = new byte[2097152];
		String name = "";
		
		UploadBacklogItemAttachFileOutput output = uploadBacklogItemAttachFile(attachFileContents, name, backlogItemId);
		
		boolean isUploadSuccess = output.isUploadSuccess();
		String errorMessage = output.getErrorMessage();
		String expectedErrorMessage = "The name of the backlog item attach file should be required!\n";
		assertFalse(isUploadSuccess);
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	@Test
	public void Should_ReturnBacklogItemAttachFileList_When_GetAttachFilesOfBacklogItem() {
		String[] names = {"Test.txt", "測試用檔案-工作會議附件.docx", "(Test) Product Backlog Excel File 2020-1-8.xlsx"};
		String backlogItemId = "1";
		
		int numberOfBacklogItemAttachFiles = names.length;
		
		for(int i = 0; i < numberOfBacklogItemAttachFiles; i++) {
			File attachFile = new File(names[i]);
			byte[] attachFileContents = null;
			try {
				attachFileContents = Files.readAllBytes(attachFile.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
			uploadBacklogItemAttachFile(attachFileContents, names[i], backlogItemId);
		}
		
		GetBacklogItemAttachFilesByBacklogItemIdOutput output = getBacklogItemAttachFilesByBacklogItemId(backlogItemId);
		List<BacklogItemAttachFileModel> backlogItemAttachFileList = output.getBacklogItemAttachFileList();
		
		for(int i = 0; i < numberOfBacklogItemAttachFiles; i++) {
			assertEquals(names[i], backlogItemAttachFileList.get(i).getName());
		}
		assertEquals(numberOfBacklogItemAttachFiles, backlogItemAttachFileList.size());
	}
	
	@Test
	public void Should_Success_When_DownloadBacklogItemAttachFile() {
		String name = "Test.txt";
		File attachFile = new File(name);
		byte[] attachFileContents = null;
		try {
			attachFileContents = Files.readAllBytes(attachFile.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		uploadBacklogItemAttachFile(attachFileContents, name, backlogItemId);

		List<BacklogItemAttachFile> backlogItemAttachFileList = new ArrayList<>(fakeBacklogItemAttachFileRepository.getBacklogItemAttachFilesByBacklogItemId(backlogItemId));
		String backlogItemAttachFileId = backlogItemAttachFileList.get(backlogItemAttachFileList.size() - 1).getBacklogItemAttachFileId();
		
		DownloadBacklogItemAttachFileOutput output = downloadBacklogItemAttachFile(backlogItemAttachFileId);
		
		boolean isDownloadSuccess = output.isDownloadSuccess();
		assertTrue(isDownloadSuccess);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_DownloadNotExistTaskAttachFile() {
		DownloadBacklogItemAttachFileOutput output = downloadBacklogItemAttachFile(null);
		
		boolean isDownloadSuccess = output.isDownloadSuccess();
		String errorMessage = output.getErrorMessage();
		String expectedErrorMessage = "Sorry, the attach file of the backlog item is not exist!";
		assertFalse(isDownloadSuccess);
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	@Test
	public void Should_Success_When_RemoveBacklogItemAttachFile() {
		String name = "Test.txt";
		File attachFile = new File(name);
		byte[] attachFileContents = null;
		try {
			attachFileContents = Files.readAllBytes(attachFile.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		uploadBacklogItemAttachFile(attachFileContents, name, backlogItemId);
		List<BacklogItemAttachFile> backlogItemAttachFileList = new ArrayList<>(fakeBacklogItemAttachFileRepository.getBacklogItemAttachFilesByBacklogItemId(backlogItemId));
		String backlogItemAttachFileId = backlogItemAttachFileList.get(backlogItemAttachFileList.size() - 1).getBacklogItemAttachFileId();
		
		RemoveBacklogItemAttachFileOutput output = removeBacklogItemAttachFile(backlogItemAttachFileId);
		
		boolean isRemoveSuccess = output.isRemoveSuccess();
		assertTrue(isRemoveSuccess);
	}
	
	@Test
	public void Should_ReturnErrorMessage_When_RemoveNotExistBacklogItemAttachFile() {
		String backlogItemAttachFileId = null;
		
		RemoveBacklogItemAttachFileOutput output = removeBacklogItemAttachFile(backlogItemAttachFileId);
		
		boolean isRemoveSuccess = output.isRemoveSuccess();
		String errorMessage = output.getErrorMessage();
		String expectedErrorMessage = "Sorry, the attach file of the backlog item is not exist!";
		assertFalse(isRemoveSuccess);
		assertEquals(expectedErrorMessage, errorMessage);
	}
	
	private UploadBacklogItemAttachFileOutput uploadBacklogItemAttachFile(byte[] attachFileContents, String name, String backlogItemId) {
		UploadBacklogItemAttachFileUseCase uploadBacklogItemAttachFileUseCase = new UploadBacklogItemAttachFileUseCaseImpl(fakeBacklogItemAttachFileRepository);
		UploadBacklogItemAttachFileInput input = (UploadBacklogItemAttachFileInput) uploadBacklogItemAttachFileUseCase;
		input.setAttachFileContents(attachFileContents);
		input.setName(name);
		input.setBacklogItemId(backlogItemId);
		UploadBacklogItemAttachFileOutput output = new UploadBacklogItemAttachFileRestfulAPI();
		uploadBacklogItemAttachFileUseCase.execute(input, output);
		return output;
	}
	
	private GetBacklogItemAttachFilesByBacklogItemIdOutput getBacklogItemAttachFilesByBacklogItemId(String backlogItemId) {
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
