

public class OutArc extends Arc {
	
	OutArc(){
		super();
	};
	
	OutArc(Place place){
		super(place);
	};
	
	OutArc(Place place, int weight){
		super(place, weight);
	};
	
	@Override
	public Boolean isFireable() {
		
		Boolean fireable;
		
		Place sourcePlace = super.getPlace();
		int weight = super.getWeight();
		
		if (sourcePlace.getToken() >= weight) {
			fireable = true;
		}else {
			fireable = false;
		}
		
		return fireable;
		
	}
	
	

}
