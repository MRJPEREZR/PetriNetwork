package main.java;

public class OutBouncerArc extends OutArc {

	OutBouncerArc(Place place) {
		super(place);
	};

	OutBouncerArc(Place place, int weight) {
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
