package mapd.implementations;

import mapd.exceptions.*;

public class Place {
	
    private Integer tokens;
    private String label;

    /**
     * Constructs a Place with a default token count of 0.
     *
     * @param label The unique label of the Place.
     * @throws InvalidTokenNumber If the initial token count is invalid (less than 0).
     */
    public Place(String label) throws InvalidTokenNumber {
        this(label, 0);
    }

    /**
     * Constructs a Place with a specified token count.
     *
     * @param label  The unique label of the Place.
     * @param tokens The initial number of tokens in the Place. Must be 0 or greater.
     * @throws InvalidTokenNumber If the initial token count is invalid (less than 0).
     */
    public Place(String label, Integer tokens) throws InvalidTokenNumber {
        if (isValidTokens(tokens)) {
            this.tokens = tokens;
        } else {
            throw new InvalidTokenNumber("Invalid token < 0");
        }
        this.label = label;
    }

    /**
     * Sets a new label for the Place.
     *
     * @param label The new label for the Place.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Returns the label of the Place.
     *
     * @return The unique label of the Place.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns the current token count of the Place.
     *
     * @return The integer token count of the Place.
     */
    public Integer getTokens() {
        return tokens;
    }

    /**
     * Sets a new token count for the Place.
     * <p>
     * Validates the token count before setting it. If the count is invalid, an exception is thrown.
     *
     * @param tokens The new token count for the Place. Must be 0 or greater.
     * @throws InvalidTokenNumber If the token count is invalid (less than 0).
     */
    public void setTokens(Integer tokens) throws InvalidTokenNumber {
        if (isValidTokens(tokens)) {
            this.tokens = tokens;
        } else {
            throw new InvalidTokenNumber("Invalid token < 0");
        }
    }

    /**
     * Provides a string representation of the Place, including its token count.
     *
     * @return A string describing the Place and its current token count.
     */
    @Override
    public String toString() {
        return "Place has " + this.tokens + " tokens";
    }

    /**
     * Checks if the token count is valid.
     *
     * @param tokens The token count to validate.
     * @return True if the token count is valid (0 or greater), false otherwise.
     */
    private Boolean isValidTokens(Integer tokens) {
        return tokens >= 0;
    }
}
