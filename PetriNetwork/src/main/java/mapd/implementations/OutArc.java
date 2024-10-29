package mapd.implementations;

import mapd.exceptions.InvalidTokenNumber;
import mapd.exceptions.InvalidWeightNumber;
import mapd.exceptions.RepeatedArc;

public class OutArc extends Arc {

	protected Boolean isActive;

	public OutArc(String label, Place place, Transition transition) throws InvalidWeightNumber, RepeatedArc {
		this(label, place, transition, 1);
	}

	public OutArc(String label, Place place, Transition transition, int weight) throws InvalidWeightNumber, RepeatedArc {
		super(label, place, weight);
		this.updateIsActive();
		addToTransition(transition);
	}

	public Boolean isActive() {
		return isActive;
	}

	public void updateIsActive() {
		isActive = this.getPlace().getTokens() - this.getWeight() >= 0;
	}

	@Override
	public void modifyTokens() throws InvalidTokenNumber {
		Place currentPlace = this.getPlace();
		if (isActive) {
			currentPlace.setTokens(currentPlace.getTokens() - this.getWeight());
			this.updateIsActive();
		}
	}

	@Override
    public void addToTransition(Transition transition) throws RepeatedArc {
        transition.addOutArc(this);
    }
	
	@Override
    public void setWeight(int weight) throws InvalidWeightNumber {
        super.setWeight(weight);
        this.updateIsActive();
    }

	@Override
	public String toString() {
		return "Conventional out arc has weight " + this.getWeight() + " and it is " + this.isActive();
	}

}
