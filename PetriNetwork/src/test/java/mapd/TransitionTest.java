package mapd;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import mapd.implementations.*;
import mapd.exceptions.InvalidTokenNumber;
import mapd.exceptions.RepeatedArc;

public class TransitionTest {

    private Transition transition;
    private InArc inArc;
    private OutArc outArc;
    private Place place;

    @BeforeEach
    public void setUp() {
        transition = new Transition("t");
        place = new Place("p");
        inArc = new InArc("a",place);
        outArc = new OutArc("a",place);
    }

    @Test
    public void testDefaultConstructor() {
        assertTrue(transition.getInArcs().isEmpty());
        assertTrue(transition.getOutArcs().isEmpty());
    }

    @Test
    public void testAddInArc() throws RepeatedArc {
        transition.addInArc(inArc);
        assertEquals(1, transition.getInArcs().size());
        assertTrue(transition.getInArcs().contains(inArc));
    }

    @Test
    public void testAddOutArc() throws RepeatedArc {
        transition.addOutArc(outArc);
        assertEquals(1, transition.getOutArcs().size());
        assertTrue(transition.getOutArcs().contains(outArc));
    }

    @Test
    public void testAddDuplicateInArc() throws RepeatedArc {
        transition.addInArc(inArc);
        assertThrows(RepeatedArc.class, () -> {
        	transition.addInArc(inArc);
        });
    }

    @Test
    public void testAddDuplicateOutArc() throws RepeatedArc {
        transition.addOutArc(outArc);
        assertThrows(RepeatedArc.class, () -> {
        	transition.addOutArc(outArc);
        });
    }

    @Test
    public void testRemoveInArc() throws RepeatedArc {
        transition.addInArc(inArc);
        transition.rmInArc(inArc);
        assertFalse(transition.getInArcs().contains(inArc));
    }

    @Test
    public void testRemoveOutArc() throws RepeatedArc {
        transition.addOutArc(outArc);
        transition.rmOutArc(outArc);
        assertFalse(transition.getOutArcs().contains(outArc));
    }

    @Test
    public void testIsFireableWithActiveOutArc() throws InvalidTokenNumber, RepeatedArc {
    	this.place.setTokens(1);
        transition.addOutArc(outArc);
        transition.getOutArcs().get(0).setIsActive();
        assertTrue(transition.isFireable());
    }

    @Test
    public void testIsNotFireable() throws RepeatedArc {
        transition.addOutArc(outArc);
        transition.getOutArcs().get(0).setIsActive();
        assertFalse(transition.isFireable());
    }

    @Test
    public void testFireWhenFireable() throws InvalidTokenNumber, RepeatedArc {
    	this.place.setTokens(1);
        transition.addOutArc(outArc);
        Place anotherPlace = new Place("p1");
        InArc anotherInArc = new InArc("a1", anotherPlace);
        transition.addInArc(anotherInArc);
        transition.getOutArcs().get(0).setIsActive();
        transition.fire();
        assertFalse(transition.isFireable());
        assertEquals(0, transition.getOutArcs().get(0).getPlace().getTokens());
        assertEquals(1, transition.getInArcs().get(0).getPlace().getTokens());
    }

    @Test
    public void testFireWhenNotFireable() throws RepeatedArc {
        transition.addOutArc(outArc);
        Place anotherPlace = new Place("p1");
        InArc anotherInArc = new InArc("a1", anotherPlace);
        transition.addInArc(anotherInArc);
        transition.getOutArcs().get(0).setIsActive();
        transition.fire();
        assertEquals(0, transition.getOutArcs().get(0).getPlace().getTokens());
        assertEquals(0, transition.getInArcs().get(0).getPlace().getTokens());
    }

    @Test
    public void testToString() {
    	// return "InArcs: " + this.getInArcs() + "OutArcs: " + this.getOutArcs();
        String expected = "InArcs: " + transition.getInArcs() + "OutArcs: " + transition.getOutArcs();
        assertTrue(transition.toString().equals(expected));
    }
}
