package mapd.implementations;

import java.util.List;

public class Dashboard {
	
	public static void main (String[] args) throws Exception {
		try {
			
			PetriNetwork net = new PetriNetwork();
			List<String> fireableTransitions;
			
			//First Transition
			net.addPlace("p1", 1);
			net.addPlace("p2", 3);
			net.addPlace("p3");
			net.addPlace("p4", 1);
			
			net.addTransition("t1");
			
			net.addArc("a1", "t1", "p1", "out");
			net.addArc("a2", "t1", "p2", "out", 2);
			net.addArc("a3", "t1", "p3", "in", 3);
			net.addArc("a4", "t1", "p4", "in"); // by default weight is 1
			
			fireableTransitions = net.fireableTransitions();
			net.fire(fireableTransitions.get(0)); // t1
			
			//Second Transition
			net.addPlace("p1.1", 0);
			net.addPlace("p2.1", 10);
			
			net.addTransition("t2");
			
			net.addArc("a1.1", "t2", "p1.1", "outzero");
			net.addArc("a2.1", "t2", "p2.1", "outBouncer");
			net.addArc("a3.1", "t2", "p3", "in");
			
			fireableTransitions = net.fireableTransitions();
			net.fire(fireableTransitions.get(0)); // t2
			
			// Edit Arc Direction to give 1 token to p1.1 which initially was created without any token
			net.editArcDirection("a1.1", "t2", "p1.1");
			
			fireableTransitions = net.fireableTransitions();
			net.fire(fireableTransitions.get(0));// t2
			
			net.renameArc("a1.1", "arcInverted"); // to test renaming
			net.renamePlace("p1", "place1");
			net.renameTransition("t2", "newTransition");
		
			net.showAllElements();
			
			// Use cases to test exceptions
			// net.setPlaceTokens("place1", -1);
			// net.setArcWeight("arcInverted", 0);
			// net.addPlace("p2");
			// net.addArc("errorArc1", "p2", "t2", "out", 2);
			// net.addArc("errorArc", "t1", "p2", "out", 3);
			// net.addArc("errorArc", "t1", "p2", "outA", 3);
		}catch(Exception error) {
			System.out.println("Error: " + error.getMessage());
		}
	}

}
