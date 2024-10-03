package com.mapd.petri;

public class OutZeroArc extends OutArc{
	
	OutZeroArc(){
		super();
	};
	
	OutZeroArc(Place place){
		super(place);
	};
	
	OutZeroArc(Place place, int weight){
		super(place, weight);
	};
	
	@Override
	public Boolean isFireable() {
		// TO IMPLEMENTED
		return false;
		
	}

}
