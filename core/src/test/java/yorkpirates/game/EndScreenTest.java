package yorkpirates.game;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import yorkpirates.GdxTestRunner;

//DONE
@RunWith(GdxTestRunner.class)
public class EndScreenTest {

    private YorkPirates yp;
    private GameScreen gs;
    private EndScreen es;

    @Before
    public void init() {
        yp = new YorkPirates();
        gs = new GameScreen();
        gs.points = new ScoreManager();
        gs.loot = new ScoreManager();
        es = new EndScreen(yp, gs, true);
    }

    @Test
    public void general() {
        assertNotNull(es);
    }

    @Test
    public void updateTest() {
        es.update();
        assertNotNull(es);
    }
}
