package yorkpirates.game;

import static org.junit.Assert.assertNotNull;

import com.badlogic.gdx.graphics.Color;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import yorkpirates.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class RectangleColourTest {
   
    private RectangleColour rc;

    @Before
    public void init() {
        rc = new RectangleColour(0, 0, 250, 250, Color.RED);
    }

    @Test
    public void general() {
        assertNotNull(rc);
    }
}
