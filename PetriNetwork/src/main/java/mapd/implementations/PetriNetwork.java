package mapd.implementations;

import mapd.interfaces.IPetriNetwork;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import mapd.exceptions.ElementNameNotExists;
import mapd.exceptions.InvalidTokenNumber;
import mapd.exceptions.InvalidWeightNumber;
import mapd.exceptions.NoFireableTransition;
import mapd.exceptions.RepeatedArc;
import mapd.exceptions.RepeatedNameElement;

public class PetriNetwork implements IPetriNetwork {
	
	private static PetriNetwork instance;

	private HashMap<String, Place> places; 
	private HashMap<String, Transition> transitions;
	private HashMap<String, Arc> arcs;
	
	/**
	 * Default constructor for the PetriNetwork class.
	 * <p>
	 * Initializes the internal structures required to model a Petri net, 
	 * which includes collections for places, transitions, arcs, and records.
	 * <ul>
	 *   <li>places: A HashMap to store Place objects, indexed by their unique String identifiers.</li>
	 *   <li>transitions: A HashMap to store Transition objects, indexed by their unique String identifiers.</li>
	 *   <li>arcs: A HashMap to store Arc objects, indexed by their unique String identifiers.</li>
	 *   <li>records: An ArrayList to store lists of strings, which might represent logs or history of the network's state changes.</li>
	 * </ul>
	 */
	private PetriNetwork() {
		this.places = new HashMap<String, Place>();
		this.transitions = new HashMap<String, Transition>();
		this.arcs = new HashMap<String, Arc>();
	}
	
	/**
     * Retrieves the singleton instance of the PetriNetwork.
     * <p>
     * If the instance does not exist, it creates a new instance.
     * Ensures only one instance of PetriNetwork is present in the application.
     *
     * @return The singleton instance of PetriNetwork.
     */
	public static synchronized PetriNetwork getInstance() {
        if (instance == null) {
            instance = new PetriNetwork();
        }
        return instance;
    }
	
	/**
     * Resets the Petri network by clearing all places, transitions, and arcs.
     * <p>
     * This method is useful for setting up a clean state, especially for testing purposes.
     */
	
	public void reset() {
        this.places.clear();
        this.transitions.clear();
        this.arcs.clear();
    }

	/**
     * Returns the collection of Place objects in the network.
     *
     * @return HashMap of Place objects indexed by their unique labels.
     */
	public HashMap<String, Place> getPlaces() {
		return places;
	}
	
	/**
     * Returns the collection of Transition objects in the network.
     *
     * @return HashMap of Transition objects indexed by their unique labels.
     */
	public HashMap<String, Transition> getTransitions() {
		return transitions;
	}

	/**
     * Returns the collection of Arc objects in the network.
     *
     * @return HashMap of Arc objects indexed by their unique labels.
     */
	public HashMap<String, Arc> getArcs() {
		return arcs;
	}

	public Place getPlace(String label) throws ElementNameNotExists {
		if (this.places.containsKey(label)) {
			return this.places.get(label);
		} else {
			throw new ElementNameNotExists("A place with this name does not exist");
		}
	}

	public Transition getTransition(String label) throws ElementNameNotExists {
		if (this.transitions.containsKey(label)) {
			return this.transitions.get(label);
		} else {
			throw new ElementNameNotExists("A transition with this name does not exist");
		}
	}

	public Arc getArc(String label) throws ElementNameNotExists {
		if (this.arcs.containsKey(label)) {
			return this.arcs.get(label);
		} else {
			throw new ElementNameNotExists("An arc with this name does not exist");
		}
	}
	
	/**
	 * Adds a new Place to the Petri network with the specified label.
	 * <p>
	 * This method creates a new Place object and inserts it into the network, 
	 * using the given label as the unique identifier. If a Place with the 
	 * same label already exists, an exception is thrown to prevent duplicate entries.
	 * 
	 * @param label The unique identifier for the Place to be added.
	 * @throws RepeatedNameElement If a Place with the specified label already exists in the network.
	 * @throws InvalidTokenNumber 
	 */
	@Override
	public void addPlace(String label) throws RepeatedNameElement, ElementNameNotExists, InvalidTokenNumber {
		if (!this.places.containsKey(label)) {
			Place place = new Place(label);
			this.places.put(label, place);
		} else {
			throw new RepeatedNameElement("A Place already exists with this name");
		}
	}

