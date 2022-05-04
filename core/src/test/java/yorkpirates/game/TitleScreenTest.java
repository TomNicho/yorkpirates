package yorkpirates.game;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import yorkpirates.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class TitleScreenTest {
    
    private YorkPirates yp;
    private TitleScreen ts;

    @Before
    public void init() {
        yp = new YorkPirates();
        yp.create();
        ts = new TitleScreen(yp);
    }

    @Test
    public void generalTest() {
        assertNotNull(ts);
    }
}
