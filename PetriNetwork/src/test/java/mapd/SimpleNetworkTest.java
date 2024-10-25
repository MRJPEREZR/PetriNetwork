package mapd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import mapd.exceptions.ElementNameNotExists;
import mapd.exceptions.InvalidTokenNumber;
import mapd.exceptions.InvalidWeightNumber;
import mapd.exceptions.RepeatedArc;
import mapd.exceptions.RepeatedNameElement;
import mapd.implementations.PetriNetwork;

public class SimpleNetworkTest {
	
	PetriNetwork net = new PetriNetwork();
	
	public void setup() throws RepeatedNameElement, InvalidWeightNumber, RepeatedArc, ElementNameNotExists, InvalidTokenNumber {
		
		net.addPlace("p1", 1);
		net.addPlace("p2", 3);
		net.addPlace("p3");
		net.addPlace("p4", 1);
		
		net.addTransition("t1");
		
		net.addArc("a1", "t1", "p1", "out");
		net.addArc("a2", "t1", "p2", "out", 2);
		
		net.addArc("a3", "t1", "p3", "in", 3);
		net.addArc("a4", "t1", "p4", "in");
	}
	
	@Test
	public void testCurrentPlaceTokens() throws RepeatedNameElement, InvalidWeightNumber, RepeatedArc, ElementNameNotExists, InvalidTokenNumber {
		setup();
		assertEquals(net.getPlace("p1").getTokens(), (Integer) 1);
		assertEquals(net.getPlace("p2").getTokens(), (Integer) 3);
		assertEquals(net.getPlace("p3").getTokens(), (Integer) 0);
		assertEquals(net.getPlace("p4").getTokens(), (Integer) 1);
	}
	
	@Test
	public void testAvailableTransitions() throws RepeatedNameElement, InvalidTokenNumber, InvalidWeightNumber, RepeatedArc, ElementNameNotExists {
		setup();
		List<String> availableTransitions = net.fireableTransitions();
		assertTrue(availableTransitions.contains("t1"));
	}
	
	@Test
	public void testRunTransition1() throws ElementNameNotExists, RepeatedNameElement, InvalidTokenNumber, InvalidWeightNumber, RepeatedArc {
		setup();
		net.fire("t1");
		assertEquals(net.getPlace("p1").getTokens(), (Integer) 0);
		assertEquals(net.getPlace("p2").getTokens(), (Integer) 1);
		assertEquals(net.getPlace("p3").getTokens(), (Integer) 3);
		assertEquals(net.getPlace("p4").getTokens(), (Integer) 2);	
		
		List<String> availableTransitions = net.fireableTransitions();
		assertTrue(availableTransitions.isEmpty());
	}
	
	@Test
	public void testSetArcWeight() throws RepeatedNameElement, InvalidWeightNumber, RepeatedArc, ElementNameNotExists, InvalidTokenNumber {
		setup();
		net.setArcWeight("a1", 2);
		assertEquals(net.getArc("a1").getWeight(), 2);
	}
	
	@Test
	public void testSetPlaceTokens() throws RepeatedNameElement, InvalidWeightNumber, RepeatedArc, ElementNameNotExists, InvalidTokenNumber {
		setup();
		net.setPlaceTokens("p1", 10);
		assertEquals(net.getPlace("p1").getTokens(), (Integer) 10);
	}
	
	@Test
	public void testSetPlaceTokensWrongName() throws RepeatedNameElement, InvalidWeightNumber, RepeatedArc, ElementNameNotExists, InvalidTokenNumber {
		setup();
		assertThrows(ElementNameNotExists.class, () -> {
			net.setPlaceTokens("placeNotExist", 3);
		});
	}
	
	@Test
	public void testRemovePlaceNotExist() throws RepeatedNameElement, InvalidWeightNumber, RepeatedArc, ElementNameNotExists, InvalidTokenNumber {
		setup();
		assertThrows(ElementNameNotExists.class, () -> {
			net.rmPlace("PlaceNotExist");
		});
	}
	
	@Test
	public void testRemoveTransitionNotExist() throws RepeatedNameElement, InvalidWeightNumber, RepeatedArc, ElementNameNotExists, InvalidTokenNumber {
		setup();
		assertThrows(ElementNameNotExists.class, () -> {
			net.rmTransition("TransitionNotExist");
		});
	}
	
	@Test
	public void testRemoveArcNotExist() throws RepeatedNameElement, InvalidWeightNumber, RepeatedArc, ElementNameNotExists, InvalidTokenNumber {
		setup();
		assertThrows(ElementNameNotExists.class, () -> {
			net.rmArc("ArcNotExist");
		});
	}
	
	
	
	
	

}
