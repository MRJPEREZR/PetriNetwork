package mapd.implementations;

import mapd.exceptions.*;

import java.util.ArrayList;
import java.util.List;

public class Transition {

    private List<InArc> inArcs;
    private List<OutArc> outArcs;
    private String label;
    private Boolean isFireable;

    /**
     * Constructor for the Transition class.
     * <p>
     * Initializes the transition with an empty list of input and output arcs,
     * a unique label, and sets the fireable status to false.
     *
     * @param label The unique label identifier for the transition.
     */
    public Transition(String label) {
        this.inArcs = new ArrayList<InArc>();
        this.outArcs = new ArrayList<OutArc>();
        this.label = label;
        this.isFireable = false;
    }

    /**
     * Sets the label of the transition.
     *
     * @param label The new label for the transition.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Returns the label of the transition.
     *
     * @return The unique label of the transition.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns the list of input arcs associated with the transition.
     *
     * @return List of InArc objects.
     */
    public List<InArc> getInArcs() {
        return inArcs;
    }

    /**
     * Returns the list of output arcs associated with the transition.
     *
     * @return List of OutArc objects.
     */
    public List<OutArc> getOutArcs() {
        return outArcs;
    }

    /**
     * Adds a new input arc to the transition.
     * <p>
     * If an input arc already exists with the same direction, a RepeatedArc exception is thrown.
     *
     * @param inArc The input arc to be added.
     * @throws RepeatedArc If an input arc with the same direction already exists.
     */
    public void addInArc(InArc inArc) throws RepeatedArc {
        if (!existedArc(this.inArcs, inArc)) {
            this.inArcs.add(inArc);
            updateIsFireable();
        } else {
            throw new RepeatedArc("An Arc in the same direction already exists");
        }
    }

    /**
     * Adds a new output arc to the transition.
     * <p>
     * If an output arc already exists with the same direction, a RepeatedArc exception is thrown.
     *
     * @param outArc The output arc to be added.
     * @throws RepeatedArc If an output arc with the same direction already exists.
     */
    public void addOutArc(OutArc outArc) throws RepeatedArc {
        if (!existedArc(this.outArcs, outArc)) {
            this.outArcs.add(outArc);
            updateIsFireable();
        } else {
            throw new RepeatedArc("An Arc in the same direction already exists");
        }
    }

    /**
     * Removes an input arc from the transition.
     *
     * @param inArc The input arc to be removed.
     */
    public void rmInArc(InArc inArc) {
        this.inArcs.remove(inArc);
    }

    /**
     * Removes an output arc from the transition and updates its fireable status.
     *
     * @param outArc The output arc to be removed.
     */
    public void rmOutArc(OutArc outArc) {
        this.outArcs.remove(outArc);
        updateIsFireable();
    }

    /**
     * Checks if the transition is currently fireable.
     *
     * @return True if the transition is fireable, false otherwise.
     */
    public Boolean isFireable() {
        return this.isFireable;
    }

    /**
     * Updates the fireable status of the transition based on the current
     * state of its output arcs. The transition becomes fireable if at least
     * one output arc is active or if there are no output arcs but input arcs exist.
     */
    public void updateIsFireable() {
        outArcs.stream().forEach(OutArc::updateIsActive);
        this.isFireable = outArcs.stream().anyMatch(arc -> arc.isActive()) || (outArcs.size() == 0 && inArcs.size() > 0);
    }

    /**
     * Fires the transition if it is fireable.
     * <p>
     * This method modifies the tokens in both input and output arcs and then
     * updates the fireable status. If the transition is not fireable, a message is printed.
     * @throws NoFireableTransition 
     */
    public void fire() throws NoFireableTransition {
        if (isFireable) {
            outArcs.stream().forEach(arc -> {
                try {
                    arc.modifyTokens();
                } catch (InvalidTokenNumber e) {
                    e.printStackTrace();
                }});
            inArcs.stream().forEach(arc -> {
                try {
                    arc.modifyTokens();
                } catch (InvalidTokenNumber e) {
                    e.printStackTrace();
                }});
            updateIsFireable();
        } else {
        	throw new NoFireableTransition("Transition is not fireable");
        }
    }

    /**
     * Provides a string representation of the transition, including its input and output arcs.
     *
     * @return A string representation of the transition's input and output arcs.
     */
    @Override
    public String toString() {
        return this.label + ", " + this.outArcs.size() + " in arcs, " + this.inArcs.size() + " out arcs";
    }

    /**
     * Checks if an arc with the same place already exists in the provided list.
     *
     * @param arcs   The list of arcs to check.
     * @param newArc The new arc to be added.
     * @return True if an arc with the same place already exists in the list; false otherwise.
     */
    private Boolean existedArc(List<? extends Arc> arcs, Arc newArc) {
        Place place = newArc.getPlace();
        return arcs.stream().anyMatch(arc -> arc.getPlace().getLabel().equals(place.getLabel()));
    }
}
