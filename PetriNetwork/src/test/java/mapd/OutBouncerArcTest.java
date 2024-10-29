package mapd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import mapd.exceptions.InvalidTokenNumber;
import mapd.exceptions.InvalidWeightNumber;
import mapd.exceptions.RepeatedArc;
import mapd.implementations.OutArc;
import mapd.implementations.OutBouncerArc;
import mapd.implementations.Place;
import mapd.implementations.Transition;

public class OutBouncerArcTest {
	
	private Place testPlace;
    private Transition testTransition;

    @BeforeEach
    void setUp() throws InvalidTokenNumber {
        testPlace = new Place("p1");
        testTransition = new Transition("t1");
    }

    @Test
    @Order(1)
    void testOutBouncerArcCreation() throws InvalidWeightNumber, RepeatedArc {
        OutArc outBouncerArc = new OutBouncerArc("a1", testPlace, testTransition);
        assertEquals(testPlace, outBouncerArc.getPlace());
        assertFalse(outBouncerArc.isActive());
    }
    
    @Test
    @Order(2)
    void testOutBouncerArcActivation() throws RepeatedArc, InvalidTokenNumber, InvalidWeightNumber {
        OutArc outBouncerArc = new OutBouncerArc("a1", testPlace, testTransition);
        testPlace.setTokens(10);
        outBouncerArc.updateIsActive();
        assertTrue(outBouncerArc.isActive());
    }
    
    @Test
    @Order(3)
    void testOutBouncerArcIsFirableTransition() throws RepeatedArc, InvalidTokenNumber, InvalidWeightNumber {
        OutArc outBouncerArc = new OutBouncerArc("a1", testPlace, testTransition);
        testPlace.setTokens(10);
        outBouncerArc.updateIsActive();
        outBouncerArc.modifyTokens();
        assertEquals(testPlace.getTokens(), (Integer) 0);
    }
    
    @Test
    @Order(4)
    void testToString() throws InvalidWeightNumber, RepeatedArc {
        OutArc outBouncerArc = new OutBouncerArc("a1", testPlace, testTransition);
        assertTrue(outBouncerArc.toString().equals("bouncer out arc with weight 1 (from place with 0 tokens to transition)"));
    }

}
