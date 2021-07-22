package me.deepak.pdf_decrypter;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;

public class DecrypterService {

	private DecrypterService() {

	}

	public static void decrypt(File file, String password) throws IOException {
		File fileToBeDecrypted = getFileToBeDecrypted(file);
		PDDocument pdfDocument = PDDocument.load(fileToBeDecrypted, password);
		pdfDocument.setAllSecurityToBeRemoved(true);
		pdfDocument.save(fileToBeDecrypted);
		pdfDocument.close();
	}

	private static File getFileToBeDecrypted(File file) throws IOException {
		String fileName = file.getName();
		File parentFile = file.getParentFile();
		String parentPath = File.separator;
		if (parentFile != null) {
			parentPath = parentFile.getPath().concat(parentPath);
		}
		String decryptedFileName = parentPath
				.concat(fileName.substring(0, fileName.length() - ".pdf".length()).concat("-decrypted.pdf"));
		File decyptedFile = new File(decryptedFileName);
		FileUtils.copyFile(file, decyptedFile);
		return decyptedFile;
	}

}
