package mapd.implementations;

import mapd.exceptions.InvalidTokenNumber;
import mapd.exceptions.InvalidWeightNumber;

public class OutZeroArc extends OutArc {

	OutZeroArc(String label, Place place) {
		super(label, place);
		setIsActive();
	}

	OutZeroArc(String label, Place place, int weight) throws InvalidWeightNumber {
		super(label, place, weight);
		setIsActive();
	}

	@Override
	public void setIsActive() {
		this.isActive = this.getPlace().getTokens() == 0; 
	}

	@Override
	public void modifyTokens() throws InvalidTokenNumber {
	}

	@Override
	public String toString() {
		return "Zero out arc has weight " + this.getWeight() + " and it is " + this.getIsActive();
	}

}
