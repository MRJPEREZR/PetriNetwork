package mapd;

import mapd.exceptions.*;
import mapd.implementations.PetriNetwork;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PetriNetworkTest {

    private PetriNetwork petriNetwork;

    @BeforeEach
    public void setUp() {
        petriNetwork = new PetriNetwork();
    }

    @Test
    public void testAddPlaceSuccessfully() throws RepeatedNameElement, ElementNameNotExists, InvalidTokenNumber {
        petriNetwork.addPlace("P1");
        assertEquals("P1", petriNetwork.getPlace("P1").getLabel());
    }

    @Test
    public void testAddPlaceWithTokensSuccessfully() throws RepeatedNameElement, InvalidTokenNumber, ElementNameNotExists {
        petriNetwork.addPlace("P2", 5);
        assertEquals(5, petriNetwork.getPlace("P2").getTokens());
    }

    @Test
    public void testAddDuplicatePlaceThrowsException() {
        assertThrows(RepeatedNameElement.class, () -> {
            petriNetwork.addPlace("P1");
            petriNetwork.addPlace("P1");
        });
    }

    @Test
    public void testGetNonExistingPlaceThrowsException() {
        assertThrows(ElementNameNotExists.class, () -> petriNetwork.getPlace("P3"));
    }

    @Test
    public void testRemovePlaceSuccessfully() throws RepeatedNameElement, ElementNameNotExists, InvalidTokenNumber {
        petriNetwork.addPlace("P1");
        petriNetwork.rmPlace("P1");
        assertThrows(ElementNameNotExists.class, () -> petriNetwork.getPlace("P1"));
    }

    @Test
    public void testSetPlaceTokensSuccessfully() throws RepeatedNameElement, InvalidTokenNumber, ElementNameNotExists {
        petriNetwork.addPlace("P1");
        petriNetwork.setPlaceTokens("P1", 10);
        assertEquals(10, petriNetwork.getPlace("P1").getTokens());
    }

    @Test
    public void testAddTransitionSuccessfully() throws RepeatedNameElement, ElementNameNotExists {
        petriNetwork.addTransition("T1");
        assertEquals("T1", petriNetwork.getTransition("T1").getLabel());
    }
    
    @Test
    public void testRemoveTransitionSuccessfully() throws RepeatedNameElement, ElementNameNotExists {
        petriNetwork.addTransition("T1");
        petriNetwork.rmTransition("T1");
        assertThrows(ElementNameNotExists.class, () -> petriNetwork.getTransition("A1"));
    }

    @Test
    public void testAddDuplicateTransitionThrowsException() {
        assertThrows(RepeatedNameElement.class, () -> {
            petriNetwork.addTransition("T1");
            petriNetwork.addTransition("T1");
        });
    }

    @Test
    public void testGetNonExistingTransitionThrowsException() {
        assertThrows(ElementNameNotExists.class, () -> petriNetwork.getTransition("T3"));
    }

    @Test
    public void testAddAndRemoveArcSuccessfully() throws RepeatedNameElement, ElementNameNotExists, RepeatedArc, InvalidWeightNumber, InvalidTokenNumber {
        petriNetwork.addPlace("P1");
        petriNetwork.addTransition("T1");
        petriNetwork.addArc("A1", "T1", "P1", "in", 1);
        assertEquals("A1", petriNetwork.getArc("A1").getLabel());
        
        petriNetwork.rmArc("A1");
        assertThrows(ElementNameNotExists.class, () -> petriNetwork.getArc("A1"));
    }

    @Test
    public void testAddDuplicateArcThrowsException() {
        assertThrows(RepeatedNameElement.class, () -> {
            petriNetwork.addPlace("P1");
            petriNetwork.addTransition("T1");
            petriNetwork.addArc("A1", "T1", "P1", "in", 1);
            petriNetwork.addArc("A1", "T1", "P1", "in", 1);
        });
    }

    @Test
    public void testSetArcWeightSuccessfully() throws RepeatedNameElement, ElementNameNotExists, InvalidWeightNumber, RepeatedArc, InvalidTokenNumber {
        petriNetwork.addPlace("P1");
        petriNetwork.addTransition("T1");
        petriNetwork.addArc("A1", "T1", "P1", "in", 1);
        petriNetwork.setArcWeight("A1", 5);
        assertEquals(5, petriNetwork.getArc("A1").getWeight());
    }

    @Test
    public void testRenamePlaceSuccessfully() throws RepeatedNameElement, ElementNameNotExists, InvalidTokenNumber {
        petriNetwork.addPlace("P1");
        petriNetwork.renamePlace("P1", "P2");
        assertThrows(ElementNameNotExists.class, () -> petriNetwork.getPlace("P1"));
        assertEquals("P2", petriNetwork.getPlace("P2").getLabel());
    }

    @Test
    public void testRenameTransitionSuccessfully() throws RepeatedNameElement, ElementNameNotExists {
        petriNetwork.addTransition("T1");
        petriNetwork.renameTransition("T1", "T2");
        assertThrows(ElementNameNotExists.class, () -> petriNetwork.getTransition("T1"));
        assertEquals("T2", petriNetwork.getTransition("T2").getLabel());
    }
    
    @Test
    public void testRenameArcSuccessfully() throws RepeatedNameElement, ElementNameNotExists, InvalidTokenNumber, InvalidWeightNumber, RepeatedArc {
    	petriNetwork.addPlace("P1");
        petriNetwork.addTransition("T1");
        petriNetwork.addArc("A1", "T1", "P1", "in", 1);
        petriNetwork.renameArc("A1", "A1*");
        
    }

    @Test
    public void testFireableTransitionsWhenNoTransitionsAreFireable() {
        assertTrue(petriNetwork.fireableTransitions().isEmpty());
    }

}
