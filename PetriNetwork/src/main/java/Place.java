package main.java;

public class Place {
	
	private Integer tokens;
	
	private Boolean isValidTokens(Integer tokens) {
		return tokens >= 0;
	}
	
	Place() {
		this.tokens = 0;
	}

	Place(Integer tokens) {
		 if (isValidTokens(tokens)) {
			 this.tokens = tokens;
		 } //TODO throw exception
	}

	public Integer getTokens() {
		return tokens;
	}

	public void setTokens(Integer tokens) {
		if (isValidTokens(tokens)) {
			 this.tokens = tokens;
		 } //TODO throw exception
	}

	@Override
	public String toString() {
		return "Place has " + this.tokens;
	}
}
