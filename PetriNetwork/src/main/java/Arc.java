

public abstract class Arc {
	
	private Place place;
	private int weight;
	
	Arc(){
		this.weight = 1;
	};
	
	Arc(Place place){
		this.place = place;
		this.weight = 1;
	};
	
	Arc(Place place, int weight){
		this.place = place;
		this.weight = weight;
	};
	
	public Place getPlace() {
		return place;
	}
	public void setPlace(Place place) {
		this.place = place;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public abstract Boolean isFireable();
	
	@Override
	public String toString() {
		return "Arc has weight " + this.weight;
	}
}
