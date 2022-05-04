package yorkpirates.game;

import com.badlogic.gdx.graphics.Texture;

enum BarrelType{
    GOLD,
    BROWN
}
public class Barrel extends Obstacle{

    public int damage;
    public BarrelType type;

    /**
     * Generates a barrel that deals damage on collision with the player.
     * @param texture   The texture for the object.
     * @param x         The x coordinate within the map to initialise the object at.
     * @param y         The y coordinate within the map to initialise the object at.
     * @param width     The size of the object in the x-axis.
     * @param height    The size of the object in the y-axis.
     * @param team      The team the object is on.
     * @param damage    The amount of damage done on collision with the player
     * @param type      The type of barrel
     */
    public Barrel(Texture texture, float x, float y, float width, float height, String team, int damage, BarrelType type) {
        super(texture, x, y, width, height, team, damage);
        this.type = type;
        if(type == BarrelType.BROWN){
            this.damage = 40;
        }else{
            this.damage = 0;
        }
    }
}
