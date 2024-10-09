package main.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PetriNetwork implements IPetriNetwork {

	private HashMap<String, Place> places; 
	private HashMap<String, Transition> transitions;
	private HashMap<String, Arc> arcs;

	PetriNetwork() {
		this.places = new HashMap<String, Place>(); 
		this.transitions = new HashMap<String, Transition>();
		this.arcs = new HashMap<String, Arc>();
	}

	public HashMap<String, Place> getPlaces() {
		return places;
	}

	public HashMap<String, Transition> getTransitions() {
		return transitions;
	}

	public HashMap<String, Arc> getArcs() {
		return arcs;
	}
	
	public Place getPlace(String label) {
		return places.get(label);
	}

	public Transition getTransition(String label) {
		return transitions.get(label);
	}

	public Arc getArc(String label) {
		return arcs.get(label);
	}

	@Override
	public void addPlace(String label) {
		Place place = new Place();
		this.places.put(label, place);
	}

	@Override
	public void addPlace(String label, Integer tokens) {
		Place place = new Place(tokens);
		this.places.put(label, place);
	}

	@Override
	public void rmPlace(String label) {
		this.places.remove(label);
	}

	@Override
	public void setPlaceTokens(String label, Integer tokens) {
		places.get(label).setTokens(tokens);
	}

	@Override
	public void addTransition(String label) {
		Transition transition = new Transition();
		this.transitions.put(label, transition);
	}

	@Override
	public void rmTransition(String label) {
		this.transitions.remove(label);
	}
	
	private Arc createArc(String type, Place place, Integer newWeight) {
		int weight = (newWeight == null) ? 1 : newWeight;
        switch (type.toLowerCase()) {
            case "in":
                return new InArc(place, weight);
            case "out":
                return new OutArc(place, weight);
            case "outzero":
                return new OutZeroArc(place, weight);
            case "outbounder":
                return new OutBouncerArc(place, weight);
            default:
            	throw new IllegalArgumentException("No valid" + type);
            	//TODO Exception
        }
    }

	@Override
	public void addArc(String label, String transitionLabel, String placeLabel, String type, Integer weight) {
		Place place = places.get(placeLabel);
		Transition transition = transitions.get(transitionLabel);
		Arc arc = createArc(type, place, weight);
        arc.addToTransition(transition);
	}

	@Override
	public void addArc(String label, String transitionLabel, String placeLabel, String type) {
		Place place = places.get(placeLabel);
		Transition transition = transitions.get(transitionLabel);
		Arc arc = createArc(type, place, null);
        arc.addToTransition(transition);
        this.arcs.put(label, arc);
	}
	
	@Override
	public void modArc(String label, String transition, String place, String type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setArcWeight(String label, Integer weight) {
		this.arcs.get(label).setWeight(weight);
	}

	@Override
	public void rmArc(String label) {
		arcs.remove(label);
	}
	
	@Override
	public List<String> fireableTransitions() {
		return transitions.entrySet().stream()
	            .filter(entry -> entry.getValue().isFireable())
	            .map(Map.Entry::getKey)
	            .collect(Collectors.toList());
	}

	@Override
	public void fire(String label) {
		transitions.get(label).fire();
	}

	@Override
	public void showPlaces() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void showTransitions() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void showArcs() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void showAllElements() {
		// TODO Auto-generated method stub
		
	}
}