	@Override
	public void addPlace(String label, Integer tokens) throws InvalidTokenNumber, RepeatedNameElement {
		if (!this.places.containsKey(label)) {
			Place place = new Place(label, tokens);
			this.places.put(label, place);
		} else {
			throw new RepeatedNameElement("A Place already exists with this name");
		}
	}
	
	/**
	 * Adds a new Place to the Petri network with the specified label and initial number of tokens.
	 * <p>
	 * This method creates a new Place object with a given number of tokens and inserts it into the network,
	 * using the provided label as the unique identifier. If a Place with the same label already exists,
	 * an exception is thrown. Additionally, if the number of tokens is invalid (e.g., negative),
	 * an exception is thrown.
	 * 
	 * @param label  The unique identifier for the Place to be added.
	 * @param tokens The initial number of tokens in the Place. Must be a valid non-negative integer.
	 * @throws ElementNameNotExists 
	 * @throws InvalidTokenNumber If the provided number of tokens is invalid (e.g., negative).
	 * @throws RepeatedNameElement If a Place with the specified label already exists in the network.
	 */
	@Override
	public void rmPlace(String label) throws ElementNameNotExists {
		Place place = this.getPlace(label);
		if (place != null) {
			List<String> arcLabels = this.arcs.entrySet()
					.stream()
					.filter(entry -> entry.getValue().getPlace().getLabel().equals(label))
					.map(Map.Entry::getKey)
					.collect(Collectors.toList());
			arcLabels.forEach((arcLabel) -> {this.arcs.remove(arcLabel);});
			this.places.remove(label);
		}
	}

	/**
	 * Sets the number of tokens for a specified Place in the Petri network.
	 * <p>
	 * This method updates the token count for the Place identified by the given label.
	 * If the label corresponds to a Place in the network, the number of tokens is updated.
	 * If the provided token count is invalid (e.g., negative), an exception is thrown.
	 * 
	 * @param label  The unique identifier of the Place whose token count is to be updated.
	 * @param tokens The new token count for the Place. Must be a valid non-negative integer.
	 * @throws InvalidTokenNumber If the provided number of tokens is invalid (e.g., negative).
	 * @throws ElementNameNotExists 
	 */
	@Override
	public void setPlaceTokens(String label, Integer tokens) throws InvalidTokenNumber, ElementNameNotExists {
		Place place = this.getPlace(label);
		if (place != null) {
			place.setTokens(tokens);
		}
	}
	
	/**
	 * Adds a new Transition to the Petri network with the specified label.
	 * <p>
	 * This method creates a new Transition object and inserts it into the network, 
	 * using the given label as the unique identifier. If a Transition with the 
	 * same label already exists, an exception is thrown to prevent duplicate entries.
	 * 
	 * @param label The unique identifier for the Transition to be added.
	 * @throws RepeatedNameElement If a Transition with the specified label already exists in the network.
	 */
	@Override
	public void addTransition(String label) throws RepeatedNameElement {
		if (!this.transitions.containsKey(label)) {
			Transition transition = new Transition(label);
			this.transitions.put(label, transition);
		} else {
			throw new RepeatedNameElement("A transition already exists with this name");
		}
	}
	
	/**
     * Updates the fireable status of each transition in the network.
     * <p>
     * This method checks and updates each transition to determine whether it is currently fireable
     * based on the state of the associated places and arcs.
     */
	private void updateTransitions() {
		transitions.forEach((key, transition) -> {
			transition.updateIsFireable();
		});
	}

	/**
	 * Removes a Transition from the Petri network with the specified label.
	 * <p>
	 * This method removes the Transition identified by the given label from the network.
	 * If the label does not exist, no action is taken.
	 * 
	 * @param label The unique identifier of the Transition to be removed.
	 * @throws ElementNameNotExists 
	 */
	@Override
	public void rmTransition(String label) throws ElementNameNotExists {
		Transition transition = this.getTransition(label);
		if (transition != null) {
			List<String> inArcLabels = transition.getInArcs()
					.stream()
					.map(InArc::getLabel)
					.collect(Collectors.toList());
			inArcLabels.forEach((arcLabel) -> {this.arcs.remove(arcLabel);});
			List<String> outArcLabels = transition.getOutArcs()
					.stream()
					.map(OutArc::getLabel)
					.collect(Collectors.toList());
			outArcLabels.forEach((arcLabel) -> {this.arcs.remove(arcLabel);});
			this.transitions.remove(label);
		}
	}
	
