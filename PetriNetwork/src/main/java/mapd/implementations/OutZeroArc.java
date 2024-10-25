package mapd.implementations;

import mapd.exceptions.InvalidTokenNumber;
import mapd.exceptions.InvalidWeightNumber;

public class OutZeroArc extends OutArc {

	public OutZeroArc(String label, Place place) {
		super(label, place);
		setIsActive();
	}

	public OutZeroArc(String label, Place place, int weight) throws InvalidWeightNumber {
		super(label, place, weight);
		setIsActive();
	}

	@Override
	public void setIsActive() {
		this.isActive = this.getPlace().getTokens() == 0; 
	}

	@Override
	public void modifyTokens() throws InvalidTokenNumber {
		Place currentPlace = this.getPlace();
		currentPlace.setTokens(0);
	}

	@Override
	public String toString() {
		return "Zero out arc has weight " + this.getWeight() + " and it is " + this.getIsActive();
	}

}
