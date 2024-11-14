package mapd;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import mapd.exceptions.ElementNameNotExists;
import mapd.exceptions.InvalidTokenNumber;
import mapd.exceptions.InvalidWeightNumber;
import mapd.exceptions.RepeatedArc;
import mapd.exceptions.RepeatedNameElement;
import mapd.implementations.PetriNetwork;

public class showElementsTest {
	
	private final PrintStream standardOut = System.out;
	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
	
	PetriNetwork net = PetriNetwork.getInstance();

	@BeforeEach
	public void setUp() throws InvalidTokenNumber, RepeatedNameElement, ElementNameNotExists, InvalidWeightNumber, RepeatedArc {
		net.reset();
		net.addPlace("p1", 1);
		net.addPlace("p2", 3);
		net.addPlace("p3");
		net.addPlace("p4", 1);
		
		net.addTransition("t1");
		
		net.addArc("a1", "t1", "p1", "out");
		net.addArc("a2", "t1", "p2", "out", 2);
		
		net.addArc("a3", "t1", "p3", "in", 3);
		net.addArc("a4", "t1", "p4", "in");
		
		System.setOut(new PrintStream(outputStreamCaptor));
	}
	
	@AfterEach
	public void tearDown() {
	    System.setOut(standardOut);
	}
	
	@Test
	@Order(1)
	public void testShowPlaces() {
	    net.showPlaces();
	    assertEquals(net.getPlaces().toString(), outputStreamCaptor.toString().trim());
	}
	
	@Test
	@Order(2)
	public void testShowTransitions() {
	    net.showTransitions();
	    assertEquals(net.getTransitions().toString(), outputStreamCaptor.toString()
	      .trim());
	}
	
	@Test
	@Order(3)
	public void testShowArcs() {
	    net.showArcs();
	    assertEquals(net.getArcs().toString(), outputStreamCaptor.toString()
	      .trim());
	}
	
	@Test
	@Order(4)
	public void testShowAllElements() {
		net.showAllElements();
		String message = 
			    "Petri Network\n" +
			    "4 places\n" +
			    "1 transitions\n" +
			    "4 arcs\n" +
			    "List of places:\n" +
			    "1: place with 1 tokens, 1 simple out arcs, 0 in arcs, 0 bouncer out arcs, 0 zero out arcs\n" +
			    "2: place with 3 tokens, 1 simple out arcs, 0 in arcs, 0 bouncer out arcs, 0 zero out arcs\n" +
			    "3: place with 0 tokens, 0 simple out arcs, 1 in arcs, 0 bouncer out arcs, 0 zero out arcs\n" +
			    "4: place with 1 tokens, 0 simple out arcs, 1 in arcs, 0 bouncer out arcs, 0 zero out arcs\n" +
			    "List of transitions:\n" +
			    "1: t1, 2 in arcs, 2 out arcs\n" +
			    "List of arcs:\n" +
			    "1: simple out arc with weight 1 (from place with 1 tokens to transition)\n" +
			    "2: simple out arc with weight 2 (from place with 3 tokens to transition)\n" +
			    "3: simple in arc with weight 3 (transition to place with 0 tokens)\n" +
			    "4: simple in arc with weight 1 (transition to place with 1 tokens)";
		assertEquals(message, outputStreamCaptor.toString().trim());
		
	}
	

}
