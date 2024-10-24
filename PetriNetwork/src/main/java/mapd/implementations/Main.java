package mapd.implementations;

public class Main {
	
	public static void main (String[] args) throws Exception {
		try {
			// PetriNetwork Mutex topology example
			PetriNetwork net = new PetriNetwork();
			
			// Places
			net.addPlace("p1");
			net.addPlace("p2", 1);
			net.addPlace("p3");
			net.addPlace("p4");
			net.addPlace("p5", 1);
			
			// Transitions
			net.addTransition("t1");
			net.addTransition("t2");
			net.addTransition("t3");
			net.addTransition("t4");
			
			//Out Arcs (having place as a reference)
			net.addArc("a1_out", "t1", "p1", "out");
			net.addArc("a2_out", "t2", "p2", "out");
			net.addArc("a3_out", "t1", "p3", "out");
			net.addArc("a4_out", "t4", "p4", "out");
			net.addArc("a5_out", "t3", "p5", "out");
			
			//In Arcs (having place as a reference)
			net.addArc("a1_in", "t2", "p1", "in");
			net.addArc("a2_in", "t1", "p2", "in");
			net.addArc("a3_in", "t2", "p3", "in");
			net.addArc("a4_in", "t3", "p4", "in");
			net.addArc("a5_in", "t4", "p5", "in");
			
			net.showAllElements();
			
			net.fireableTransitions();
			net.fire("t2");
			net.fireableTransitions();
			net.fire("t3");
			net.fireableTransitions();
			net.fire("t4");
			net.fireableTransitions();
			net.fire("t1");
			net.fireableTransitions();
			
			
		}catch(Exception error) {
			System.err.println("Error: " + error.getMessage());
		}
	}

}
