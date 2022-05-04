package yorkpirates.game;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import yorkpirates.GdxTestRunner;

//DONE
@RunWith(GdxTestRunner.class)
public class CollegeTest {

    private Player p;
    private College c;
    private GameScreen gs;

   @Before
   public void init() {
       p = new Player(null, 50, 50, 100, 100, "Player", null);
       c = new College(new Texture("alcuin.png"), 0, 0, 1f, 1000, 100, "College", "Team", p, null, null, null);
       gs = new GameScreen();
       gs.player = p;
   }

   @Test
   public void updateTest() {
       gs.projectiles = new HashSet<>();
       assertEquals(0,c.update(gs, 1f));
       assertEquals(1, gs.projectiles.size());
   }

   @Test
   public void damageTest() {
       c.takeDamage(gs, 50, "");
       assertEquals(950, c.currentHealth, 0.1f);
   }

   @Test
   public void deathTest() {
       gs.shops = new Array<>();
       gs.loot = new ScoreManager();
       gs.points = new ScoreManager();
       gs.weathers = new ArrayList<>();

       c.takeDamage(gs, 2000, "");

       assertEquals(c.team, GameScreen.playerTeam);
       assertEquals(1, College.capturedCount);
       assertEquals(50, gs.points.Get());
       assertEquals(50, gs.loot.Get());
   }

   @Test
   public void addBoatTest() {
         c.addBoat(100, 100, 100);
         assertEquals(1, c.boats.size);
         assertEquals(new Vector2(100, 100), c.boats.get(0).getPosition());
   }
}
