package com.mapd.petri;

public class InArc extends Arc{
	
	InArc(){
		super();
	};
	
	InArc(Place place, Transition transition){
		super(place, transition);
	};
	
	InArc(Place place, Transition transition, int weight){
		super(place, transition, weight);
	};
	
	@Override
	public Boolean isFireable() {
		return true;
		
	}

}
