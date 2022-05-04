package yorkpirates.game;

import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HealthBarTest {

    private HealthBar hb;
    private GameObject obj;

    @Before
    public void init() {
        obj = new GameObject(null, 0, 0, 100, 100, "");
        obj.setMaxHealth(1000);
        hb = new HealthBar(obj, null);
    }

    @Test
    public void resizeTest() {
        hb.resize(900f);
        assertEquals(90f, hb.width, 0f);
    }

    @Test
    public void moveTest() {
        hb.move(100, 100, 1);
        assertEquals(hb.getPosition(), new Vector2(100, 100));
    }
}
