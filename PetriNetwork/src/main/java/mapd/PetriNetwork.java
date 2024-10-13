package mapd;

import interfaces.IPetriNetwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import exceptions.ElementNameNotExists;
import exceptions.InvalidTokenNumber;
import exceptions.InvalidWeightNumber;
import exceptions.RepeatedArc;
import exceptions.RepeatedNameElement;

public class PetriNetwork implements IPetriNetwork {

	private HashMap<String, Place> places; 
	private HashMap<String, Transition> transitions;
	private HashMap<String, Arc> arcs;
	private List<List<String>> records = new ArrayList<List<String>>();
	
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
	PetriNetwork() {
		this.places = new HashMap<String, Place>(); 
		this.transitions = new HashMap<String, Transition>();
		this.arcs = new HashMap<String, Arc>();
		this.records = new ArrayList<List<String>>();
	}

	public HashMap<String, Place> getPlaces() {
		return places;
	}

	public HashMap<String, Transition> getTransitions() {
		return transitions;
	}

	public HashMap<String, Arc> getArcs() {
		return arcs;
	}
	
	public List<List<String>> getRecords() {
		return records;
	}
	
	public Place getPlace(String label) {
		return places.get(label);
	}

	public Transition getTransition(String label) {
		return transitions.get(label);
	}

	public Arc getArc(String label) {
		return arcs.get(label);
	}

	public void setRecords(List<List<String>> records) {
		this.records = records;
	}

	public void setPlaces(HashMap<String, Place> places) {
		this.places = places;
	}

	public void setTransitions(HashMap<String, Transition> transitions) {
		this.transitions = transitions;
	}

