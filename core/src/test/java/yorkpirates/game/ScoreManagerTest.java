package yorkpirates.game;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ScoreManagerTest {
    
    private ScoreManager sm;

    @Before
    public void init() {
        sm = new ScoreManager(0);
    }

    @Test
    public void addTest() {
        sm.Add(20);
        assertEquals(sm.Get(), 20);
    }

    @Test
    public void subTest() {
        sm.Add(10);
        assertEquals(sm.Sub(5), true);
        assertEquals(sm.Get(), 5);
    }

    @Test
    public void stringTest() {
        sm.Add(10);
        assertEquals(sm.GetString(), "10");
    }
}
