package mapd;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import mapd.implementations.Place;
import mapd.exceptions.InvalidTokenNumber;

public class PlaceTest {

    private Place place;

    @BeforeEach
    public void setUp() {
        place = new Place();
    }

    @Test
    public void testDefaultConstructor() {
        assertEquals(0, place.getTokens());
    }

    @Test
    public void testConstructorWithValidTokens() throws InvalidTokenNumber {
        Place placeWithTokens = new Place(5);
        assertEquals(5, placeWithTokens.getTokens());
    }

    @Test
    public void testConstructorWithInvalidTokens() {
        assertThrows(InvalidTokenNumber.class, () -> {
            new Place(-1);
        });
    }

    @Test
    public void testGetTokens() {
        assertEquals(0, place.getTokens());
    }

    @Test
    public void testSetTokensValid() throws InvalidTokenNumber {
        place.setTokens(10);
        assertEquals(10, place.getTokens());
    }

    @Test
    public void testSetTokensInvalid() {
        assertThrows(InvalidTokenNumber.class, () -> {
            place.setTokens(-5);
        });
    }

    @Test
==== BASE ====
    public void testToString() {
        assertEquals("Place has 0", place.toString());
==== BASE ====
        place.setTokens(15);
        assertEquals("Place has 15 tokens", place.toString());
    }
}
