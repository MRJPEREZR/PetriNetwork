package mapd.implementations;

import mapd.exceptions.InvalidTokenNumber;
import mapd.exceptions.InvalidWeightNumber;

public class OutBouncerArc extends OutArc {

	OutBouncerArc(Place place) {
		super(place);
	};

	OutBouncerArc(Place place, int weight) throws InvalidWeightNumber {
		super(place, weight);
	}
	
	@Override
	public void setIsActive() {
		// If at least there is 1 token, the out arc is activated
		isActive = this.getPlace().getTokens() >= 1;
	}

	@Override
	public void modifyTokens() throws InvalidTokenNumber {
		// When the out arc is activate, all tokens are removed
		Place currentPlace = this.getPlace();
		if (this.getIsActive()) {
			currentPlace.setTokens(0);
		}
	}

}