	/**
	 * Adds a new Arc to the Petri network, linking a specified Transition and Place.
	 * <p>
	 * This method creates an Arc of the given type between the specified Transition and Place,
	 * using the provided label as a unique identifier. The weight of the Arc can be customized,
	 * and if no weight is provided, a default weight of 1 is used. The method checks for 
	 * duplicate arcs with the same direction (Transition to Place) to avoid conflicts. 
	 * If a duplicate arc or an arc with the same label already exists, an exception is thrown.
	 * 
	 * @param label           The unique identifier for the Arc to be added.
	 * @param transitionLabel The label of the Transition to which the Arc will be connected.
	 * @param placeLabel      The label of the Place to which the Arc will be connected.
	 * @param type            The type of the Arc (e.g., "in", "out", "outzero", "outbouncer").
	 * @param weight          The weight of the Arc. If null, the default weight of 1 is used.
	 * @throws InvalidWeightNumber If the provided weight is invalid (e.g., negative).
	 * @throws RepeatedNameElement If an Arc with the same label already exists in the network.
	 * @throws RepeatedArc         If an Arc with the same transition, place, and type already exists.
	 * @throws ElementNameNotExists 
	 */
	@Override
	public void addArc(String label, String transitionLabel, String placeLabel, String type, Integer weight) throws InvalidWeightNumber, RepeatedNameElement, RepeatedArc, ElementNameNotExists {
		if (!this.arcs.containsKey(label)) {
			Place place = this.getPlace(placeLabel);
			Transition transition = this.getTransition(transitionLabel);
			Arc arc = createArc(label, type, place, transition, weight);
			this.arcs.put(label, arc);
		} else {
			throw new RepeatedNameElement("An arc already exists with this name");
		}
	}
	
	
	@Override
	public void addArc(String label, String transitionLabel, String placeLabel, String type) throws InvalidWeightNumber, RepeatedNameElement, RepeatedArc, ElementNameNotExists {
		if (!this.arcs.containsKey(label)) {
			Place place = this.getPlace(placeLabel);
			Transition transition = this.getTransition(transitionLabel);
			
			Arc arc = createArc(label, type, place, transition, null);
			this.arcs.put(label, arc);
		} else {
			throw new RepeatedNameElement("An arc already exists with this name");
		}
	}

	/**
	 * Sets the weight of a specified Arc in the Petri network.
	 * <p>
	 * This method updates the weight of the Arc identified by the given label. 
	 * If the provided weight is invalid (e.g., negative), an exception is thrown.
	 * 
	 * @param label  The unique identifier of the Arc whose weight is to be updated.
	 * @param weight The new weight for the Arc. Must be a valid non-negative integer.
	 * @throws InvalidWeightNumber If the provided weight is invalid (e.g., negative).
	 * @throws ElementNameNotExists 
	 */
	@Override
	public void setArcWeight(String label, Integer weight) throws InvalidWeightNumber, ElementNameNotExists {
		Arc arc = this.getArc(label);
		if (arc != null) {
			arc.setWeight(weight);
		}
	}
	
	/**
	 * Removes an Arc from the Petri network with the specified label.
	 * <p>
	 * This method deletes the Arc identified by the given label from the network.
	 * If the label does not exist, no action is taken.
	 * 
	 * @param label The unique identifier of the Arc to be removed.
	 * @throws ElementNameNotExists 
	 */
	@Override
	public void rmArc(String label) throws ElementNameNotExists {
		Arc arc = this.getArc(label);
		if (arc != null) {
			this.arcs.remove(label);
		}
	}
	
	/**
	 * Retrieves a list of fireable transitions in the Petri network.
	 * <p>
	 * This method checks all transitions in the network and filters those that are fireable.
	 * The keys (labels) of the fireable transitions are collected into a list, which is then returned.
	 * Additionally, the list of fireable transitions is printed to the console for debugging purposes.
	 * 
	 * @return A list of labels of transitions that are currently fireable.
	 */
	@Override
	public List<String> fireableTransitions() {
		List<String> fireableTransitions = transitions.entrySet().stream()
	            .filter(entry -> entry.getValue().isFireable())
	            .map(Map.Entry::getKey)
	            .collect(Collectors.toList());
		return fireableTransitions;
	}

