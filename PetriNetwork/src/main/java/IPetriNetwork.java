

import java.util.List;

public interface IPetriNetwork {
	
	public void addPlace(String label);
	public void rmPlace(String label);
	public void setPlaceToken(String label, int token);
	
	public void addTransition(String label);
	public void rmTransition(String label);
	
	public void addInArc(String label, String fromTransition, String toPlace);
	public void addOutArc(String label, String fromPlace, String toTransition);
	public void setArcWeight(String label, int weight);
	public void rmArc(String label);
	
	public void fire(String label);
	public List<String> fireableTransitions();
	
	
	
	

}
