package mapd.exceptions;

public class NoFireableTransition extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public NoFireableTransition(String errorMessage) {
		super(errorMessage);
	}
	
}
