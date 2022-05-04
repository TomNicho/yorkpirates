package yorkpirates.game;

import static org.junit.Assert.assertEquals;

import com.badlogic.gdx.graphics.Texture;
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
public class IndicatorTest {

    private Indicator i;
    private Player p;
    private College c;

    @Before 
    public void init() {
        p = new Player(null, 50, 50, 100, 100, "Player", null);
        c = new College(new Texture("alcuin.png"), 0, 0, 1f, 1000, 100, "College", "Team", p, null, null);
        i = new Indicator(c, p, new Texture("homeArrow.png"));
    }


    @Test
    public void stillTest() {
        i.move();
        assertEquals(i.getPosition(), new Vector2(0, 0));
    }

    @Test
    public void moveTest() {
        HUD.speedLbl = new Label("0mph", new LabelStyle(new BitmapFont(), null));
        p.move(100, 100, 1f);
        i.setVisible(false);
        i.move();
        assertEquals(new Vector2(100, 100), i.getPosition());
    }
}
