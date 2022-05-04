package yorkpirates.game;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import yorkpirates.GdxTestRunner;

@RunWith(GdxTestRunner.class)
public class WeatherTest {
    
    private Weather w1, w2;

    @Before
    public void init() {
        w1 = new Weather(0, 0, 100, 100, WeatherType.RAIN);
        w2 = new Weather(1000, 1000, 0, 0, WeatherType.MORTAR);
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
}
