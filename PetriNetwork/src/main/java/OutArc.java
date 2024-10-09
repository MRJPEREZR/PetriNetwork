package main.java;

public class OutArc extends Arc {

	private Boolean isActive;

	OutArc(Place place) {
		super(place);
		setIsActive();
	}
	
	OutArc(Place place, int weight) {
		super(place, weight);
		setIsActive();
	}

	public Boolean getIsActive() {
		return isActive;
	}
	
	public void setIsActive() {
		this.isActive = this.getPlace().getTokens() - this.getWeight() >= 0;
	}

	@Override
	public void modifyTokens() {
		Place currentPlace = this.getPlace();
		if (isActive) {
			currentPlace.setTokens(currentPlace.getTokens() - this.getWeight());
		}
	}
	
	@Override
    public void addToTransition(Transition transition) {
        transition.addOutArc(this);
    }
	
	@Override
	public String toString() {
		return "Arc has weight " + this.getWeight() + "and it is " + this.getIsActive();
	}

}
