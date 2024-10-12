package mapd;

import exceptions.InvalidTokenNumber;
import exceptions.InvalidWeightNumber;

public class InArc extends Arc {

	InArc(Place place) {
		super(place);
	}

	InArc(Place place, int weight) throws InvalidWeightNumber {
		super(place, weight);
	}

	@Override
	public void modifyTokens() throws InvalidTokenNumber {
		Place currentPlace = this.getPlace();
		currentPlace.setTokens(currentPlace.getTokens() + this.getWeight());
	}

	@Override
    public void addToTransition(Transition transition) {
        transition.addInArc(this);
    }
	
	@Override
	public String toString() {
		return "Arc has weight " + this.getWeight();
	}
}
