package mapd.exceptions;

public class InvalidTokenNumber extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public InvalidTokenNumber(String errorMessage) {
        super(errorMessage);
    }
	
}
