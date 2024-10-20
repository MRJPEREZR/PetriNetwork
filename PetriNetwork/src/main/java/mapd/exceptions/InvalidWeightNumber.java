package mapd.exceptions;

public class InvalidWeightNumber extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public InvalidWeightNumber(String errorMessage) {
		super(errorMessage);
	}
	
}
