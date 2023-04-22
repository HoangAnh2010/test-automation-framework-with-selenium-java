package exceptions;

/**
 * FrameworkException extends RuntimeException - because I want to terminate the
 * program when the Exception comes
 */
@SuppressWarnings("serial")
public class FrwException extends RuntimeException {

	public FrwException(String message) {
		super(message);
	}

	public FrwException(String message, Throwable cause) {
		super(message, cause);
	}
}