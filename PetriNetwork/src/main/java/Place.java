

public class Place {
	
	private int token;
	
	Place(){
		this.token = 0;
	};
	
	Place(int token){
		this.token = token;
	}

	public int getToken() {
		return token;
	}

	public void setToken(int token) {
		this.token = token;
	}
	
	@Override
	public String toString() {
		return "Place has " + this.token;
	}

}
