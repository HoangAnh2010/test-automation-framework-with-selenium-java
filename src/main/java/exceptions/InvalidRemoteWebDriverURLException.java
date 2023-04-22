package exceptions;

@SuppressWarnings("serial")
public class InvalidRemoteWebDriverURLException extends FrwException {

	public InvalidRemoteWebDriverURLException(String message) {
		super(message);
	}

	public InvalidRemoteWebDriverURLException(String message, Throwable cause) {
		super(message, cause);
	}

}
