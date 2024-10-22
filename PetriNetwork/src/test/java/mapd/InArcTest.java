package mapd;

import mapd.implementations.InArc;
import mapd.implementations.Place;
import mapd.implementations.Transition;
import mapd.exceptions.InvalidWeightNumber;
import mapd.exceptions.RepeatedArc;
import mapd.exceptions.InvalidTokenNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InArcTest {

    private Place testPlace;
    private Transition testTransition;

    @BeforeEach
    void setUp() throws InvalidTokenNumber {
        testPlace = new Place("p");
        testPlace.setTokens(5);
        testTransition = new Transition("t");
    }

    @Test
    void testInArcCreationWithDefaultWeight() {
        InArc inArc = new InArc("a1",testPlace);
        assertEquals(testPlace, inArc.getPlace());
        assertEquals(1, inArc.getWeight());
    }

    @Test
    void testInArcCreationWithValidWeight() throws InvalidWeightNumber {
        InArc inArc = new InArc("a1",testPlace, 3);
        assertEquals(testPlace, inArc.getPlace());
        assertEquals(3, inArc.getWeight());
    }

    @Test
    void testInArcCreationWithInvalidWeight() {
        InvalidWeightNumber thrown = assertThrows(InvalidWeightNumber.class, () -> {
            new InArc("a1",testPlace, 0);
        });
        assertEquals("Invalid weight < 1", thrown.getMessage());
    }

    @Test
    void testModifyTokens() throws InvalidTokenNumber, InvalidWeightNumber {
        InArc inArc = new InArc("a1",testPlace, 2);
        inArc.modifyTokens();
        assertEquals(7, testPlace.getTokens());
    }

    @Test
    void testModifyTokensWithDefaultWeight() throws InvalidTokenNumber {
        InArc inArc = new InArc("a1",testPlace);
        inArc.modifyTokens();
        assertEquals(6, testPlace.getTokens());
    }

    @Test
    void testAddToTransition() throws RepeatedArc {
        InArc inArc = new InArc("a1",testPlace);
        inArc.addToTransition(testTransition);
        assertTrue(testTransition.getInArcs().contains(inArc));
    }

    @Test
    void testToString() throws InvalidWeightNumber {
        InArc inArc = new InArc("a1",testPlace, 4);
        System.out.println(inArc.toString());
        assertTrue(inArc.toString().equals("In arc has weight 4"));
    }
}
