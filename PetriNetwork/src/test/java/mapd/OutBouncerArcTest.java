package mapd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mapd.exceptions.InvalidTokenNumber;
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
    void testOutBouncerArcCreation() {
        OutArc outBouncerArc = new OutBouncerArc("a1", testPlace);
        assertEquals(testPlace, outBouncerArc.getPlace());
        assertFalse(outBouncerArc.getIsActive());
    }
    
    @Test
    void testOutBouncerArcActivation() throws RepeatedArc, InvalidTokenNumber {
        OutArc outBouncerArc = new OutBouncerArc("a1", testPlace);
        outBouncerArc.addToTransition(testTransition);
        testPlace.setTokens(10);
        outBouncerArc.setIsActive();
        assertTrue(testTransition.isFireable());
    }
    
    @Test
    void testOutBouncerArcIsFirableTransition() throws RepeatedArc, InvalidTokenNumber {
        OutArc outBouncerArc = new OutBouncerArc("a1", testPlace);
        outBouncerArc.addToTransition(testTransition);
        testPlace.setTokens(10);
        assertTrue(testTransition.isFireable());
    }
    
    @Test
    void testOutBouncerFire() throws RepeatedArc, InvalidTokenNumber {
        OutArc outBouncerArc = new OutBouncerArc("a1", testPlace);
        outBouncerArc.addToTransition(testTransition);
        testPlace.setTokens(10);
        testTransition.isFireable();
        testTransition.fire();
        assertEquals(testPlace.getTokens(), (Integer) 0);
    }

}
