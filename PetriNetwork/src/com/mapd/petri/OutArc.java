package com.mapd.petri;

public class OutArc extends Arc {
	
	OutArc(){
		super();
	};
	
	OutArc(Place place, Transition transition){
		super(place, transition);
	};
	
	OutArc(Place place, Transition transition, int weight){
		super(place, transition, weight);
	};
	
	@Override
	public Boolean isFireable() {
		
		Boolean fireable;
		
		Place sourcePlace = super.getPlace();
		int weight = super.getWeight();
		
		if (sourcePlace.getToken() >= weight) {
			fireable = true;
		}else {
			fireable = false;
		}
		
		return fireable;
		
	}
	
	

}
