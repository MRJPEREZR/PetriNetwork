package com.mapd.petri;

public class OutBouncerArc extends OutArc{
	
	OutBouncerArc(){
		super();
	};
	
	OutBouncerArc(Place place){
		super(place);
	};
	
	OutBouncerArc(Place place, int weight){
		super(place, weight);
	};
	
	@Override
	public Boolean isFireable() {
		// TO IMPLEMENTED
		return false;
		
	}
	
}
