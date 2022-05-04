package yorkpirates.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Shop extends GameObject {
    public String name;
    public boolean activated;
    /**
     * Generates a shop linked to a college
     * @param shopImage The texture of the shop
     * @param x         The x coordinate of the shop   
     * @param y         The y coordinate of the shop
     * @param width     The width of the shop texture
     * @param height    The height of the shop texture
     * @param scale     The modifier used when drawing the sprite
     * @param activated If the shop is able to be used by the player
     * @param name      The college assoicated with the shop
     */
    public Shop(Texture shopImage, float x, float y, float width, float height, float scale, Boolean activated, String name){
        super(shopImage, x, y, width, height, "neutral");
        this.activated = activated;
        this.name = name;
    }
    /**
     * Enables the shop
     */
    public void activate(){
        this.activated = true;
    }
    /**
     * Upgrades the players stats based on the upgrade
     * @param player            The player to be upgraded
     * @param loot              The players current gold
     * @param upgradeSelected   The upgrade to be applied
     */
    public void upgrade(Player player, ScoreManager loot, String upgradeSelected){
        if (upgradeSelected == "damage"){
            if(loot.Sub(10)){
                player.DAMAGE += 5f;
            }
        }
        if (upgradeSelected == "speed"){
            // upgrade speed
            if(loot.Sub(10)){
                player.SPEED += 5f;
            }
        }
        if (upgradeSelected == "armour"){
            if (player.ARMOUR < 17){
                if(loot.Sub(10)){
                    player.ARMOUR += 3;
                }
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch, float elapsedTime){
        super.draw(batch, elapsedTime);
    }
}