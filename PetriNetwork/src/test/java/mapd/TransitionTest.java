package mapd;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import mapd.implementations.*;
import mapd.exceptions.InvalidTokenNumber;
import mapd.exceptions.InvalidWeightNumber;
import mapd.exceptions.RepeatedArc;

public class TransitionTest {

    private Transition transition;
    private InArc inArc;
    private OutArc outArc;
    private Place place;

    @BeforeEach
    public void setUp() throws InvalidTokenNumber, RepeatedArc {
        transition = new Transition("t1");
        place = new Place("p1");
    }

    @Test
    @Order(1)
    public void testDefaultConstructor() {
        assertTrue(transition.getInArcs().isEmpty());
        assertTrue(transition.getOutArcs().isEmpty());
    }

    @Test
    @Order(2)
    public void testArcs() throws RepeatedArc, InvalidWeightNumber {
    	inArc = new InArc("a1", place, transition);
        outArc = new OutArc("a2", place, transition);
        assertEquals(1, transition.getInArcs().size());
        assertTrue(transition.getInArcs().contains(inArc));
        assertEquals(1, transition.getOutArcs().size());
        assertTrue(transition.getOutArcs().contains(outArc));
    }

    @Test
    @Order(3)
    public void testAddDuplicateInArc() throws RepeatedArc, InvalidWeightNumber {
    	inArc = new InArc("a1", place, transition);
        RepeatedArc thrown = assertThrows(RepeatedArc.class, () -> {
        	transition.addInArc(inArc);
        });
        assertEquals("An Arc in the same direction already exists", thrown.getMessage());
    }

    @Test
    @Order(4)
    public void testAddDuplicateOutArc() throws RepeatedArc, InvalidWeightNumber {
    	outArc = new OutArc("a2", place, transition);
        RepeatedArc thrown = assertThrows(RepeatedArc.class, () -> {
        	transition.addOutArc(outArc);
        });
        assertEquals("An Arc in the same direction already exists", thrown.getMessage());
    }

    @Test
    @Order(5)
    public void testRemoveInArc() throws RepeatedArc, InvalidWeightNumber {
    	inArc = new InArc("a1", place, transition);
        transition.rmInArc(inArc);
        assertFalse(transition.getInArcs().contains(inArc));
    }

    @Test
    @Order(6)
    public void testRemoveOutArc() throws RepeatedArc, InvalidWeightNumber {
    	outArc = new OutArc("a2", place, transition);
        transition.rmOutArc(outArc);
        assertFalse(transition.getOutArcs().contains(outArc));
    }

    @Test
    @Order(7)
    public void testIsFireableWithActiveOutArc() throws InvalidTokenNumber, RepeatedArc, InvalidWeightNumber {
    	this.place.setTokens(1);
    	outArc = new OutArc("a2", place, transition);
        transition.getOutArcs().get(0).isActive();
        assertTrue(transition.isFireable());
    }

    @Test
    @Order(8)
    public void testIsNotFireable() throws RepeatedArc, InvalidWeightNumber {
    	outArc = new OutArc("a2", place, transition);
        transition.getOutArcs().get(0).isActive();
        assertFalse(transition.isFireable());
    }

    @Test
    @Order(9)
    public void testFireWhenFireable() throws InvalidTokenNumber, RepeatedArc, InvalidWeightNumber {
    	this.place.setTokens(1);
    	Place place2 = new Place("p2");
    	inArc = new InArc("a1", place2, transition);
        outArc = new OutArc("a2", place, transition);
        assertTrue(transition.isFireable());
        transition.fire();
        assertFalse(transition.isFireable());
        assertEquals(0, transition.getOutArcs().get(0).getPlace().getTokens());
        assertEquals(1, transition.getInArcs().get(0).getPlace().getTokens());
    }
    
    @Test
    @Order(10)
    public void testFireWhenFireableAndNotHaveOutArcs() throws InvalidTokenNumber, RepeatedArc, InvalidWeightNumber {
    	inArc = new InArc("a1", place, transition);
    	assertTrue(transition.isFireable());
        transition.fire();
        assertEquals(1, transition.getInArcs().get(0).getPlace().getTokens());
    }


    @Test
    @Order(11)
    public void testToString() {
        String expected = "InArcs: " + transition.getInArcs() + "OutArcs: " + transition.getOutArcs();
        assertTrue(transition.toString().equals(expected));
    }
}
