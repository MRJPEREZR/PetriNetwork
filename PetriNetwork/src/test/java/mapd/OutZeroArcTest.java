package mapd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mapd.exceptions.InvalidTokenNumber;
import mapd.exceptions.RepeatedArc;
import mapd.implementations.OutArc;
import mapd.implementations.OutBouncerArc;
import mapd.implementations.OutZeroArc;
import mapd.implementations.Place;
import mapd.implementations.Transition;

public class OutZeroArcTest {
	private Place testPlace;
    private Transition testTransition;

    @BeforeEach
    void setUp() throws InvalidTokenNumber {
        testPlace = new Place("p1");
        testTransition = new Transition("t1");
    }

    @Test
    void testOutZeroArcCreation() {
        OutArc outZeroArc = new OutZeroArc("a1", testPlace);
        assertEquals(testPlace, outZeroArc.getPlace());
    }
    
    @Test
    void testOutZeroArcActivation() throws RepeatedArc {
        OutArc outZeroArc = new OutZeroArc("a1", testPlace);
        outZeroArc.addToTransition(testTransition);
        outZeroArc.setIsActive();
        assertTrue(testTransition.isFireable());
    }
    
    @Test
    void testOutZeroArcIsFirableTransition() throws RepeatedArc, InvalidTokenNumber {
        OutArc outZeroArc = new OutZeroArc("a1", testPlace);
        outZeroArc.addToTransition(testTransition);
        assertTrue(testTransition.isFireable());
    }
    
    @Test
    void testOutZeroArcFire() throws RepeatedArc {
        OutArc outZeroArc = new OutZeroArc("a1", testPlace);
        outZeroArc.addToTransition(testTransition);
        testTransition.fire();
        assertEquals(testPlace.getTokens(), (Integer) 0);
    }
}
