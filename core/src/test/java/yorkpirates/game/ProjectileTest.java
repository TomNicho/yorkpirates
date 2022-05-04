package yorkpirates.game;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import yorkpirates.GdxTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;

import com.badlogic.gdx.math.Vector2;

//DONE
@RunWith(GdxTestRunner.class)
public class ProjectileTest {

    private Projectile p, e;
    private Player pl;
    private GameObject obj;
    private GameScreen gs;

    @Before
    public void init() {
        obj = new GameObject(null, 0, 0, 100, 100, "");
        pl = new Player(null, 0, 0, 100, 100, "PLAYER", null);
        p = new Projectile(null, obj, 1000, 1000, "");
        e = new Projectile(null, pl, 1000, 1000, "");
        gs = new GameScreen();
        gs.player = pl;
        gs.colleges = new HashSet<>();
    }

    @Test
    public void general() {
        assertNotNull(p);
    }

    @Test
    public void enemyTest() {
        assertEquals(1, e.update(gs, 1f));
        assertEquals(new Vector2(50, 50), e.getPosition());
    }

    @Test
    public void playerTest() {
        assertEquals(0, p.update(gs, 1f));
        assertEquals(new Vector2(50, 50), p.getPosition());
    }
}
