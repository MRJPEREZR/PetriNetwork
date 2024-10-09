

public class InArc extends Arc{
	
	InArc(){
		super();
	};
	
	InArc(Place place){
		super(place);
	};
	
	InArc(Place place, int weight){
		super(place, weight);
	};
	
	@Override
	public Boolean isFireable() {
		return true;
		
	}

}