	public void setArcs(HashMap<String, Arc> arcs) {
		this.arcs = arcs;
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
	 */
	@Override
	public void addPlace(String label) throws RepeatedNameElement {
		if (! this.places.containsKey(label)) {
			Place place = new Place();
			this.places.put(label, place);
		}else {
			throw new RepeatedNameElement("A Place already exists with this name");
		}
	}

	@Override
	public void addPlace(String label, Integer tokens) throws InvalidTokenNumber, RepeatedNameElement {
		if (! this.places.containsKey(label)) {
			Place place = new Place(tokens);
			this.places.put(label, place);
		}else {
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
	 * @throws InvalidTokenNumber If the provided number of tokens is invalid (e.g., negative).
	 * @throws RepeatedNameElement If a Place with the specified label already exists in the network.
	 */
	@Override
	public void rmPlace(String label) {
		this.places.remove(label);
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
	 */
	@Override
	public void setPlaceTokens(String label, Integer tokens) throws InvalidTokenNumber {
		places.get(label).setTokens(tokens);
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
		if (! this.transitions.containsKey(label)) {
			Transition transition = new Transition();
			this.transitions.put(label, transition);
		}else {
			throw new RepeatedNameElement("A transition already exists with this name");
		}
	}

	/**
	 * Removes a Transition from the Petri network with the specified label.
	 * <p>
	 * This method removes the Transition identified by the given label from the network.
	 * If the label does not exist, no action is taken.
	 * 
	 * @param label The unique identifier of the Transition to be removed.
	 */
	@Override
	public void rmTransition(String label) {
		this.transitions.remove(label);
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
	 * @throws IllegalArgumentException If the specified arc type is not recognized.
	 */
	private Arc createArc(String type, Place place, Integer newWeight) throws InvalidWeightNumber {
		int weight = (newWeight == null) ? 1 : newWeight;
        switch (type.toLowerCase()) {
            case "in":
                return new InArc(place, weight);
            case "out":
                return new OutArc(place, weight);
            case "outzero":
                return new OutZeroArc(place, weight);
            case "outbouncer":
                return new OutBouncerArc(place, weight);
            default:
            	throw new IllegalArgumentException("No valid " + type + " arc type");
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
	 */
	@Override
	public void addArc(String label, String transitionLabel, String placeLabel, String type, Integer weight) throws InvalidWeightNumber, RepeatedNameElement, RepeatedArc {
		if (! this.arcs.containsKey(label)) {
			if (!this.isArcRepeated(transitionLabel, placeLabel, type)) {
				Place place = places.get(placeLabel);
				Transition transition = transitions.get(transitionLabel);
				Arc arc = createArc(type, place, weight);
		        arc.addToTransition(transition);
		        this.arcs.put(label, arc);
		        List<String> arcRecord = Arrays.asList(transitionLabel, placeLabel, type);
		        records.add(arcRecord);
			}else {
				throw new RepeatedArc("An Arc in the same direction already exists");
			}
		}else {
			throw new RepeatedNameElement("An arc already exists with this name");
		}
	}
	
	
	@Override
	public void addArc(String label, String transitionLabel, String placeLabel, String type) throws InvalidWeightNumber, RepeatedNameElement, RepeatedArc {
		if ( !this.arcs.containsKey(label)) {
			if (!this.isArcRepeated(transitionLabel, placeLabel, type)) {
				Place place = places.get(placeLabel);
				Transition transition = transitions.get(transitionLabel);
				Arc arc = createArc(type, place, null);
		        arc.addToTransition(transition);
		        this.arcs.put(label, arc);
		        List<String> arcRecord = Arrays.asList(transitionLabel, placeLabel, type);
		        records.add(arcRecord);
			}else {
				throw new RepeatedArc("An Arc in the same direction already exists");
			}
		}else {
			throw new RepeatedNameElement("An arc already exists with this name");
		}
	}
	
	/**
	 * Adds a new Arc to the Petri network, linking a specified Transition and Place with a default weight.
	 * <p>
	 * This method creates an Arc of the given type between the specified Transition and Place,
	 * using the provided label as a unique identifier. The Arc is created with a default weight of 1,
	 * since no weight parameter is provided. The method checks for duplicate arcs with the same 
	 * direction (Transition to Place) to avoid conflicts. If a duplicate arc or an arc with the same
	 * label already exists, an exception is thrown.
	 * 
	 * @param label           The unique identifier for the Arc to be added.
	 * @param transitionLabel The label of the Transition to which the Arc will be connected.
	 * @param placeLabel      The label of the Place to which the Arc will be connected.
	 * @param type            The type of the Arc (e.g., "in", "out", "outzero", "outbouncer").
	 * @throws InvalidWeightNumber If the provided weight is invalid (handled by the default weight).
	 * @throws RepeatedNameElement If an Arc with the same label already exists in the network.
	 * @throws RepeatedArc         If an Arc with the same transition, place, and type already exists.
	 */
	@Override
	public void editArcDirection(String label, String labelTransition, String labelPlace) throws InvalidWeightNumber, RepeatedArc {
		Arc oldArc = arcs.get(label);
		Integer weight = oldArc.getWeight();
		Place place = oldArc.getPlace();
		
		Transition involvedTransition = transitions.get(labelTransition);
		
		String type = "";
		if (oldArc instanceof InArc) {
			type = "in";
			if (! isArcRepeated(labelTransition, labelPlace, type)) {
				involvedTransition.getInArcs().remove(oldArc);
				Arc newArc = createArc("out", place, weight);
				newArc.addToTransition(involvedTransition);
				this.arcs.put(label, newArc);
				// update record
				updateArcDirectionInRecord(labelTransition, labelPlace, "out");
			}else {
				throw new RepeatedArc("An Arc in the same direction already exists");
			}
		}else if (oldArc instanceof OutArc || oldArc instanceof OutZeroArc || oldArc instanceof OutBouncerArc) {
			type = "out";
			if (! isArcRepeated(labelTransition, labelPlace, type)) {
				involvedTransition.getInArcs().remove(oldArc);
				Arc newArc = createArc("in", place, weight);
				newArc.addToTransition(involvedTransition);
				this.arcs.put(label, newArc);
				// update record
				updateArcDirectionInRecord(labelTransition, labelPlace, "in");
			}else {
				throw new RepeatedArc("An Arc in the same direction already exists");
			}
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
	 */
	@Override
	public void setArcWeight(String label, Integer weight) throws InvalidWeightNumber {
		this.arcs.get(label).setWeight(weight);
	}
	
	/**
	 * Removes an Arc from the Petri network with the specified label.
	 * <p>
	 * This method deletes the Arc identified by the given label from the network.
	 * If the label does not exist, no action is taken.
	 * 
	 * @param label The unique identifier of the Arc to be removed.
	 */
	@Override
	public void rmArc(String label) {
		arcs.remove(label);
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
		
		System.out.println(fireableTransitions);
		
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
	 */
	@Override
	public void fire(String label) {
		System.out.println("Before fire transition");
		showPlaces();
		
		transitions.get(label).fire();
		
		System.out.println("After fire transition");
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
		System.out.println("Places:");
		showPlaces();
		System.out.println("Transitions:");
		showTransitions();
		System.out.println("Arcs:");
		showArcs();
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
		if(places.containsKey(oldName)) {
			Place place = places.get(oldName);
			places.remove(oldName);
			if (!arcs.containsKey(newName)) {
				places.put(newName, place);
				// update record of new place name
				updateNameInRecords(oldName, newName, "place");
				
			}else {
				throw new RepeatedNameElement("New name already exists");
			}
		}else {
			throw new ElementNameNotExists("Element with name" + oldName + "does not exist");
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
		if (transitions.containsKey(oldName)) {
			Transition transition = transitions.get(oldName);
			transitions.remove(oldName);
			if (!arcs.containsKey(newName)) {
				transitions.put(newName, transition);
				// update record of transition name
				updateNameInRecords(oldName, newName, "transition");
			}else {
				throw new RepeatedNameElement("New name already exists");
			}
		}else {
			throw new ElementNameNotExists("Element with name" + oldName + "does not exist");
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
		if (arcs.containsKey(oldName)) {
			Arc arc = arcs.get(oldName);
			arcs.remove(oldName);
			if (!arcs.containsKey(newName)) {
				arcs.put(newName, arc);
			}else {
				throw new RepeatedNameElement("New name already exists");
			}
		}else {
			throw new ElementNameNotExists("Element with name" + oldName + "does not exist");
		}
	}
	
	/**
	 * Checks if an Arc with the same transition, place, and type already exists in the Petri network.
	 * <p>
	 * This method creates a record based on the provided transition, place, and type and 
	 * verifies if this record is already present in the list of existing arc records. 
	 * It helps prevent duplicate arcs from being created in the network.
	 * 
	 * @param transition The label of the Transition associated with the Arc.
	 * @param place      The label of the Place associated with the Arc.
	 * @param type       The type of the Arc (e.g., "in", "out", "outzero", "outbouncer").
	 * @return True if an Arc with the same transition, place, and type exists; false otherwise.
	 */
	private Boolean isArcRepeated(String transition, String place, String type) {
		List<String> arcRecord = Arrays.asList(transition, place, type);
		return records.contains(arcRecord);
	}
	
	/**
	 * Updates the name in the records for either "place" or "transition" based on the given type.
	 * The method iterates through the records and replaces the `oldName` with the `newName`
	 * in the appropriate index of each list, determined by the type.
	 *
	 * @param oldName the original name to be updated in the records
	 * @param newName the new name to replace the old name in the records
	 * @param type    specifies the type of the record, either "place" or "transition".
	 *                If the type is "place", the name is updated at index 1 of the list.
	 *                If the type is "transition", the name is updated at index 0 of the list.
	 */
	private void updateNameInRecords (String oldName, String newName, String type) {
		Iterator <List<String>> iterator = this.records.iterator();
		int index = 0;
		if (type.equals("place")) {
			index = 1;
		}else if(type.equals("transition")) {
			index = 0;
		}
		while(iterator.hasNext()) {
			List<String> list = iterator.next();
			if (list.contains(oldName)) {
				list.set(index, newName);
			}
		}
	}
	
	/**
	 * Updates the direction of an arc between a specified transition and place in the records.
	 * The method iterates through the records and replaces the arc direction with the given 
	 * `newDirection` for the matching transition and place.
	 *
	 * @param transition   the name of the transition involved in the arc
	 * @param place        the name of the place involved in the arc
	 * @param newDirection the new direction to set for the arc between the transition and place.
	 *                     This value will replace the element at index 2 in the matching record.
	 */
	private void updateArcDirectionInRecord (String transition, String place, String newDirection) {
		Iterator <List<String>> iterator = this.records.iterator();
		while(iterator.hasNext()) {
			List<String> list = iterator.next();
			if (list.contains(transition) && list.contains(place)) {
				list.set(2, newDirection);
			}
		}
	}
}
