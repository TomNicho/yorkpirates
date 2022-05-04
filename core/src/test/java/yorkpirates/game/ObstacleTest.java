package yorkpirates.game;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import yorkpirates.GdxTestRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(GdxTestRunner.class)
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
