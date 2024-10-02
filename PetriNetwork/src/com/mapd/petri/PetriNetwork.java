package com.mapd.petri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	public void addInArc(String label, String fromTransition, String toPlace) {
		InArc inArc = new InArc(places.get(toPlace), transitions.get(fromTransition)); 
		this.arcs.put(label, inArc);
		
	}

	@Override
	public void addOutArc(String label, String fromPlace, String toTransition) {
		OutArc outArc = new OutArc(places.get(fromPlace), transitions.get(toTransition));
		this.arcs.put(label, outArc);
		
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
		Transition t = transitions.get(label);
		Boolean isFireable = t.isFireable(); 
		if (isFireable) {
			Set<InArc> inArcs = t.getInArcs();
			Iterator<InArc> inArcsIterator = inArcs.iterator();
			
			Set<OutArc> outArcs = t.getOutArcs();
			Iterator<OutArc> outArcsIterator = outArcs.iterator();
			
			Place sourcePlace;
			int sourceToken = 0;
			int newSourceToken = 0;
			
			Place destinationPlace;
			int destinationToken = 0;
			int newDestinationToken = 0;
			
			System.out.println("Firing ...");
			System.out.println(inArcs);
			System.out.println(outArcs);
			
			
			while(outArcsIterator.hasNext()) {
				OutArc outArc = outArcsIterator.next(); 
				sourcePlace = outArc.getPlace();
				sourceToken = sourcePlace.getToken();
				newSourceToken = sourceToken - outArcsIterator.next().getWeight();
				outArcsIterator.next().getPlace().setToken(newSourceToken);
				
				System.out.println("Source places");
				System.out.println("Before >> After");
				// System.out.printf("%s: %d >> %s: %d", sourcePlace.getLabel(), sourceToken, sourcePlace.getLabel(), newSourceToken);
			}
			
			while(inArcsIterator.hasNext()) {
				destinationPlace = inArcsIterator.next().getPlace();
				destinationToken = destinationPlace.getToken();
				newDestinationToken = destinationToken + inArcsIterator.next().getWeight();
				inArcsIterator.next().getPlace().setToken(newDestinationToken);
				
				System.out.println("Destination places");
				System.out.println("Before >> After");
				// System.out.printf("%s: %d >> %s: %d", destinationPlace.getLabel(), destinationToken, destinationPlace.getLabel(), newDestinationToken);
			}
			
		}else {
			System.out.println("Transition is not fireable");
		}
		
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
