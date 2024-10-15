package mapd.implementations;

import mapd.exceptions.*;

public class Place {
	
	private Integer tokens;
	
	private Boolean isValidTokens(Integer tokens) {
		return tokens >= 0;
	}
	
	Place() {
		this.tokens = 0;
	}

	Place(Integer tokens) throws InvalidTokenNumber {
		 if (isValidTokens(tokens)) {
			 this.tokens = tokens;
		 } else {
			 throw new InvalidTokenNumber("Invalid token < 0");
		 }
	}

	public Integer getTokens() {
		return tokens;
	}

	public void setTokens(Integer tokens) throws InvalidTokenNumber {
		if (isValidTokens(tokens)) {
			 this.tokens = tokens;
		 }else {
			 throw new InvalidTokenNumber("Invalid token < 0");
		 }
	}

	@Override
	public String toString() {
		return "Place has " + this.tokens;
	}
}
