package yorkpirates.game;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import yorkpirates.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class GameScreenTest {

    private GameScreen gs;
    private YorkPirates yp;

    @Before
    public void init() {
        yp = new YorkPirates();
        yp.create();
        gs = new GameScreen(yp);
    }

    @Test
    public void generalTest() {
        assertNotNull(gs);
    }

    @Test
    public void updateTest() {
        gs.update();
        assertNotNull(gs);
    }
}