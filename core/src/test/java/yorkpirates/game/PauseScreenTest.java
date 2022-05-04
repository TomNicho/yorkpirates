package yorkpirates.game;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class PauseScreenTest {
    
    private YorkPirates yp;
    private GameScreen gs;
    private PauseScreen ps;

    @Before
    public void init() {
        yp = new YorkPirates();
        yp.create();
        gs = new GameScreen(yp);
        ps = new PauseScreen(yp, gs);
    }

    @Test
    public void generalTest() {
        assertNotNull(ps);
    }
}
