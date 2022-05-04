package yorkpirates.game;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoatTest {

    private Boat b;

    @Before
    public void init() {
        b = new Boat(null, 0, 0, 250, 250, 50, "ENEMY");
    }

    @Test
    public void moveTest() {
        b.move(100, 100, 1f);
        assertEquals(45.0, b.rotation, 0.1f);
        assertEquals(0.707, b.x, 0.1f);
        assertEquals(0.707, b.y, 0.1f);
    }
}
