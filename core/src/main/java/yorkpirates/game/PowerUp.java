package yorkpirates.game;

import com.badlogic.gdx.graphics.Texture;

enum PowerType{
    NOTHING,
    SPEED,
    DAMAGE,
    FIRERATE,
    HEAL,
    IMMUNE
}
public class PowerUp extends Obstacle{
    public int damage;
    public PowerType type;
    public PowerUp(Texture texture, float x, float y, float width, float height, String team, int damage, PowerType type) {
        super(texture, x, y, width, height, team,damage);
        this.type = type;
    }
}
