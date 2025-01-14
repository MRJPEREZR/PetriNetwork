package mapd;

import mapd.exceptions.*;
import mapd.implementations.OutBouncerArc;
import mapd.implementations.OutZeroArc;
import mapd.implementations.PetriNetwork;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PetriNetworkTest {

    private PetriNetwork petriNetwork;

    @BeforeEach
    public void setUp() {
        petriNetwork = PetriNetwork.getInstance();
        petriNetwork.reset();
    }

    @Test
    @Order(1)
    public void testAddPlaceSuccessfully() throws RepeatedNameElement, ElementNameNotExists, InvalidTokenNumber {
        petriNetwork.addPlace("P1");
        assertEquals("P1", petriNetwork.getPlace("P1").getLabel());
    }

    @Test
    @Order(2)
    public void testAddPlaceWithTokensSuccessfully() throws RepeatedNameElement, InvalidTokenNumber, ElementNameNotExists {
        petriNetwork.addPlace("P2", 5);
        assertEquals(5, petriNetwork.getPlace("P2").getTokens());
    }

    @Test
    @Order(3)
    public void testAddDuplicatePlaceThrowsException() {
        assertThrows(RepeatedNameElement.class, () -> {
            petriNetwork.addPlace("P1");
            petriNetwork.addPlace("P1");
        });
    }

    @Test
    @Order(4)
    public void testGetNonExistingPlaceThrowsException() {
        assertThrows(ElementNameNotExists.class, () -> petriNetwork.getPlace("P3"));
    }

    @Test
    @Order(5)
    public void testRemovePlaceSuccessfully() throws RepeatedNameElement, ElementNameNotExists, InvalidTokenNumber {
        petriNetwork.addPlace("P1");
        petriNetwork.rmPlace("P1");
        assertThrows(ElementNameNotExists.class, () -> petriNetwork.getPlace("P1"));
    }

    @Test
    @Order(6)
    public void testSetPlaceTokensSuccessfully() throws RepeatedNameElement, InvalidTokenNumber, ElementNameNotExists {
        petriNetwork.addPlace("P1");
        petriNetwork.setPlaceTokens("P1", 10);
        assertEquals(10, petriNetwork.getPlace("P1").getTokens());
    }

    @Test
    @Order(7)
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
    @Order(8)
    public void testAddDuplicateTransitionThrowsException() {
        assertThrows(RepeatedNameElement.class, () -> {
            petriNetwork.addTransition("T1");
            petriNetwork.addTransition("T1");
        });
    }

    @Test
    @Order(9)
    public void testGetNonExistingTransitionThrowsException() {
        assertThrows(ElementNameNotExists.class, () -> petriNetwork.getTransition("T3"));
    }

    @Test
    @Order(10)
    public void testAddAndRemoveArcSuccessfully() throws RepeatedNameElement, ElementNameNotExists, RepeatedArc, InvalidWeightNumber, InvalidTokenNumber {
        petriNetwork.addPlace("P1");
        petriNetwork.addTransition("T1");
        petriNetwork.addArc("A1", "T1", "P1", "in", 1);
        assertEquals("A1", petriNetwork.getArc("A1").getLabel());
        
        petriNetwork.rmArc("A1");
        assertThrows(ElementNameNotExists.class, () -> petriNetwork.getArc("A1"));
    }

    @Test
    @Order(11)
    public void testAddDuplicateArcThrowsException() {
        assertThrows(RepeatedNameElement.class, () -> {
            petriNetwork.addPlace("P1");
            petriNetwork.addTransition("T1");
            petriNetwork.addArc("A1", "T1", "P1", "in", 1);
            petriNetwork.addArc("A1", "T1", "P1", "in", 1);
        });
    }

    @Test
    @Order(12)
    public void testSetArcWeightSuccessfully() throws RepeatedNameElement, ElementNameNotExists, InvalidWeightNumber, RepeatedArc, InvalidTokenNumber {
        petriNetwork.addPlace("P1");
        petriNetwork.addTransition("T1");
        petriNetwork.addArc("A1", "T1", "P1", "in", 1);
        petriNetwork.setArcWeight("A1", 5);
        assertEquals(5, petriNetwork.getArc("A1").getWeight());
    }

    @Test
    @Order(13)
    public void testRenamePlaceSuccessfully() throws RepeatedNameElement, ElementNameNotExists, InvalidTokenNumber {
        petriNetwork.addPlace("P1");
        petriNetwork.renamePlace("P1", "P2");
        assertThrows(ElementNameNotExists.class, () -> petriNetwork.getPlace("P1"));
        assertEquals("P2", petriNetwork.getPlace("P2").getLabel());
    }

    @Test
    @Order(14)
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
    @Order(15)
    public void testFireableTransitionsWhenNoTransitionsAreFireable() {
        assertTrue(petriNetwork.fireableTransitions().isEmpty());
    }
    
    @Test
    @Order(16)
    public void testRenameArcToExistingNameThrowsException() throws RepeatedNameElement, ElementNameNotExists, InvalidTokenNumber, InvalidWeightNumber, RepeatedArc {
        petriNetwork.addPlace("P1");
        petriNetwork.addTransition("T1");
        petriNetwork.addArc("A1", "T1", "P1", "in", 1);
        petriNetwork.addArc("A2", "T1", "P1", "out", 1);
        assertThrows(RepeatedNameElement.class, () -> petriNetwork.renameArc("A1", "A2"));
    }

    @Test
    @Order(17)
    public void testRenameTransitionToExistingNameThrowsException() throws RepeatedNameElement, ElementNameNotExists {
        petriNetwork.addTransition("T1");
        petriNetwork.addTransition("T2");
        assertThrows(RepeatedNameElement.class, () -> petriNetwork.renameTransition("T1", "T2"));
    }

    @Test
    @Order(18)
    public void testRenamePlaceToExistingNameThrowsException() throws RepeatedNameElement, ElementNameNotExists, InvalidTokenNumber {
        petriNetwork.addPlace("P1");
        petriNetwork.addPlace("P2");
        assertThrows(RepeatedNameElement.class, () -> petriNetwork.renamePlace("P1", "P2"));
    }

    @Test
    @Order(19)
    public void testCreateOutZeroArcAndVerifyInOutArcsList() throws RepeatedNameElement, ElementNameNotExists, InvalidWeightNumber, InvalidTokenNumber, RepeatedArc {
        petriNetwork.addPlace("P1", 5);
        petriNetwork.addTransition("T1");
        petriNetwork.addArc("A1", "T1", "P1", "outzero");
        
        assertTrue(petriNetwork.getTransition("T1").getOutArcs().stream().anyMatch(arc -> arc instanceof OutZeroArc));
        assertEquals(1, petriNetwork.getTransition("T1").getOutArcs().size());
    }

    @Test
    @Order(20)
    public void testCreateOutBouncerArcAndVerifyInOutArcsList() throws RepeatedNameElement, ElementNameNotExists, InvalidWeightNumber, InvalidTokenNumber, RepeatedArc {
        petriNetwork.addPlace("P1", 3);
        petriNetwork.addTransition("T1");
        petriNetwork.addArc("A2", "T1", "P1", "outbouncer");
        
        assertTrue(petriNetwork.getTransition("T1").getOutArcs().stream().anyMatch(arc -> arc instanceof OutBouncerArc));
        assertEquals(1, petriNetwork.getTransition("T1").getOutArcs().size());
    }

    @Test
    @Order(21)
    public void testCreateInvalidArcTypeThrowsException() throws RepeatedNameElement, ElementNameNotExists, InvalidTokenNumber, InvalidWeightNumber {
        petriNetwork.addPlace("P1", 3);
        petriNetwork.addTransition("T1");
        
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            petriNetwork.addArc("A3", "T1", "P1", "inBouncer");
        });
        assertEquals("No valid inBouncer arc type", thrown.getMessage());
    }
    
    @Test
    @Order(22)
    public void testGetTransitionsIsNotEmpty() throws RepeatedNameElement {
    	petriNetwork.addTransition("T1");
    	assertTrue(!petriNetwork.getTransitions().isEmpty());
    }
    
    @Test
    @Order(23)
    public void testGetArcsIsNotEmpty() throws RepeatedNameElement, InvalidTokenNumber, InvalidWeightNumber, RepeatedArc, ElementNameNotExists {
    	petriNetwork.addPlace("P1", 3);
        petriNetwork.addTransition("T1");
        petriNetwork.addArc("A2", "T1", "P1", "outbouncer");
    	assertTrue(!petriNetwork.getArcs().isEmpty());
    }

}
