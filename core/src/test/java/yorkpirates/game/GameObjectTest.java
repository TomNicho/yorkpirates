package yorkpirates.game;

import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import org.junit.Test;

public class GameObjectTest {

    @Test
    public void init() {
        GameObject obj = new GameObject(null, 0, 0, 100,100, "");
        moveTest(obj);
        assertTrue(true);
    }

    private void moveTest(GameObject obj) {
        Vector2 result = new Vector2(100, 100);
        obj.move(result.x, result.y);
        assertTrue(result.equals(obj.getPosition()));
    }
}