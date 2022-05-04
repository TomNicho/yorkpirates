package yorkpirates.game;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ProjectileTest {

    private Projectile p;
    private GameObject obj;

    @Before
    public void init() {
        obj = new GameObject(null, 0, 0, 100, 100, "");
        p = new Projectile(null, obj, 1000, 1000, "");
    }

    @Test
    public void general() {
        assertNotNull(p);
    }

    @Test
    public void updateTest() {

    }
}
