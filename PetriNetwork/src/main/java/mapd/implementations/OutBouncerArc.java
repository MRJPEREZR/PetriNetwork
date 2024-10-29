package mapd.implementations;

import mapd.exceptions.InvalidTokenNumber;
import mapd.exceptions.InvalidWeightNumber;
import mapd.exceptions.RepeatedArc;

public class OutBouncerArc extends OutArc {

    /**
     * Constructs an OutBouncerArc with a default weight of 1.
     * <p>
     * This arc type removes all tokens from its associated Place upon firing.
     *
     * @param label      The unique label of the OutBouncerArc.
     * @param place      The Place associated with this OutBouncerArc.
     * @param transition The Transition to which this OutBouncerArc will be connected.
     * @throws InvalidWeightNumber If the weight is invalid (less than 1).
     * @throws RepeatedArc         If an OutArc with the same label already exists in the Transition.
     */
    public OutBouncerArc(String label, Place place, Transition transition) throws InvalidWeightNumber, RepeatedArc {
        this(label, place, transition, 1);
    }

    /**
     * Constructs an OutBouncerArc with a specified weight.
     * <p>
     * This arc type removes all tokens from its associated Place upon firing.
     *
     * @param label      The unique label of the OutBouncerArc.
     * @param place      The Place associated with this OutBouncerArc.
     * @param transition The Transition to which this OutBouncerArc will be connected.
     * @param weight     The weight of the OutBouncerArc (primarily decorative, as it removes all tokens).
     * @throws InvalidWeightNumber If the weight is invalid (less than 1).
     * @throws RepeatedArc         If an OutArc with the same label already exists in the Transition.
     */
    public OutBouncerArc(String label, Place place, Transition transition, int weight) throws InvalidWeightNumber, RepeatedArc {
        super(label, place, transition, weight);
    }

    /**
     * Updates the active status of this OutBouncerArc.
     * <p>
     * The arc is active if there is at least one token in the associated Place.
     */
    @Override
    public void updateIsActive() {
        this.isActive = this.getPlace().getTokens() >= 1;
    }

    /**
     * Modifies the tokens in the associated Place by setting the token count to zero.
     * <p>
     * This method clears all tokens from the Place if the arc is active.
     *
     * @throws InvalidTokenNumber If the modification results in an invalid token count.
     */
    @Override
    public void modifyTokens() throws InvalidTokenNumber {
        Place currentPlace = this.getPlace();
        if (this.isActive()) {
            currentPlace.setTokens(0);
        }
    }

    /**
     * Provides a string representation of the OutBouncerArc, including its weight and active status.
     *
     * @return A string describing the OutBouncerArc, its weight, and its current active status.
     */
    @Override
    public String toString() {
        return "Bouncer out arc has weight " + this.getWeight() + " and it is " + this.isActive();
    }
}
