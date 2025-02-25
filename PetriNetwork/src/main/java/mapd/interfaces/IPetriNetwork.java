package mapd.interfaces;

import mapd.exceptions.*;

import java.util.List;

public interface IPetriNetwork {

	public void addPlace(String label) throws RepeatedNameElement, ElementNameNotExists, InvalidTokenNumber;
	public void addPlace(String label, Integer tokens) throws InvalidTokenNumber, RepeatedNameElement;
	public void rmPlace(String label) throws ElementNameNotExists;
	public void setPlaceTokens(String label, Integer tokens) throws InvalidTokenNumber, ElementNameNotExists;
	public void showPlaces();
	public void renamePlace(String oldName, String newName) throws RepeatedNameElement, ElementNameNotExists;
	
	public void addTransition(String label) throws RepeatedNameElement;
	public void rmTransition(String label) throws ElementNameNotExists;
	public void showTransitions();
	public void renameTransition(String oldName, String newName) throws RepeatedNameElement, ElementNameNotExists;

	public void addArc(String label, String transition, String place, String type) throws InvalidWeightNumber, RepeatedNameElement, RepeatedArc, ElementNameNotExists;
	public void addArc(String label, String transition, String place, String type, Integer weight) throws InvalidWeightNumber, RepeatedNameElement, RepeatedArc, ElementNameNotExists;
	public void setArcWeight(String label, Integer weight) throws InvalidWeightNumber, ElementNameNotExists;
	public void rmArc(String label) throws ElementNameNotExists;
	public void showArcs();
	public void renameArc(String oldName, String newName) throws RepeatedNameElement, ElementNameNotExists;

	public void fire(String label) throws ElementNameNotExists, NoFireableTransition;
	public List<String> fireableTransitions();
	public void showAllElements();

}

