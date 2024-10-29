package mapd.implementations;

import mapd.exceptions.InvalidTokenNumber;
import mapd.exceptions.InvalidWeightNumber;
import mapd.exceptions.RepeatedArc;

public abstract class Arc {

    private Place place;
    private int weight;
    private String label;

    /**
     * Constructs an Arc with a default weight of 1.
     *
     * @param label The unique label of the Arc.
     * @param place The Place associated with this Arc.
     * @throws InvalidWeightNumber If the weight is invalid (less than 1).
     * @throws RepeatedArc         If the Arc with the same label already exists.
     */
    public Arc(String label, Place place) throws InvalidWeightNumber, RepeatedArc {
        this(label, place, 1);
    }

    /**
     * Constructs an Arc with a specified weight.
     *
     * @param label  The unique label of the Arc.
     * @param place  The Place associated with this Arc.
     * @param weight The weight of the Arc. Must be 1 or greater.
     * @throws InvalidWeightNumber If the weight is invalid (less than 1).
     * @throws RepeatedArc         If an Arc with the same label already exists.
     */
    public Arc(String label, Place place, int weight) throws InvalidWeightNumber, RepeatedArc {
        this.place = place;
        this.label = label;
        if (isValidWeight(weight)) {
            this.weight = weight;
        } else {
            throw new InvalidWeightNumber("Invalid weight < 1");
        }
    }

    /**
     * Returns the Place associated with this Arc.
     *
     * @return The Place object.
     */
    public Place getPlace() {
        return place;
    }

    /**
     * Returns the weight of this Arc.
     *
     * @return The integer weight of the Arc.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Sets a new label for this Arc.
     *
     * @param label The new label for the Arc.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Returns the label of this Arc.
     *
     * @return The unique label identifier of the Arc.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets a new weight for the Arc.
     * <p>
     * Validates the weight before setting it. If the weight is invalid, an exception is thrown.
     *
     * @param weight The new weight to set for the Arc. Must be 1 or greater.
     * @throws InvalidWeightNumber If the weight is invalid (less than 1).
     */
    public void setWeight(int weight) throws InvalidWeightNumber {
        if (isValidWeight(weight)) {
            this.weight = weight;
        } else {
            throw new InvalidWeightNumber("Invalid weight < 1");
        }
    }

    /**
     * Modifies the tokens in the Place associated with this Arc.
     * <p>
     * Implementations will define the specific behavior for how tokens are modified.
     *
     * @throws InvalidTokenNumber If the token modification results in an invalid token count.
     */
    public abstract void modifyTokens() throws InvalidTokenNumber;

    /**
     * Adds this Arc to a specified Transition.
     * <p>
     * Implementations should define how the Arc interacts with the Transition.
     *
     * @param transition The Transition to which the Arc will be added.
     * @throws RepeatedArc If an Arc with the same direction already exists in the Transition.
     */
    public abstract void addToTransition(Transition transition) throws RepeatedArc;

    /**
     * Validates the weight of the Arc.
     *
     * @param weight The weight to validate.
     * @return True if the weight is valid (greater than or equal to 1), false otherwise.
     */
    private Boolean isValidWeight(Integer weight) {
        return weight >= 1;
    }
}
