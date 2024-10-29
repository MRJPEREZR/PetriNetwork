package mapd.implementations;

import mapd.exceptions.InvalidTokenNumber;
import mapd.exceptions.InvalidWeightNumber;
import mapd.exceptions.RepeatedArc;

public class OutArc extends Arc {

    protected Boolean isActive;

    /**
     * Constructs an OutArc with a default weight of 1.
     * <p>
     * This arc type subtracts tokens from its associated Place upon firing, provided that
     * there are sufficient tokens. The arc is active if it can subtract tokens without causing
     * a negative token count.
     *
     * @param label      The unique label of the OutArc.
     * @param place      The Place associated with this OutArc.
     * @param transition The Transition to which this OutArc will be connected.
     * @throws InvalidWeightNumber If the weight is invalid (less than 1).
     * @throws RepeatedArc         If an OutArc with the same label already exists in the Transition.
     */
    public OutArc(String label, Place place, Transition transition) throws InvalidWeightNumber, RepeatedArc {
        this(label, place, transition, 1);
    }

    /**
     * Constructs an OutArc with a specified weight.
     * <p>
     * This arc type subtracts tokens from its associated Place upon firing, provided that
     * there are sufficient tokens. The arc is active if it can subtract tokens without causing
     * a negative token count.
     *
     * @param label      The unique label of the OutArc.
     * @param place      The Place associated with this OutArc.
     * @param transition The Transition to which this OutArc will be connected.
     * @param weight     The weight of the OutArc, determining how many tokens it subtracts from the Place upon firing.
     * @throws InvalidWeightNumber If the weight is invalid (less than 1).
     * @throws RepeatedArc         If an OutArc with the same label already exists in the Transition.
     */
    public OutArc(String label, Place place, Transition transition, int weight) throws InvalidWeightNumber, RepeatedArc {
        super(label, place, weight);
        this.updateIsActive();
        addToTransition(transition);
    }

    /**
     * Checks if this OutArc is currently active.
     * <p>
     * An OutArc is active if there are enough tokens in the associated Place to fire
     * the arc without causing a negative token count.
     *
     * @return True if the OutArc is active, false otherwise.
     */
    public Boolean isActive() {
        return isActive;
    }

    /**
     * Updates the active status of this OutArc.
     * <p>
     * The arc becomes active if the number of tokens in the associated Place is greater than or equal
     * to the arc's weight.
     */
    public void updateIsActive() {
        isActive = this.getPlace().getTokens() - this.getWeight() >= 0;
    }

    /**
     * Modifies the tokens in the associated Place by subtracting tokens based on the weight of this OutArc.
     * <p>
     * This method only subtracts tokens if the arc is active, ensuring no negative token count occurs.
     * The active status is updated after the modification.
     *
     * @throws InvalidTokenNumber If the resulting token count in the Place is invalid.
     */
    @Override
    public void modifyTokens() throws InvalidTokenNumber {
        Place currentPlace = this.getPlace();
        if (isActive) {
            currentPlace.setTokens(currentPlace.getTokens() - this.getWeight());
            this.updateIsActive();
        }
    }

    /**
     * Adds this OutArc to the specified Transition.
     * <p>
     * Registers this arc as an output arc to the Transition, enabling the Transition to use this OutArc's functionality.
     *
     * @param transition The Transition to which this OutArc is being added.
     * @throws RepeatedArc If an OutArc with the same direction already exists in the Transition.
     */
    @Override
    public void addToTransition(Transition transition) throws RepeatedArc {
        transition.addOutArc(this);
    }

    /**
     * Sets a new weight for this OutArc and updates its active status.
     * <p>
     * Validates the weight before setting it. If the weight is invalid, an exception is thrown.
     *
     * @param weight The new weight to set for the OutArc. Must be 1 or greater.
     * @throws InvalidWeightNumber If the weight is invalid (less than 1).
     */
    @Override
    public void setWeight(int weight) throws InvalidWeightNumber {
        super.setWeight(weight);
        this.updateIsActive();
    }

    /**
     * Provides a string representation of the OutArc, including its weight and active status.
     *
     * @return A string describing the OutArc, its weight, and its current active status.
     */
    @Override
    public String toString() {
    	return "simple out arc with weight " + this.getWeight() + " (from " + this.getPlace().toString() + " to transition)";
    }
}
