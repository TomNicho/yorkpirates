package yorkpirates.game;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import yorkpirates.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class PlayerTest {

   private Player p;

   @Before
   public void init() {
       p = new Player(null, 0, 0, 100, 100, "", null);
   }

   @Test
   public void general() {
       assertNotNull(p);
   }
}
