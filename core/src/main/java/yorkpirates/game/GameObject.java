package yorkpirates.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameObject {
    public float x, y, width, height, currentHealth;
    public int maxHealth;

    public String team;
    public Texture texture;
    public Rectangle hitBox;

    // ShaderProgram shader = new ShaderProgram(Gdx.files.internal("red.vsh"), Gdx.files.internal("red.fsh"));

    /**
     * Generates a generic object within the game with animated frame(s) and a hit-box.
     * @param frames    The animation frames, or a single sprite.
     * @param fps       The number of frames to be displayed per second.
     * @param x         The x coordinate within the map to initialise the object at.
     * @param y         The y coordinate within the map to initialise the object at.
     * @param width     The size of the object in the x-axis.
     * @param height    The size of the object in the y-axis.
     * @param team      The team the object is on.
     */
    public GameObject(Texture texture, float x, float y, float width, float height, String team) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.team = team;
        this.width = width;
        this.height = height;
        setHitbox();
    }

    /**
     * Called when the image needs to be changed or set.
     * @param frames    The animation frames, or a single sprite.
     * @param fps       The number of frames to be displayed per second.
     */
    void changeImage(Texture texture) {
        this.texture.dispose();
        this.texture = texture;
    }

    /**
     * Called when the health of the object needs to be set.
     * @param mh    The health value for the object
     */
    public void setMaxHealth(int mh){
        maxHealth = mh;
        currentHealth = maxHealth;
    }

    /**
     * Called when a projectile hits the object.
     * @param screen            The main game screen.
     * @param damage            The damage dealt by the projectile.
     * @param projectileTeam    The team of the projectile.
     */
    public void takeDamage(GameScreen screen, float damage, String projectileTeam){
        currentHealth -= damage;
    }

    /**
     * Moves the object within the x and y-axis of the game world.
     * @param x     The amount to move the object within the x-axis.
     * @param y     The amount to move the object within the y-axis.
     */
    public void move(float x, float y, float delta){
        this.x += x * delta;
        this.y += y * delta;
    }

    public int update(GameScreen screen, float delta) {
        return 0;
    }

    /**
     * Sets the object's hit-box, based upon it's x, y, width and height values.
     */
    private void setHitbox(){
        hitBox = new Rectangle();
        updateHitboxPos();
        hitBox.width = width;
        hitBox.height = height;
    }

    /**
     * Updates the object's hit-box location to match the object's rendered location.
     */
    void updateHitboxPos() {
        hitBox.x = x - width/2;
        hitBox.y = y - height/2;
    }

    /**
     * Checks if this object overlaps with another.
     * @param rect  The other object to be checked against.
     * @return      True if overlapping, false otherwise.
     */
    boolean overlaps(Rectangle rect){
        updateHitboxPos();
        return hitBox.overlaps(rect);
    }

    public Vector2 getPosition(){
        return new Vector2(x, y);
    }

    /**
     * Called when drawing the object.
     * @param batch         The batch to draw the object within.
     * @param elapsedTime   The current time the game has been running for.
     */
    public void draw(SpriteBatch batch, float elapsedTime){
        batch.draw(texture, x - width/2, y - height/2, width, height);
    }

    /**
     * Called when the object needs to be disposed.
     */
    public void dispose(){
        texture.dispose();
    }

    public boolean inProcess(Vector2 pos, int radius) {
        return pos.dst(x, y) < radius;
    }
}
