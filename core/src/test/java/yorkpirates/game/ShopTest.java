package yorkpirates.game;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ShopTest {
    
    private Shop s;

    @Before
    public void init() {
        s = new Shop(null, 0, 0, 100, 100, 1f, false, "Shop");
    }

    @Test
    public void activateTest() {
        s.activate();
        assertEquals(s.activated, true);
    }

    // @Test
    // public void upgradeTest() {
    //     s.upgrade();
    //     assertEquals(s.upgradeLevel, 2);
    // }
}
