
public class IllegalCharacterException extends Exception {
	private static final String MESSAGE = "Invalid Id";
	
	public IllegalCharacterException() {
		super(MESSAGE);
	}
	
	public IllegalCharacterException(String message) {
		super(message);
	}
}
