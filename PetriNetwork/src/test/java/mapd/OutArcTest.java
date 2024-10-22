package mapd;

import mapd.implementations.OutArc;
import mapd.implementations.Place;
import mapd.implementations.Transition;
import mapd.exceptions.InvalidWeightNumber;
import mapd.exceptions.RepeatedArc;
import mapd.exceptions.InvalidTokenNumber;
import org.junit.jupiter.api.BeforeEach;
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
    void testOutArcCreationWithDefaultWeight() {
        OutArc outArc = new OutArc("a1", testPlace);
        assertEquals(testPlace, outArc.getPlace());
        assertEquals(1, outArc.getWeight());
        assertTrue(outArc.getIsActive());
    }

    @Test
    void testOutArcCreationWithValidWeight() throws InvalidWeightNumber {
        OutArc outArc = new OutArc("a1", testPlace, 3);
        assertEquals(testPlace, outArc.getPlace());
        assertEquals(3, outArc.getWeight());
        assertTrue(outArc.getIsActive());
    }

    @Test
    void testOutArcCreationWithInvalidWeight() {
        InvalidWeightNumber thrown = assertThrows(InvalidWeightNumber.class, () -> {
            new OutArc("a1", testPlace, 0);
        });
        assertEquals("Invalid weight < 1", thrown.getMessage());
    }

    @Test
    void testIsActiveWhenTokensAreNotEnough() throws InvalidWeightNumber {
        OutArc outArc = new OutArc("a1", testPlace, 6);
        assertFalse(outArc.getIsActive());
    }

    @Test
    void testModifyTokensWhenActive() throws InvalidTokenNumber, InvalidWeightNumber {
        OutArc outArc = new OutArc("a1", testPlace, 2);
        outArc.modifyTokens();
        assertEquals(3, testPlace.getTokens());
    }

    @Test
    void testModifyTokensWhenNotActive() throws InvalidWeightNumber, InvalidTokenNumber {
        OutArc outArc = new OutArc("a1", testPlace, 6);
        outArc.modifyTokens();
        assertEquals(5, testPlace.getTokens());
    }

    @Test
    void testAddToTransition() throws RepeatedArc {
        OutArc outArc = new OutArc("a1", testPlace);
        outArc.addToTransition(testTransition);
        assertTrue(testTransition.getOutArcs().contains(outArc));
    }

    @Test
    void testToString() throws InvalidWeightNumber {
        OutArc outArc = new OutArc("a1", testPlace, 4);
        assertTrue(outArc.toString().equals("Conventional out arc has weight 4 and it is true"));
    }
}
