package mapd.implementations;

import mapd.exceptions.InvalidTokenNumber;
import mapd.exceptions.InvalidWeightNumber;

public class OutBouncerArc extends OutArc {

	OutBouncerArc(String label, Place place) throws InvalidWeightNumber {
		super(label, place);
	};

	OutBouncerArc(String label, Place place, int weight) throws InvalidWeightNumber {
		super(label, place, weight);
	}
	
	@Override
	public void isActive() {
		this.isActive = this.getPlace().getTokens() >= 1;
	}

	@Override
	public void modifyTokens() throws InvalidTokenNumber {
		Place currentPlace = this.getPlace();
		if (this.getIsActive()) {
			currentPlace.setTokens(0);
		}
	}
	
	@Override
	public String toString() {
		return "Bouncer out arc has weight " + this.getWeight() + " and it is " + this.getIsActive();
	}

}
