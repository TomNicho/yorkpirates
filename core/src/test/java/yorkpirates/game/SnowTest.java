package yorkpirates.game;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class SnowTest {
    
    private Snow s;

    @Before
    public void init() {
        s = new Snow(0, 0, 100, 100, null, 1f);
    }

    @Test
    public void actTest() {
        s.act(1f);
        assertEquals(s.getWidth(), 105f, 0.1f);
        assertEquals(s.getHeight(), 105f, 0.1f);
    }
}
