package mapd;

import exceptions.InvalidTokenNumber;
import exceptions.InvalidWeightNumber;

public class OutZeroArc extends OutArc {

	OutZeroArc(Place place) {
		super(place);
	}

	OutZeroArc(Place place, int weight) throws InvalidWeightNumber {
		super(place, weight);
	}
	
	@Override
	public void setIsActive() {
		// Out arc is active only if it has 0 tokens
		this.isActive = this.getPlace().getTokens() == 0; 
	}

	@Override
	public void modifyTokens() throws InvalidTokenNumber {
		Place currentPlace = this.getPlace();
		if (this.getIsActive()) {
			currentPlace.setTokens(0);
		}
	}

}
