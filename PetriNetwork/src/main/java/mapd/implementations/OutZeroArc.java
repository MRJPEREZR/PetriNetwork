package mapd.implementations;

import mapd.exceptions.InvalidTokenNumber;
import mapd.exceptions.InvalidWeightNumber;
import mapd.exceptions.RepeatedArc;

public class OutZeroArc extends OutArc {

	public OutZeroArc(String label, Place place, Transition transition) throws InvalidWeightNumber, RepeatedArc {
		this(label, place, transition, 1);
	}

	public OutZeroArc(String label, Place place, Transition transition, int weight) throws InvalidWeightNumber, RepeatedArc {
		super(label, place, transition, weight);
	}

	@Override
	public void updateIsActive() {
		this.isActive = this.getPlace().getTokens() == 0; 
	}

	@Override
	public void modifyTokens() throws InvalidTokenNumber {
		Place currentPlace = this.getPlace();
		currentPlace.setTokens(0);
	}

	@Override
	public String toString() {
		return "Zero out arc has weight " + this.getWeight() + " and it is " + this.isActive();
	}

}
