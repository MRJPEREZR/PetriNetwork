package com.mapd.petri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PetriNetwork implements IPetriNetwork{
	
	private HashMap<String, Place> places; 
	private HashMap<String, Transition> transitions;
	private HashMap<String, Arc> arcs;
	
	PetriNetwork(){
		this.places = new HashMap<String, Place>(); 
		this.transitions = new HashMap<String, Transition>();
		this.arcs = new HashMap<String, Arc>();
	};
	
	
	public HashMap<String, Place> getPlaces() {
		return places;
	}
	public void setPlaces(HashMap<String, Place> places) {
		this.places = places;
	}
	public HashMap<String, Transition> getTransitions() {
		return transitions;
	}
	public void setTransitions(HashMap<String, Transition> transitions) {
		this.transitions = transitions;
	}
	public HashMap<String, Arc> getArcs() {
		return arcs;
	}
	public void setArcs(HashMap<String, Arc> arcs) {
		this.arcs = arcs;
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
	public void rmPlace(String label) {
		this.places.remove(label);
		
	}

	@Override
	public void setPlaceToken(String label, int token) {
		places.get(label).setToken(token);
		
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
	
	@Override
	public void addInArc(String label, String toPlace, String fromTransition) {
		InArc inArc = new InArc(places.get(toPlace)); 
		this.arcs.put(label, inArc);
		this.transitions.get(fromTransition).addInArc(inArc);
		
	}

	@Override
	public void addOutArc(String label, String fromPlace, String toTransition) {
		OutArc outArc = new OutArc(places.get(fromPlace));
		this.arcs.put(label, outArc);
		this.transitions.get(toTransition).addOutArc(outArc);
	}

	@Override
	public void setArcWeight(String label, int weight) {
		this.arcs.get(label).setWeight(weight);
	}

	@Override
	public void rmArc(String label) {
		arcs.remove(label);
	}

	@Override
	public void fire(String label) {
		transitions.get(label).fire();
	}

	@Override
	public List<String> fireableTransitions() {
		List<String> fireableTransitions = new ArrayList<String>();
		Iterator<Map.Entry<String, Transition>> transitionIterator = transitions.entrySet().iterator();
		
		while(transitionIterator.hasNext()) {
			Map.Entry<String, Transition> entry = transitionIterator.next();
			if (entry.getValue().isFireable()) {
				fireableTransitions.add(entry.getKey());
			}
		}
		return fireableTransitions;
	}
	
	
	
	
	
	
	
	

}
