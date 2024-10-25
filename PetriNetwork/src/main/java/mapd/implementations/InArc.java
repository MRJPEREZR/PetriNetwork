package mapd.implementations;

import mapd.exceptions.InvalidTokenNumber;
import mapd.exceptions.InvalidWeightNumber;
import mapd.exceptions.RepeatedArc;

public class InArc extends Arc {

	public InArc(String label, Place place) throws InvalidWeightNumber {
		super(label, place);
	}

	public InArc(String label, Place place, int weight) throws InvalidWeightNumber {
		super(label, place, weight);
	}

	@Override
	public void modifyTokens() throws InvalidTokenNumber {
		Place currentPlace = this.getPlace();
		currentPlace.setTokens(currentPlace.getTokens() + this.getWeight());
	}

	@Override
    public void addToTransition(Transition transition) throws RepeatedArc {
        transition.addInArc(this);
    }

	@Override
	public String toString() {
		return "In arc has weight " + this.getWeight();
	}
}
