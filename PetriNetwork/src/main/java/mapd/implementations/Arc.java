package mapd.implementations;

import mapd.exceptions.InvalidTokenNumber;
import mapd.exceptions.InvalidWeightNumber;
import mapd.exceptions.RepeatedArc;

public abstract class Arc {

	private Place place;
	private int weight;
	private String label;
	
	private Boolean isValidWeight(Integer weight) {
		return weight >= 1;
	}

	Arc(String label, Place place) {
		this.place = place;
		this.weight = 1;
		this.label = label;
	}

	Arc(String label, Place place, int weight) throws InvalidWeightNumber {
		this.place = place;
		this.label = label;
		if (isValidWeight(weight)) {
			this.weight = weight;
		} else {
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
	
	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setWeight(int weight) throws InvalidWeightNumber {
		if (isValidWeight(weight)) {
			this.weight = weight;
		} else {
			throw new InvalidWeightNumber("Invalid weight < 1");
		}
	}

	public abstract void modifyTokens() throws InvalidTokenNumber;
	public abstract void addToTransition(Transition transition) throws RepeatedArc;

}
