package mapd.interfaces;

import mapd.exceptions.*;

import java.util.List;

public interface IPetriNetwork {

	public void addPlace(String label) throws RepeatedNameElement;
	public void addPlace(String label, Integer tokens) throws InvalidTokenNumber, RepeatedNameElement, InvalidTokenNumber, mapd.exceptions.RepeatedNameElement;
	public void rmPlace(String label);
	public void setPlaceTokens(String label, Integer tokens) throws InvalidTokenNumber;
	public void showPlaces();
	public void renamePlace(String oldName, String newName) throws RepeatedNameElement, ElementNameNotExists;
	
	public void addTransition(String label) throws RepeatedNameElement;
	public void rmTransition(String label);
	public void showTransitions();
	public void renameTransition(String oldName, String newName) throws RepeatedNameElement, ElementNameNotExists;

	public void addArc(String label, String transition, String place, String type) throws InvalidWeightNumber, RepeatedNameElement, RepeatedArc;
	public void addArc(String label, String transition, String place, String type, Integer weight) throws InvalidWeightNumber, RepeatedNameElement, RepeatedArc;
	public void editArcDirection(String label, String labelTransition, String labelPlace) throws InvalidWeightNumber, RepeatedArc;
	public void setArcWeight(String label, Integer weight) throws InvalidWeightNumber;
	public void rmArc(String label);
	public void showArcs();
	public void renameArc(String oldName, String newName) throws RepeatedNameElement, ElementNameNotExists;

	public void fire(String label);
	public List<String> fireableTransitions();
	public void showAllElements();

}

