package yorkpirates.game;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import yorkpirates.GdxTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import com.badlogic.gdx.math.Vector2;

//DONE
@RunWith(GdxTestRunner.class)
public class BoatTest {

    private Boat b;
    private GameScreen gs;

    @Before
    public void init() {
        b = new Boat(null, 0, 0, 250, 250, 50, "ENEMY");
        gs = new GameScreen();
    }

    @Test
    public void moveTest() {
        b.move(100, 100, 1f);
        assertEquals(45.0, b.rotation, 0.1f);
        assertEquals(14.14, b.x, 0.1f);
        assertEquals(14.14, b.y, 0.1f);
    }

    @Test
    public void damageTest() {
        assertFalse(b.takeDamage(gs, 20f));
        assertEquals(b.currentHealth, 30, 0.1f);
    }

    @Test
    public void deathTest() {
        gs.loot = new ScoreManager();
        gs.points = new ScoreManager();
        assertTrue(b.takeDamage(gs, 1000f));
        assertEquals(gs.loot.Get(), 5);
        assertEquals(gs.points.Get(), 10);
    }

    @Test
    public void shotTest() {
        gs.projectiles = new HashSet<>();
        gs.player = new Player(null, 0, 0, 100, 100, "", null);
        b.fire(gs);
        assertEquals(1, gs.projectiles.size());
    }

    @Test
    public void collisionTest() {
        gs.colleges = new HashSet<>();
        b.check_collision(gs, 10, 10, 1f);
        assertEquals(new Vector2(0, 0), b.getPosition());
    }
}
