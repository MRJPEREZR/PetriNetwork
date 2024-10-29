package mapd.implementations;

import mapd.exceptions.InvalidTokenNumber;
import mapd.exceptions.InvalidWeightNumber;
import mapd.exceptions.RepeatedArc;

public class OutZeroArc extends OutArc {

    /**
     * Constructs an OutZeroArc with a default weight of 1.
     * <p>
     * This arc type is only active when the associated Place has zero tokens. It implements the
     * Null Object pattern in `modifyTokens` to do nothing when fired.
     *
     * @param label      The unique label of the OutZeroArc.
     * @param place      The Place associated with this OutZeroArc.
     * @param transition The Transition to which this OutZeroArc will be connected.
     * @throws InvalidWeightNumber If the weight is invalid (less than 1).
     * @throws RepeatedArc         If an OutArc with the same label already exists in the Transition.
     */
    public OutZeroArc(String label, Place place, Transition transition) throws InvalidWeightNumber, RepeatedArc {
        this(label, place, transition, 1);
    }

    /**
     * Constructs an OutZeroArc with a specified weight.
     * <p>
     * This arc type is only active when the associated Place has zero tokens. It implements the
     * Null Object pattern in `modifyTokens` to do nothing when fired.
     *
     * @param label      The unique label of the OutZeroArc.
     * @param place      The Place associated with this OutZeroArc.
     * @param transition The Transition to which this OutZeroArc will be connected.
     * @param weight     The weight of the OutZeroArc (primarily decorative, as no tokens are modified).
     * @throws InvalidWeightNumber If the weight is invalid (less than 1).
     * @throws RepeatedArc         If an OutArc with the same label already exists in the Transition.
     */
    public OutZeroArc(String label, Place place, Transition transition, int weight) throws InvalidWeightNumber, RepeatedArc {
        super(label, place, transition, weight);
    }

    /**
     * Updates the active status of this OutZeroArc.
     * <p>
     * The arc is active only if the token count in the associated Place is zero.
     */
    @Override
    public void updateIsActive() {
        this.isActive = this.getPlace().getTokens() == 0;
    }

    /**
     * Does nothing when called, implementing the Null Object pattern.
     * <p>
     * Since this arc type should not modify tokens, this method is intentionally empty.
     */
    @Override
    public void modifyTokens() throws InvalidTokenNumber {
        // Null Object pattern: intentionally does nothing.
    }

    /**
     * Provides a string representation of the OutZeroArc, including its weight and active status.
     *
     * @return A string describing the OutZeroArc, its weight, and its current active status.
     */
    @Override
    public String toString() {
        return "zero out arc with weight " + this.getWeight() + " (from " + this.getPlace().toString() + " to transition)";
    }
}
