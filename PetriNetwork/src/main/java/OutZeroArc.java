package main.java;

public class OutZeroArc extends OutArc {

	OutZeroArc(Place place) {
		super(place);
	}

	OutZeroArc(Place place, int weight) {
		super(place, weight);
	}
	
	@Override
	public void setIsActive() {
		//TODO Implement
	}

	@Override
	public void modifyTokens() {
		Place currentPlace = this.getPlace();
		if (this.getIsActive()) {
			//TODO Implement
		}
	}

}