	/**
	 * Fires the transition identified by the specified label in the Petri network.
	 * <p>
	 * This method executes the firing action for the specified transition, which may update the state 
	 * of the network. The method prints the state of the places before and after firing the transition 
	 * for debugging purposes.
	 * 
	 * @param label The unique identifier of the transition to be fired.
	 * @throws ElementNameNotExists 
	 * @throws NoFireableTransition 
	 */
	@Override
	public void fire(String label) throws ElementNameNotExists, NoFireableTransition {		
		getTransition(label).fire();
		updateTransitions();
		showPlaces();
	}
	
	/**
	 * Displays the current state of the places in the Petri network.
	 * <p>
	 * This method retrieves and prints the list of places, allowing users to see the current 
	 * configuration of the network. It is an overridden method that provides a specific implementation 
	 * for showing places.
	 */
	@Override
	public void showPlaces() {
		System.out.println(this.getPlaces());	
	}
	
	/**
	 * Displays the current state of the transitions in the Petri network.
	 * <p>
	 * This method retrieves and prints the list of transitions, allowing users to see the current 
	 * configuration of the network. It is an overridden method that provides a specific implementation 
	 * for showing transitions.
	 */
	@Override
	public void showTransitions() {
		System.out.println(this.getTransitions());	
	}
	
	/**
	 * Displays the current state of the arcs in the Petri network.
	 * <p>
	 * This method retrieves and prints the list of arcs, allowing users to see the current 
	 * configuration of the network. It provides an overview of how the transitions and places are 
	 * interconnected through arcs.
	 */
	@Override
	public void showArcs() {
		System.out.println(this.getArcs());	
	}
	
	/**
	 * Displays the current state of all elements in the Petri network.
	 * <p>
	 * This method prints the lists of places, transitions, and arcs in the network. 
	 * It provides a comprehensive overview of the network's structure, making it easier 
	 * for users to understand the current configuration and relationships between elements.
	 */
	@Override
	public void showAllElements() {
	    StringBuilder result = new StringBuilder();

	    result.append("Petri Network\n")
	          .append(this.places.size()).append(" places\n")
	          .append(this.transitions.size()).append(" transitions\n")
	          .append(this.arcs.size()).append(" arcs\n");

	    result.append("List of places:\n");
	    int placeCount = 1;
	    for (Place place : this.places.values()) {
	        long entranceArcCount = this.arcs.values().stream()
	                .filter(arc -> arc instanceof InArc && arc.getPlace().equals(place))
	                .count();
	        long exitArcCount = this.arcs.values().stream()
	                .filter(arc -> arc instanceof OutArc && !(arc instanceof OutBouncerArc) && !(arc instanceof OutZeroArc) && arc.getPlace().equals(place))
	                .count();
	        long bouncerExitArcCount = this.arcs.values().stream()
	                .filter(arc -> arc instanceof OutBouncerArc && arc.getPlace().equals(place))
	                .count();
	        long zeroExitArcCount = this.arcs.values().stream()
	                .filter(arc -> arc instanceof OutZeroArc && arc.getPlace().equals(place))
	                .count();

	        result.append(placeCount).append(": ").append(place.toString())
	              .append(", ").append(exitArcCount).append(" simple out arcs, ")
	              .append(entranceArcCount).append(" in arcs, ")
	              .append(bouncerExitArcCount).append(" bouncer out arcs, ")
	              .append(zeroExitArcCount).append(" zero out arcs\n");
	        placeCount++;
	    }

	    result.append("List of transitions:\n");
	    int transitionCount = 1;
	    for (Transition transition : this.transitions.values()) {
	        result.append(transitionCount).append(": ").append(transition.toString()).append("\n");
	        transitionCount++;
	    }

	    result.append("List of arcs:\n");
	    int arcCount = 1;
	    for (Arc arc : this.arcs.values()) {
	        result.append(arcCount).append(": ").append(arc.toString()).append("\n");
	        arcCount++;
	    }

	    System.out.println(result.toString());
	}
	
