package virtualDisk;

import java.io.File;
import java.io.FileNotFoundException;

public final class FileValidator {

	private static File fileToValidate;

	public static void validate(File file) throws FileNotFoundException {
		fileToValidate = file;
		newValidator().check();
	}

	public static FileValidator newValidator() {
		return new FileValidator();
	}

	public FileValidator check() throws FileNotFoundException {
		if (isFileNull() || doesFileExist()) {
			throw new FileNotFoundException();
		}

		return this;
	}

	private boolean isFileNull() {
		return fileToValidate == null;
	}

	private boolean doesFileExist() {
		return !fileToValidate.exists();
	}
}