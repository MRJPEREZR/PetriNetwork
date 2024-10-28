package mapd.implementations;

import mapd.exceptions.InvalidTokenNumber;
import mapd.exceptions.InvalidWeightNumber;
import mapd.exceptions.RepeatedArc;

public class OutBouncerArc extends OutArc {

	public OutBouncerArc(String label, Place place, Transition transition) throws InvalidWeightNumber, RepeatedArc {
		this(label, place, transition, 1);
	};

	public OutBouncerArc(String label, Place place, Transition transition, int weight) throws InvalidWeightNumber, RepeatedArc {
		super(label, place, transition, weight);
	}
	
	@Override
	public void updateIsActive() {
		this.isActive = this.getPlace().getTokens() >= 1;
	}

	@Override
	public void modifyTokens() throws InvalidTokenNumber {
		Place currentPlace = this.getPlace();
		if (this.isActive()) {
			currentPlace.setTokens(0);
		}
	}
	
	@Override
	public String toString() {
		return "Bouncer out arc has weight " + this.getWeight() + " and it is " + this.isActive();
	}

}
