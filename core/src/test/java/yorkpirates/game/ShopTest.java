package yorkpirates.game;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import yorkpirates.GdxTestRunner;

//DONE
@RunWith(GdxTestRunner.class)
public class ShopTest {
    
    private Shop s;
    private Player p;
    private ScoreManager sm;

    @Before
    public void init() {
        s = new Shop(null, 0, 0, 100, 100, 1f, false, "Shop");
        p = new Player(null, 0, 0, 100, 100, "Player", null);
        sm = new ScoreManager(0);
    }

    @Test
    public void activateTest() {
        s.activate();
        assertEquals(s.activated, true);
    }

    @Test
    public void upgradeTest() {
        sm.Add(100);

        s.upgrade(p, sm, "armour");
        s.upgrade(p, sm, "damage");
        s.upgrade(p, sm, "speed");

        assertEquals(3, p.ARMOUR, 0.1f);
        assertEquals(25, p.DAMAGE, 0.1f);
        assertEquals(75, p.SPEED, 0.1f);
    }
}
