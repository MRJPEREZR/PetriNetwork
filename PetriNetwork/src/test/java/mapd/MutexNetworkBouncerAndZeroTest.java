package mapd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import mapd.exceptions.ElementNameNotExists;
import mapd.exceptions.InvalidTokenNumber;
import mapd.exceptions.InvalidWeightNumber;
import mapd.exceptions.NoFireableTransition;
import mapd.exceptions.RepeatedArc;
import mapd.exceptions.RepeatedNameElement;
import mapd.implementations.PetriNetwork;

public class MutexNetworkBouncerAndZeroTest {
	
	PetriNetwork net = PetriNetwork.getInstance();
	
	@BeforeEach
	public void setup() throws RepeatedNameElement, InvalidTokenNumber, InvalidWeightNumber, RepeatedArc, ElementNameNotExists {
		net.reset();
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
		net.addArc("a3_out", "t1", "p3", "outbouncer");
		net.addArc("a4_out", "t4", "p4", "outzero");
		net.addArc("a5_out", "t3", "p5", "out");
		net.addArc("a6_out", "t3", "p3", "out");
						
		//In Arcs (having place as a reference)
		net.addArc("a1_in", "t2", "p1", "in");
		net.addArc("a2_in", "t1", "p2", "in");
		net.addArc("a3_in", "t2", "p3", "in");
		net.addArc("a4_in", "t3", "p4", "in");
		net.addArc("a5_in", "t4", "p5", "in");
		net.addArc("a6_in", "t4", "p3", "in");
	}
	
	@Test
	@Order(1)
	public void testCurrentPlaceTokens() throws ElementNameNotExists, RepeatedNameElement, InvalidTokenNumber, InvalidWeightNumber, RepeatedArc {
		assertEquals(net.getPlace("p1").getTokens(), (Integer) 0);
		assertEquals(net.getPlace("p2").getTokens(), (Integer) 1);
		assertEquals(net.getPlace("p3").getTokens(), (Integer) 0);
		assertEquals(net.getPlace("p4").getTokens(), (Integer) 0);
		assertEquals(net.getPlace("p5").getTokens(), (Integer) 1);
	}
	
	@Test
	@Order(2)
	public void testAvailableTransitions() throws RepeatedNameElement, InvalidTokenNumber, InvalidWeightNumber, RepeatedArc, ElementNameNotExists {
		List<String> availableTransitions = net.fireableTransitions();
		assertTrue(availableTransitions.contains("t2"));
		assertTrue(availableTransitions.contains("t3"));
		assertTrue(availableTransitions.contains("t4"));
	}

	@Test
	@Order(3)
	public void testRunTransition3() throws ElementNameNotExists, RepeatedNameElement, InvalidTokenNumber, InvalidWeightNumber, RepeatedArc, NoFireableTransition {
		net.fire("t3");
		assertEquals(net.getPlace("p1").getTokens(), (Integer) 0);
		assertEquals(net.getPlace("p2").getTokens(), (Integer) 1);
		assertEquals(net.getPlace("p3").getTokens(), (Integer) 0);
		assertEquals(net.getPlace("p4").getTokens(), (Integer) 1);
		assertEquals(net.getPlace("p5").getTokens(), (Integer) 0);	
		
		List<String> availableTransitions = net.fireableTransitions();
		assertTrue(availableTransitions.contains("t2"));
		assertFalse(availableTransitions.contains("t3"));
	}

	@Test
	@Order(4)
	public void testRunTransition2() throws ElementNameNotExists, RepeatedNameElement, InvalidTokenNumber, InvalidWeightNumber, RepeatedArc, NoFireableTransition {
		net.fire("t3");
		net.fire("t2");
		assertEquals(net.getPlace("p1").getTokens(), (Integer) 1);
		assertEquals(net.getPlace("p2").getTokens(), (Integer) 0);
		assertEquals(net.getPlace("p3").getTokens(), (Integer) 1);
		assertEquals(net.getPlace("p4").getTokens(), (Integer) 1);
		assertEquals(net.getPlace("p5").getTokens(), (Integer) 0);
		
		List<String> availableTransitions = net.fireableTransitions();
		assertTrue(availableTransitions.contains("t1"));
		assertTrue(availableTransitions.contains("t3"));
	}
	
