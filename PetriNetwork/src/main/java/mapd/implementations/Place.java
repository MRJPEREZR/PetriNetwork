package mapd.implementations;

import mapd.exceptions.*;

public class Place {
	
	private Integer tokens;
	private String label;
	
	private Boolean isValidTokens(Integer tokens) {
		return tokens >= 0;
	}
	
	public Place(String label) {
		this.tokens = 0;
		this.label = label;
	}

	public Place(String label, Integer tokens) throws InvalidTokenNumber {
		 if (isValidTokens(tokens)) {
			 this.tokens = tokens;
		 } else {
			 throw new InvalidTokenNumber("Invalid token < 0");
		 }
		 this.label = label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
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
		return "Place has " + this.tokens + " tokens";
	}
}
