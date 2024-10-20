import mapd.implementations.OutArc;
import mapd.implementations.Place;
import mapd.implementations.Transition;
import mapd.exceptions.InvalidWeightNumber;
import mapd.exceptions.InvalidTokenNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OutArcTest {

    private Place testPlace;
    private Transition testTransition;

    @BeforeEach
    void setUp() {
        testPlace = new Place();
        testPlace.setTokens(5);
        testTransition = new Transition();
    }

    @Test
    void testOutArcCreationWithDefaultWeight() {
        OutArc outArc = new OutArc(testPlace);
        assertEquals(testPlace, outArc.getPlace());
        assertEquals(1, outArc.getWeight());
        assertTrue(outArc.getIsActive());
    }

    @Test
    void testOutArcCreationWithValidWeight() throws InvalidWeightNumber {
        OutArc outArc = new OutArc(testPlace, 3);
        assertEquals(testPlace, outArc.getPlace());
        assertEquals(3, outArc.getWeight());
        assertTrue(outArc.getIsActive());
    }

    @Test
    void testOutArcCreationWithInvalidWeight() {
        InvalidWeightNumber thrown = assertThrows(InvalidWeightNumber.class, () -> {
            new OutArc(testPlace, 0);
        });
        assertEquals("Invalid weight < 1", thrown.getMessage());
    }

    @Test
    void testIsActiveWhenTokensAreNotEnough() throws InvalidWeightNumber {
        OutArc outArc = new OutArc(testPlace, 6);
        assertFalse(outArc.getIsActive());
    }

    @Test
    void testModifyTokensWhenActive() throws InvalidTokenNumber, InvalidWeightNumber {
        OutArc outArc = new OutArc(testPlace, 2);
        outArc.modifyTokens();
        assertEquals(3, testPlace.getTokens());
    }

    @Test
    void testModifyTokensWhenNotActive() throws InvalidWeightNumber, InvalidTokenNumber {
        OutArc outArc = new OutArc(testPlace, 6);
        outArc.modifyTokens();
        assertEquals(5, testPlace.getTokens());
    }

    @Test
    void testAddToTransition() {
        OutArc outArc = new OutArc(testPlace);
        outArc.addToTransition(testTransition);
        assertTrue(testTransition.getOutArcs().contains(outArc));
    }

    @Test
    void testToString() throws InvalidWeightNumber {
        OutArc outArc = new OutArc(testPlace, 4);
        assertEquals("Arc has weight 4 and it is true", outArc.toString());
    }
}
