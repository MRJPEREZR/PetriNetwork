package mapd.exceptions;

public class ElementNameNotExists extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public ElementNameNotExists(String errorMessage) {
		super(errorMessage);
	}
}