	@Test
	@Order(5)
	public void testRunTransition1() throws ElementNameNotExists, RepeatedNameElement, InvalidTokenNumber, InvalidWeightNumber, RepeatedArc, NoFireableTransition {
		net.fire("t3");
		net.fire("t2");
		net.fire("t1");
		assertEquals(net.getPlace("p1").getTokens(), (Integer) 0);
		assertEquals(net.getPlace("p2").getTokens(), (Integer) 1);
		assertEquals(net.getPlace("p3").getTokens(), (Integer) 0);
		assertEquals(net.getPlace("p4").getTokens(), (Integer) 1);
		assertEquals(net.getPlace("p5").getTokens(), (Integer) 0);
		
		List<String> availableTransitions = net.fireableTransitions();
		assertFalse(availableTransitions.contains("t1"));
		assertTrue(availableTransitions.contains("t2"));
		assertFalse(availableTransitions.contains("t3"));
		assertFalse(availableTransitions.contains("t4"));
	}
	
	@Test
	@Order(6)
	public void testRunTransition2Again() throws ElementNameNotExists, RepeatedNameElement, InvalidTokenNumber, InvalidWeightNumber, RepeatedArc, NoFireableTransition {
		net.fire("t3");
		net.fire("t2");
		net.fire("t1");
		net.fire("t2");
		assertEquals(net.getPlace("p1").getTokens(), (Integer) 1);
		assertEquals(net.getPlace("p2").getTokens(), (Integer) 0);
		assertEquals(net.getPlace("p3").getTokens(), (Integer) 1);
		assertEquals(net.getPlace("p4").getTokens(), (Integer) 1);
		assertEquals(net.getPlace("p5").getTokens(), (Integer) 0);
		
		List<String> availableTransitions = net.fireableTransitions();
		assertTrue(availableTransitions.contains("t1"));
		assertFalse(availableTransitions.contains("t2"));
		assertTrue(availableTransitions.contains("t3"));
		assertFalse(availableTransitions.contains("t4"));
	}
	
	@Test
	@Order(7)
	public void testRepeatedOutArc() throws RepeatedNameElement, InvalidTokenNumber, InvalidWeightNumber, RepeatedArc, ElementNameNotExists {
		assertThrows(RepeatedArc.class, () -> {
			net.addArc("newOutArc", "t1", "p1", "out");
		});
	}
	
	@Test
	@Order(8)
	public void testRepeatedInArc() throws RepeatedNameElement, InvalidTokenNumber, InvalidWeightNumber, RepeatedArc, ElementNameNotExists {
		assertThrows(RepeatedArc.class, () -> {
			net.addArc("newInArc", "t2", "p1", "in");
		});
	}
	
	@Test
	@Order(9)
	public void testRepeatedNamePlace() throws RepeatedNameElement, InvalidTokenNumber, InvalidWeightNumber, RepeatedArc, ElementNameNotExists {
		assertThrows(RepeatedNameElement.class, () -> {
			net.addPlace("p1");
		});
	}
	
	@Test
	@Order(10)
	public void testRepeatedNameTransition() throws RepeatedNameElement, InvalidTokenNumber, InvalidWeightNumber, RepeatedArc, ElementNameNotExists {
		assertThrows(RepeatedNameElement.class, () -> {
			net.addTransition("t1");
		});
	}
	
	@Test
	@Order(11)
	public void testRepeatedNameArc() throws RepeatedNameElement, InvalidTokenNumber, InvalidWeightNumber, RepeatedArc, ElementNameNotExists {
		assertThrows(RepeatedNameElement.class, () -> {
			net.addArc("a1_in", "t2", "p1", "in");
		});
	}
}
