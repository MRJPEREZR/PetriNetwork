package com.mapd.petri;

import java.util.HashMap;
import java.util.List;

public class Dashboard {
	
	public static void main (String[] args) {
		
		PetriNetwork net = new PetriNetwork();
		List<String> fireableTransitions;
		
		net.addPlace("p1");
		net.setPlaceToken("p1", 1);
		
		net.addPlace("p2");
		net.setPlaceToken("p2", 3);
		
		net.addPlace("p3");
		
		net.addPlace("p4");
		net.setPlaceToken("p4", 1);
		
		net.addTransition("t1");
		
		net.addOutArc("a1", "p1", "t1");
		
		net.addOutArc("a2", "p2", "t1");
		net.setArcWeight("a2", 2);
		
		
		net.addInArc("a3", "p3", "t1");
		net.setArcWeight("a3", 3);
		
		net.addInArc("a4", "p4", "t1");
		
		fireableTransitions = net.fireableTransitions();
		
		System.out.println(fireableTransitions);
		
		HashMap<String, Place> places_before = net.getPlaces();
		System.out.println(places_before);
		
		HashMap<String, Arc> arcs = net.getArcs();
		System.out.println(arcs);
		
		net.fire(fireableTransitions.get(0));
		
		HashMap<String, Place> places_after = net.getPlaces();
		System.out.println(places_after);
		
		net.rmArc("a3");

		System.out.println(net.getArcs());

		System.out.println(net.getTransition("t1").toString());
		
		
		
	}

}
