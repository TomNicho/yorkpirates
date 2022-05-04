package yorkpirates.game;

import static org.junit.Assert.assertNotNull;

import com.badlogic.gdx.graphics.OrthographicCamera;

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
        yp.camera = new OrthographicCamera();
        // yp.keyboard = new Keyboard();
        gs = new GameScreen(yp);
    }

    @Test
    public void general() {
        assertNotNull(gs);
    }

    // @Test
    // public void update() {
    //     gs.update();
    // }
}