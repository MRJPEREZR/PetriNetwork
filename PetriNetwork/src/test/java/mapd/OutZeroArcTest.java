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
    @Order(1)
    void testOutZeroArcCreation() throws InvalidWeightNumber, RepeatedArc {
        OutArc outZeroArc = new OutZeroArc("a1", testPlace, testTransition);
        assertEquals(testPlace, outZeroArc.getPlace());
    }
    
    @Test
    @Order(2)
    void testOutZeroActive() throws RepeatedArc, InvalidWeightNumber {
        OutArc outZeroArc = new OutZeroArc("a1", testPlace, testTransition);
        assertTrue(outZeroArc.isActive());
    }
    
    @Test
    @Order(3)
    void testOutZeroUnactive() throws RepeatedArc, InvalidWeightNumber, InvalidTokenNumber {
    	testPlace.setTokens(3);
        OutArc outZeroArc = new OutZeroArc("a1", testPlace, testTransition);
        assertFalse(outZeroArc.isActive());
    }
}
