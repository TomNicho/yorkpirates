package yorkpirates.game;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import yorkpirates.GdxTestRunner;

//DONE
@RunWith(GdxTestRunner.class)
public class BarrelTest {
    
    private Barrel b;

    @Before
    public void init() {
        b = new Barrel(null, 0, 0, 100, 100, "neutral", 0, BarrelType.BROWN);
    }

    @Test
    public void damageTest() {
        assertEquals(40, b.damage, 0.1f);
    }
}
