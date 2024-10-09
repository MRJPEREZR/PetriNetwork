package main.java;

import java.util.HashMap;
import java.util.List;

public class Dashboard {
	
	public static void main (String[] args) {

		PetriNetwork net = new PetriNetwork();
		List<String> fireableTransitions;

		net.addPlace("p1");
		net.setPlaceTokens("p1", 1);
		
		net.addPlace("p2", 3);

		net.addPlace("p3");
		
		net.addPlace("p4");
		net.setPlaceTokens("p4", 1);
		
		net.addTransition("t1");
		
		net.addArc("a1", "t1", "p1", "out");

		net.addArc("a2", "t1", "p2", "out", 2);

		net.addArc("a3", "t1", "p3", "in");
		net.setArcWeight("a3", 3);

		net.addArc("a4", "t1", "p4", "in");
		
		fireableTransitions = net.fireableTransitions();
		System.out.println(fireableTransitions);
		
		HashMap<String, Place> places_before = net.getPlaces();
		System.out.println(places_before);
		net.fire("t1");
		HashMap<String, Place> places_after = net.getPlaces();
		System.out.println(places_after);
		System.out.println(net.getTransitions());
		net.fire("t1");
	}

}
