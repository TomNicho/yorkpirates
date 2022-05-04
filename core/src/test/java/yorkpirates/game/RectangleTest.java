package yorkpirates.game;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import yorkpirates.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class RectangleTest {

    private Rectangle r;

    @Before
    public void init() {
        r = new Rectangle(0, 0, 250, 250, null, 1f);
    }

    @Test
    public void general() {
        assertNotNull(r);
    }
}
