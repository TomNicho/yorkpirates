package yorkpirates.game;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import yorkpirates.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class WeatherTest {
    
    private Weather w1, w2;
    private Player p;
    private GameScreen gs;

    @Before
    public void init() {
        w1 = new Weather(0, 0, 100, 100, WeatherType.RAIN);
        w2 = new Weather(1000, 1000, 0, 0, WeatherType.MORTAR);
        p = new Player(null, 0, 0, 100, 100, "team", new Label("", new LabelStyle(new BitmapFont(), null)));
        gs = new GameScreen();
    }

    @Test
    public void stringTest() {
        assertEquals(Weather.getWeatherLabelText(WeatherType.MORTAR), "A MORTAR has been spotted...");
    }

    @Test
    public void whichTest() {
        ArrayList<Weather> weathers = new ArrayList<>();
        weathers.add(w1);
        weathers.add(w2);

        WeatherType wt = Weather.WhichWeather(0, 0, weathers);
        assertEquals(wt, WeatherType.RAIN); 
    }

    @Test
    public void disadvantageTest() {
        Weather.DisadvantagePlayer(gs, p, WeatherType.SNOW, new ArrayList<>());
        assertEquals(0.5f, p.projectileShootCooldown, 0.1f);
        assertEquals(60f, p.SPEED, 0.1f);
    }

    @Test
    public void resetDisTest() {
        Weather.DisadvantagePlayer(gs, p, WeatherType.SNOW, new ArrayList<>());
        Weather.ResetPlayerDisadvantage(gs, p);
        assertEquals(0.1f, p.projectileShootCooldown, 0.1f);
        assertEquals(70f, p.SPEED, 0.1f);
    }
}
