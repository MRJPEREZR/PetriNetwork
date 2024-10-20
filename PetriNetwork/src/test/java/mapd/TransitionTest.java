import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import mapd.implementations.*;
import mapd.exceptions.InvalidTokenNumber;

import java.util.ArrayList;
import java.util.List;

public class TransitionTest {

    private Transition transition;
    private InArc inArc;
    private OutArc outArc;
    private Place place;

    @BeforeEach
    public void setUp() {
        transition = new Transition();
        place = new Place();
        inArc = new InArc(place);
        outArc = new OutArc(place);
    }

    @Test
    public void testDefaultConstructor() {
        assertTrue(transition.getInArcs().isEmpty());
        assertTrue(transition.getOutArcs().isEmpty());
    }

    @Test
    public void testAddInArc() {
        transition.addInArc(inArc);
        assertEquals(1, transition.getInArcs().size());
        assertTrue(transition.getInArcs().contains(inArc));
    }

    @Test
    public void testAddOutArc() {
        transition.addOutArc(outArc);
        assertEquals(1, transition.getOutArcs().size());
        assertTrue(transition.getOutArcs().contains(outArc));
    }

    @Test
    public void testAddDuplicateInArc() {
        transition.addInArc(inArc);
        transition.addInArc(inArc);
        assertEquals(1, transition.getInArcs().size());
    }

    @Test
    public void testAddDuplicateOutArc() {
        transition.addOutArc(outArc);
        transition.addOutArc(outArc);
        assertEquals(1, transition.getOutArcs().size());
    }

    @Test
    public void testRemoveInArc() {
        transition.addInArc(inArc);
        transition.rmInArc(inArc);
        assertFalse(transition.getInArcs().contains(inArc));
    }

    @Test
    public void testRemoveOutArc() {
        transition.addOutArc(outArc);
        transition.rmOutArc(outArc);
        assertFalse(transition.getOutArcs().contains(outArc));
    }

    @Test
    public void testIsFireableWithActiveOutArc() {
        outArc.setIsActive(true);
        transition.addOutArc(outArc);
        assertTrue(transition.isFireable());
    }

    @Test
    public void testIsNotFireable() {
        outArc.setIsActive(false);
        transition.addOutArc(outArc);
        assertFalse(transition.isFireable());
    }

    @Test
    public void testFireWhenFireable() throws InvalidTokenNumber {
        outArc.setIsActive(true);
        transition.addOutArc(outArc);
        transition.addInArc(inArc);
        transition.fire();
    }

    @Test
    public void testFireWhenNotFireable() {
        outArc.setIsActive(false);
        transition.addOutArc(outArc);
        transition.fire();
    }

    @Test
    public void testToString() {
        String expected = "InArcs" + transition.getInArcs() + "OutArcs" + transition.getOutArcs();
        assertEquals(expected, transition.toString());
    }
}
