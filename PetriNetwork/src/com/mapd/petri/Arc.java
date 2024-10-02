package com.mapd.petri;

public abstract class Arc {
	
	private Place place;
	private Transition transition;
	private int weight;
	
	Arc(){
		this.weight = 1;
	};
	
	Arc(Place place, Transition transition){
		this.place = place;
		this.transition = transition;
		this.weight = 1;
	};
	
	Arc(Place place, Transition transition, int weight){
		this.place = place;
		this.transition = transition;
		this.weight = weight;
	};
	
	public Place getPlace() {
		return place;
	}
	public void setPlace(Place place) {
		this.place = place;
	}
	public Transition getTransition() {
		return transition;
	}
	public void setTransition(Transition transition) {
		this.transition = transition;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public abstract Boolean isFireable();
}
