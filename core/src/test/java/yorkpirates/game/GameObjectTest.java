package yorkpirates.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import yorkpirates.GdxTestRunner;

//DONE
@RunWith(GdxTestRunner.class)
public class GameObjectTest {

    private GameObject obj;
    private Rectangle r;

    @Before
    public void init() {
        obj = new GameObject(null, 0, 0, 100,100, "");
        obj.setMaxHealth(100);
        r = new Rectangle(0, 0, 250, 250);
    }

    @Test
    public void moveTest() {
        Vector2 result = new Vector2(10, 10);
        obj.move(result.x, result.y, 1f);
        assertEquals(result, obj.getPosition());
    }

    @Test
    public void updateTest() {
        int result = obj.update(null, 1f);
        assertEquals(result, 0);
    }

    @Test
    public void damageTest() {
        obj.takeDamage(null, 10, "");
        assertEquals(obj.currentHealth, 90, 0);
    }

    @Test
    public void updateHitboxTest() {
        obj.updateHitboxPos();
        assertTrue(true);
    }

    @Test
    public void overlapTest() {
        assertTrue(obj.overlaps(r));
    }

    @Test
    public void inProcessTest() {
        Vector2 v = new Vector2(500, 500);
        assertTrue(obj.inProcess(v, 1000));
    }
}