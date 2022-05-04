package yorkpirates.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import yorkpirates.GdxTestRunner;

//DONE
@RunWith(GdxTestRunner.class)
public class PlayerTest {

   private Player p;
   private GameScreen gs;

   @Before
   public void init() {
       p = new Player(null, 0, 0, 100, 100, "", new Label("", new LabelStyle(new BitmapFont(), null)));
       p.setMaxHealth(100);
       gs = new GameScreen();
   }

   @Test
   public void general() {
       assertNotNull(p);
   }

   @Test
    public void moveTest() {
        HUD.speedLbl = new Label("0mph", new LabelStyle(new BitmapFont(), null));
        p.move(100, 100, 1f);
        assertEquals(new Vector2(100, 100), p.getPosition());
    }

    @Test
    public void updateTest() {
        HUD.speedLbl = new Label("0mph", new LabelStyle(new BitmapFont(), null));
        HUD.powerLbl = new Label("0mph", new LabelStyle(new BitmapFont(), null));
        YorkPirates yp = new YorkPirates();
        yp.camera = new OrthographicCamera();
        gs.setMain(yp);
        p.update(gs, 1f);

        assertEquals("no power up", HUD.powerLbl.getText().toString());
        assertEquals("0mph", HUD.speedLbl.getText().toString());
    }

    @Test
    public void weatherTest() {
        Weather w1 = new Weather(0, 0, 100, 100, WeatherType.RAIN);
        Weather w2 = new Weather(1000, 1000, 0, 0, WeatherType.MORTAR);

        gs.weathers = new ArrayList<>();
        gs.weathers.add(w1);
        gs.weathers.add(w2);

        p.checkForWeather(gs);
        assertEquals(WeatherType.RAIN, p.getCurrentWeatherType());
    }

    @Test
    public void damageTest() {
        p.takeDamage(gs, 50, "ENEMY");
        assertEquals(50, p.currentHealth, 0.1f);
    }
}
