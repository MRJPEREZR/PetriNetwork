package mapd;

import mapd.implementations.InArc;
import mapd.implementations.Place;
import mapd.implementations.Transition;
import mapd.exceptions.InvalidWeightNumber;
import mapd.exceptions.RepeatedArc;
import mapd.exceptions.InvalidTokenNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InArcTest {

    private Place testPlace;
    private Transition testTransition;

    @BeforeEach
    void setUp() throws InvalidTokenNumber {
        testPlace = new Place("p", 5);
        testTransition = new Transition("t");
    }

    @Test
    @Order(1)
    void testInArcCreationWithDefaultWeight() throws InvalidWeightNumber, RepeatedArc {
        InArc inArc = new InArc("a1",testPlace, testTransition);
        assertEquals(testPlace, inArc.getPlace());
        assertEquals(1, inArc.getWeight());
    }

    @Test
    @Order(2)
    void testInArcCreationWithValidWeight() throws InvalidWeightNumber, RepeatedArc {
        InArc inArc = new InArc("a1",testPlace, testTransition, 3);
        assertEquals(testPlace, inArc.getPlace());
        assertEquals(3, inArc.getWeight());
    }

    @Test
    @Order(3)
    void testInArcCreationWithInvalidWeight() {
        InvalidWeightNumber thrown = assertThrows(InvalidWeightNumber.class, () -> {
            new InArc("a1",testPlace, testTransition, 0);
        });
        assertEquals("Invalid weight < 1", thrown.getMessage());
    }

    @Test
    @Order(4)
    void testModifyTokens() throws InvalidTokenNumber, InvalidWeightNumber, RepeatedArc {
        InArc inArc = new InArc("a1",testPlace, testTransition, 2);
        inArc.modifyTokens();
        assertEquals(7, testPlace.getTokens());
    }

    @Test
    @Order(5)
    void testModifyTokensWithDefaultWeight() throws InvalidTokenNumber, InvalidWeightNumber, RepeatedArc {
        InArc inArc = new InArc("a1",testPlace, testTransition);
        inArc.modifyTokens();
        assertEquals(6, testPlace.getTokens());
    }

    @Test
    @Order(6)
    void testAddToTransition() throws RepeatedArc, InvalidWeightNumber {
        InArc inArc = new InArc("a1",testPlace, testTransition);
        assertTrue(testTransition.getInArcs().contains(inArc));
    }

    @Test
    @Order(7)
    void testToString() throws InvalidWeightNumber, RepeatedArc {
        InArc inArc = new InArc("a1",testPlace, testTransition, 4);
        assertTrue(inArc.toString().equals("simple in arc with weight 4 (transition to place with 5 tokens)"));
    }
    
    @Test
    @Order(8)
    void testToChangeLabel() throws InvalidWeightNumber, RepeatedArc {
        InArc inArc = new InArc("a1",testPlace, testTransition, 4);
        inArc.setLabel("a2");
        assertEquals("a2", inArc.getLabel());
    }
}
