package yorkpirates.game;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import yorkpirates.GdxTestRunner;

//DONE
@RunWith(GdxTestRunner.class)
public class RainTest {

    private Rain r;

    @Before
    public void init() {
        r = new Rain(0, 0, 100, 100, null, 1f,400);
    }

    @Test
    public void actTest() {
        r.act(1f);
        assertEquals(400 - r.getDropSpeed(), r.getY(), 0.1f);
    }
}
