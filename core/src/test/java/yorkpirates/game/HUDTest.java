package yorkpirates.game;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import yorkpirates.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class HUDTest {

    private HUD hud;
    private GameScreen gs;
    private YorkPirates yp;

    @Before
    public void init() {
        yp = new YorkPirates();
        yp.create();
        gs = new GameScreen(yp);
        hud = new HUD(gs);
    }

    @Test
    public void generalTest() {
        assertNotNull(hud);
    }
}
