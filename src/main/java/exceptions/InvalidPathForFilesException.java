package exceptions;

@SuppressWarnings("serial")
public class InvalidPathForFilesException extends FrwException {

	public InvalidPathForFilesException(String message) {
		super(message);
	}

	public InvalidPathForFilesException(String message, Throwable cause) {
		super(message, cause);
	}

}
