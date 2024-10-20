package mapd.exceptions;

public class RepeatedNameElement extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public RepeatedNameElement (String errorMessage) {
		super(errorMessage);
	}
}
