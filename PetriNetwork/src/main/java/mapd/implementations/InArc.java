package mapd.implementations;

import mapd.exceptions.InvalidTokenNumber;
import mapd.exceptions.InvalidWeightNumber;
import mapd.exceptions.RepeatedArc;

public class InArc extends Arc {

	public InArc(String label, Place place, Transition transition) throws InvalidWeightNumber, RepeatedArc {
		this(label, place, transition, 1);
		
	}

	public InArc(String label, Place place, Transition transition, int weight) throws InvalidWeightNumber, RepeatedArc {
		super(label, place, weight);
		addToTransition(transition);
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
