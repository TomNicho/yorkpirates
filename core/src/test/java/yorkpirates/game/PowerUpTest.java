package yorkpirates.game;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class PowerUpTest {
    
    private PowerUp p;

    @Before
    public void init() {
        p = new PowerUp(null, 0, 0, 100, 100, "PowerUp", 10, PowerType.DAMAGE);
    }

    @Test
    public void general() {
        assertNotNull(p);
    }
}
