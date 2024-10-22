package mapd;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import mapd.implementations.*;
import mapd.exceptions.InvalidTokenNumber;

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
    public void testIsFireableWithActiveOutArc() throws InvalidTokenNumber {
    	this.place.setTokens(1);
        transition.addOutArc(outArc);
        transition.getOutArcs().get(0).setIsActive();
        assertTrue(transition.isFireable());
    }

    @Test
    public void testIsNotFireable() {
        transition.addOutArc(outArc);
        transition.getOutArcs().get(0).setIsActive();
        assertFalse(transition.isFireable());
    }

    @Test
    public void testFireWhenFireable() throws InvalidTokenNumber {
    	this.place.setTokens(1);
        transition.addOutArc(outArc);
        Place anotherPlace = new Place();
        InArc anotherInArc = new InArc(anotherPlace);
        transition.addInArc(anotherInArc);
        transition.getOutArcs().get(0).setIsActive();
        transition.fire();
        assertFalse(transition.isFireable());
        assertEquals(0, transition.getOutArcs().get(0).getPlace().getTokens());
        assertEquals(1, transition.getInArcs().get(0).getPlace().getTokens());
    }

    @Test
    public void testFireWhenNotFireable() {
        transition.addOutArc(outArc);
        Place anotherPlace = new Place();
        InArc anotherInArc = new InArc(anotherPlace);
        transition.addInArc(anotherInArc);
        transition.getOutArcs().get(0).setIsActive();
        transition.fire();
        assertEquals(0, transition.getOutArcs().get(0).getPlace().getTokens());
        assertEquals(0, transition.getInArcs().get(0).getPlace().getTokens());
    }

    @Test
    public void testToString() {
        String expected = "InArcs" + transition.getInArcs() + "OutArcs" + transition.getOutArcs();
        assertEquals(expected, transition.toString());
    }
}
