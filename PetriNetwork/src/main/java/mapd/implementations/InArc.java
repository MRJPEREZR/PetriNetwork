package mapd.implementations;

import mapd.exceptions.InvalidTokenNumber;
import mapd.exceptions.InvalidWeightNumber;
import mapd.exceptions.RepeatedArc;

public class InArc extends Arc {

    /**
     * Constructs an InArc with a default weight of 1.
     * <p>
     * This arc type adds tokens to its associated Place upon firing.
     *
     * @param label      The unique label of the InArc.
     * @param place      The Place associated with this InArc.
     * @param transition The Transition to which this InArc will be connected.
     * @throws InvalidWeightNumber If the weight is invalid (less than 1).
     * @throws RepeatedArc         If an InArc with the same label already exists in the Transition.
     */
    public InArc(String label, Place place, Transition transition) throws InvalidWeightNumber, RepeatedArc {
        this(label, place, transition, 1);
    }

    /**
     * Constructs an InArc with a specified weight.
     * <p>
     * This arc type adds tokens to its associated Place upon firing.
     *
     * @param label      The unique label of the InArc.
     * @param place      The Place associated with this InArc.
     * @param transition The Transition to which this InArc will be connected.
     * @param weight     The weight of the InArc, determining how many tokens it adds to the Place upon firing.
     * @throws InvalidWeightNumber If the weight is invalid (less than 1).
     * @throws RepeatedArc         If an InArc with the same label already exists in the Transition.
     */
    public InArc(String label, Place place, Transition transition, int weight) throws InvalidWeightNumber, RepeatedArc {
        super(label, place, weight);
        addToTransition(transition);
    }

    /**
     * Modifies the tokens in the associated Place by adding tokens based on the weight of this InArc.
     *
     * @throws InvalidTokenNumber If the resulting token count in the Place is invalid.
     */
    @Override
    public void modifyTokens() throws InvalidTokenNumber {
        Place currentPlace = this.getPlace();
        currentPlace.setTokens(currentPlace.getTokens() + this.getWeight());
    }

    /**
     * Adds this InArc to the specified Transition.
     * <p>
     * Registers this arc as an input arc to the Transition, enabling the Transition to use this InArc's functionality.
     *
     * @param transition The Transition to which this InArc is being added.
     * @throws RepeatedArc If an InArc with the same direction already exists in the Transition.
     */
    @Override
    public void addToTransition(Transition transition) throws RepeatedArc {
        transition.addInArc(this);
    }

    /**
     * Provides a string representation of the InArc, including its weight.
     *
     * @return A string describing the InArc and its weight.
     */
    @Override
    public String toString() {
    	return "simple in arc with weight " + this.getWeight() + " (transition to " + this.getPlace().toString() + ")";
    }
}
