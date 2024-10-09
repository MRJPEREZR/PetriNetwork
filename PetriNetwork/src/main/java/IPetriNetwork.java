package main.java;

import java.util.List;

public interface IPetriNetwork {

	public void addPlace(String label);
	public void addPlace(String label, int tokens);
	public void rmPlace(String label);
	public void setPlaceToken(String label, int token);
	public void showPlaces();
	
	public void addTransition(String label);
	public void rmTransition(String label);
	public void showTransitions();
	
	public void addArc(String label, String transition, String place, String type);
	public void addArc(String label, String transition, String place, String type, int weight);
	public void modArc(String label, String transition, String place, String type);

	public void setArcWeight(String label, int weight);
	public void rmArc(String label);
	public void showArcs();

	public void fire(String label);
	public List<String> fireableTransitions();
	public void showAllElements();

}
