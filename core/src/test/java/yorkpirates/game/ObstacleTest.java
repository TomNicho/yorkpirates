package yorkpirates.game;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ObstacleTest {

    private Obstacle o;

    @Before
    public void init() {
        o = new Obstacle(null, 0, 0, 100, 100, "", 50);
    }

    @Test
    public void general() {
        assertNotNull(o);
    }
}
