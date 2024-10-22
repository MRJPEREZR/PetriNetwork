package mapd.implementations;

import mapd.exceptions.InvalidTokenNumber;
import mapd.exceptions.InvalidWeightNumber;
import mapd.exceptions.RepeatedArc;

public class OutArc extends Arc {

	protected Boolean isActive;

	OutArc(String label, Place place) {
		super(label, place);
		setIsActive();
	}

	OutArc(String label, Place place, int weight) throws InvalidWeightNumber {
		super(label, place, weight);
		setIsActive();
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive() {
		this.isActive = this.getPlace().getTokens() - this.getWeight() >= 0;
	}

	@Override
	public void modifyTokens() throws InvalidTokenNumber {
		Place currentPlace = this.getPlace();
		if (isActive) {
			currentPlace.setTokens(currentPlace.getTokens() - this.getWeight());
			this.setIsActive();
		}
	}

	@Override
    public void addToTransition(Transition transition) throws RepeatedArc {
        transition.addOutArc(this);
    }

	@Override
	public String toString() {
		return "Conventional out arc has weight " + this.getWeight() + " and it is " + this.getIsActive();
	}

}
