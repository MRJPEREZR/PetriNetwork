package mapd;

import mapd.implementations.OutArc;
import mapd.implementations.Place;
import mapd.implementations.Transition;
import mapd.exceptions.InvalidWeightNumber;
import mapd.exceptions.RepeatedArc;
import mapd.exceptions.InvalidTokenNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OutArcTest {

    private Place testPlace;
    private Transition testTransition;

    @BeforeEach
    void setUp() throws InvalidTokenNumber {
        testPlace = new Place("p1");
        testPlace.setTokens(5);
        testTransition = new Transition("t1");
    }

    @Test
    @Order(1)
    void testOutArcCreationWithDefaultWeight() throws InvalidWeightNumber, RepeatedArc {
        OutArc outArc = new OutArc("a1", testPlace, testTransition);
        assertEquals(testPlace, outArc.getPlace());
        assertEquals(1, outArc.getWeight());
        assertTrue(outArc.isActive());
    }

    @Test
    @Order(2)
    void testOutArcCreationWithValidWeight() throws InvalidWeightNumber, RepeatedArc {
        OutArc outArc = new OutArc("a1", testPlace, testTransition, 3);
        assertEquals(testPlace, outArc.getPlace());
        assertEquals(3, outArc.getWeight());
        assertTrue(outArc.isActive());
    }

    @Test
    @Order(3)
    void testOutArcCreationWithInvalidWeight() {
        InvalidWeightNumber thrown = assertThrows(InvalidWeightNumber.class, () -> {
            new OutArc("a1", testPlace, testTransition, 0);
        });
        assertEquals("Invalid weight < 1", thrown.getMessage());
    }

    @Test
    @Order(4)
    void testIsActiveWhenTokensAreNotEnough() throws InvalidWeightNumber, RepeatedArc {
        OutArc outArc = new OutArc("a1", testPlace, testTransition, 6);
        assertFalse(outArc.isActive());
    }

    @Test
    @Order(5)
    void testModifyTokensWhenActive() throws InvalidTokenNumber, InvalidWeightNumber, RepeatedArc {
        OutArc outArc = new OutArc("a1", testPlace, testTransition, 2);
        outArc.modifyTokens();
        assertEquals(3, testPlace.getTokens());
    }

    @Test
    @Order(6)
    void testModifyTokensWhenNotActive() throws InvalidWeightNumber, InvalidTokenNumber, RepeatedArc {
        OutArc outArc = new OutArc("a1", testPlace, testTransition, 6);
        outArc.modifyTokens();
        assertEquals(5, testPlace.getTokens());
    }

    @Test
    @Order(7)
    void testAddToTransition() throws RepeatedArc, InvalidWeightNumber {
        OutArc outArc = new OutArc("a1", testPlace, testTransition);
        assertTrue(testTransition.getOutArcs().contains(outArc));
    }

    @Test
    @Order(8)
    void testToString() throws InvalidWeightNumber, RepeatedArc {
        OutArc outArc = new OutArc("a1", testPlace, testTransition, 4);
        assertTrue(outArc.toString().equals("Conventional out arc has weight 4 and it is true"));
    }
    
    @Test
    @Order(9)
    void testModifyTokensNoValidAfter() throws InvalidWeightNumber, InvalidTokenNumber, RepeatedArc {
        OutArc outArc = new OutArc("a1", testPlace, testTransition, 3);
        outArc.modifyTokens();
        outArc.modifyTokens();
        assertFalse(outArc.isActive());
    }
}