	/**
	 * Renames a Place in the Petri network from the old name to the new name.
	 * <p>
	 * This method retrieves the Place associated with the old name, removes it from the 
	 * network, and then adds it back with the new name. This effectively changes the 
	 * identifier of the Place while maintaining its properties.
	 * 
	 * @param oldName The current name of the Place to be renamed.
	 * @param newName The new name to assign to the Place. 
	 * @throws RepeatedNameElement 
	 * @throws ElementNameNotExists 
	 * @throws IllegalArgumentException If a Place with the new name already exists in the network.
	 */
	@Override
	public void renamePlace(String oldName, String newName) throws RepeatedNameElement, ElementNameNotExists {
		Place place = this.getPlace(oldName);
		if (place != null && !this.places.containsKey(newName)) {
			place.setLabel(newName);
			this.places.remove(oldName);
			places.put(newName, place);
		} else {
			throw new RepeatedNameElement("New place name already exists");
		}
	}
	
	/**
	 * Renames a Transition in the Petri network from the old name to the new name.
	 * <p>
	 * This method retrieves the Transition associated with the old name, removes it from the 
	 * network, and then adds it back with the new name. This effectively changes the 
	 * identifier of the Transition while maintaining its properties.
	 * 
	 * @param oldName The current name of the Transition to be renamed.
	 * @param newName The new name to assign to the Transition.
	 * @throws RepeatedNameElement 
	 * @throws ElementNameNotExists 
	 * @throws IllegalArgumentException If a Transition with the new name already exists in the network.
	 */
	@Override
	public void renameTransition(String oldName, String newName) throws RepeatedNameElement, ElementNameNotExists {
		Transition transition = this.getTransition(oldName);
		if (transition != null && !this.transitions.containsKey(newName)) {
			transition.setLabel(newName);
			this.transitions.remove(oldName);
			this.transitions.put(newName, transition);
		} else {
			throw new RepeatedNameElement("New transition name already exists");
		}
	}
	
	/**
	 * Renames an Arc in the Petri network from the old name to the new name.
	 * <p>
	 * This method retrieves the Arc associated with the old name, removes it from the 
	 * network, and then adds it back with the new name. This effectively changes the 
	 * identifier of the Arc while maintaining its properties.
	 * 
	 * @param oldName The current name of the Arc to be renamed.
	 * @param newName The new name to assign to the Arc.
	 * @throws RepeatedNameElement 
	 * @throws ElementNameNotExists 
	 * @throws IllegalArgumentException If an Arc with the new name already exists in the network.
	 */
	@Override
	public void renameArc(String oldName, String newName) throws RepeatedNameElement, ElementNameNotExists {
		Arc arc = this.getArc(oldName);
		if (arc != null && !this.arcs.containsKey(newName)) {
			arc.setLabel(newName);
			this.arcs.remove(oldName);
			this.arcs.put(newName, arc);
		} else {
			throw new RepeatedNameElement("New arc name already exists");
		}
	}
	
	/**
	 * Creates a new Arc object of the specified type, associated with a given Place and weight.
	 * <p>
	 * This method constructs an Arc based on the provided type (e.g., "in", "out", "outzero", "outbouncer"), 
	 * associating it with the specified Place and weight. If the weight is not provided (i.e., null), 
	 * a default weight of 1 is used. If the type is invalid, an exception is thrown.
	 * 
	 * @param type      The type of the Arc to be created (e.g., "in", "out", "outzero", "outbouncer").
	 * @param place     The Place object to which the Arc will be associated.
	 * @param newWeight The weight of the Arc. If null, the default weight of 1 is used.
	 * @return The newly created Arc of the specified type.
	 * @throws InvalidWeightNumber   If the provided weight is invalid (e.g., negative).
	 * @throws RepeatedArc 
	 * @throws IllegalArgumentException If the specified arc type is not recognized.
	 */
	private Arc createArc(String label, String type, Place place, Transition transition, Integer newWeight) throws InvalidWeightNumber, RepeatedArc {
		int weight = (newWeight == null) ? 1 : newWeight;
        switch (type.toLowerCase()) {
            case "in":
                return new InArc(label, place, transition, weight);
            case "out":
                return new OutArc(label, place, transition, weight);
            case "outzero":
                return new OutZeroArc(label, place, transition, weight);
            case "outbouncer":
                return new OutBouncerArc(label, place, transition, weight);
            default:
            	throw new IllegalArgumentException("No valid " + type + " arc type");
        }
    }
}
