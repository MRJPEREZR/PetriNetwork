package mapd.exceptions;

public class RepeatedArc extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public RepeatedArc(String errorMessage) {
		super(errorMessage);
	}
}
