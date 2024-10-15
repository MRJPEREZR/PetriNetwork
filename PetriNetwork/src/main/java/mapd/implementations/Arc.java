package mapd.implementations;

import mapd.exceptions.InvalidTokenNumber;
import mapd.exceptions.InvalidWeightNumber;

public abstract class Arc {

	private Place place;
	private int weight;
	
	private Boolean isValidWeight(Integer weight) {
		return weight >= 1;
	}

	Arc(Place place) {
		this.place = place;
		this.weight = 1;
	}

	Arc(Place place, int weight) throws InvalidWeightNumber {
		this.place = place;
		if (isValidWeight(weight)) {
			this.weight = weight;
		}else {
			throw new InvalidWeightNumber("Invalid weight < 1");
		}
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) throws InvalidWeightNumber {
		if (isValidWeight(weight)) {
			this.weight = weight;
		}else {
			throw new InvalidWeightNumber("Invalid weight < 1");
		}
	}

	public abstract void modifyTokens() throws InvalidTokenNumber;
	public abstract void addToTransition(Transition transition);

}
