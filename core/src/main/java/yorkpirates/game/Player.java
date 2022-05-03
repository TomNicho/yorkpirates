package yorkpirates.game;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class Player extends GameObject {

    // Player constants
    public float SPEED = 70f; // Player movement speed.
    public float DAMAGE = 20f;
    public float ARMOUR = 0;
    public float projectileShootCooldown = 0.1f;
  
    private static final int POINT_FREQUENCY = 1000; // How often the player gains points by moving.
    private static final float CAMERA_SLACK = 0.1f; // What percentage of the screen the player can move in before the camera follows.
    
  
    private static final int HEALTH = 200;

    // Movement calculation values
    private int previousDirectionX;
    private int previousDirectionY;
    private float distance;
    private long lastMovementScore;

    private HealthBar playerHealth;
    private float splashTime;
    private long timeLastHit;
    private boolean doBloodSplash = false;

    //weather
    public Label weatherLabel;
    private WeatherType currentWeatherType= WeatherType.NONE;

    //powerups
    public PowerType activePower;
    private long LastPowered = 0;

    /**
     * Generates a generic object within the game with animated frame(s) and a hit-box.
     * @param frames    The animation frames, or a single sprite.
     * @param fps       The number of frames to be displayed per second.
     * @param x         The x coordinate within the map to initialise the object at.
     * @param y         The y coordinate within the map to initialise the object at.
     * @param width     The size of the object in the x-axis.
     * @param height    The size of the object in the y-axis.
     * @param team      The team the player is on.
     */
    public Player(Texture texture, float x, float y, float width, float height, String team, Label weatherLabel){
        super(texture, x, y, width, height, team);
        lastMovementScore = 0;
        splashTime = 0;
        this.weatherLabel = weatherLabel;

        // Generate health
        setMaxHealth(HEALTH);
        playerHealth = new HealthBar(this, new Texture("allyHealthBar.png"));
    }

    /**
     * Called once per frame. Used to perform calculations such as player/camera movement.
     * @param screen    The main game screen.
     * @param camera    The player camera.
     */
    public void update(GameScreen screen, OrthographicCamera camera){
        Vector2 oldPos = new Vector2(x,y); // Stored for next-frame calculations

        // Get input movement
        int horizontal = (Gdx.input.isKeyPressed(Input.Keys.D) ? 1 : 0)- (Gdx.input.isKeyPressed(Input.Keys.A)  ? 1 : 0);
        int vertical = (Gdx.input.isKeyPressed(Input.Keys.W)  ? 1 : 0) - (Gdx.input.isKeyPressed(Input.Keys.S) ? 1 : 0);

        // Calculate collision && movement
        if (horizontal != 0 || vertical != 0){

            //adjusts for speed powerup
            float movespeed = SPEED;
            if (this.activePower == PowerType.SPEED){
                movespeed = movespeed + 50;
            }
            if (this.activePower == PowerType.DAMAGE){
                movespeed = movespeed + 150;
            }

            move(movespeed *horizontal, movespeed *vertical);
            previousDirectionX = horizontal;
            previousDirectionY = vertical;
            if (safeMove(screen.getMain().edges)) {
                if (TimeUtils.timeSinceMillis(lastMovementScore) > POINT_FREQUENCY) {
                    lastMovementScore = TimeUtils.millis();
                    screen.points.Add(1);
                }
            } else {    // Collision
                Vector2 newPos = new Vector2(x, y);
                x = oldPos.x;
                if (!safeMove(screen.getMain().edges)) {
                    x = newPos.x;
                    y = oldPos.y;
                    if (!safeMove(screen.getMain().edges)) {
                        x = oldPos.x;
                    }
                }
            }
        }else{
            HUD.speedLbl.setText("0mph");
        }
        updateHitboxPos();
        // Track distance travelled
        distance += Math.pow((Math.pow((x - oldPos.x),2f) + Math.pow((y - oldPos.y),2f)),0.5f)/10f;

        // Camera Calculations
        ProcessCamera(screen, camera);

        // Blood splash calculations
        if(doBloodSplash){
            if(splashTime > 1){
                doBloodSplash = false;
                splashTime = 0;
            }else{
                splashTime += 1;
            }
        }

        if (TimeUtils.timeSinceMillis(timeLastHit) > 10000){
            currentHealth += 0.03;
            if(currentHealth > maxHealth) currentHealth = maxHealth;
            playerHealth.resize(currentHealth);
        }

        //healing power up
        if (activePower == PowerType.HEAL){
            currentHealth += 0.2;
            if(currentHealth > maxHealth) currentHealth = maxHealth;
            playerHealth.resize(currentHealth);
        }

        if (TimeUtils.timeSinceMillis(LastPowered) > 10000){
            this.activePower = PowerType.NOTHING;
            HUD.powerLbl.setText("no power up");
        }

        //collide with obstacle
        for(Iterator<Obstacle> it = screen.obstacles.iterator(); it.hasNext();){
            Obstacle o = it.next();
            if(overlaps(o.hitBox)){
                
                if(o instanceof Barrel){
                    Barrel b = (Barrel)o;
                    if(b.type == BarrelType.BROWN){
                        takeDamage(screen, b.damage, "ENEMY");
                    }else{
                        int randMoney = (int)Math.floor(Math.random() * (6 - 2 + 1) + 2);
                        screen.loot.Add(randMoney);
                    }
                    it.remove();
                }else{ 
                    
                    takeDamage(screen, o.damage, "ENEMY");
                    move(1000 * -previousDirectionX, 1000 * -previousDirectionY);
                    
                }

                if(o instanceof PowerUp){
                    PowerUp p = (PowerUp)o;
                    if(p.type == PowerType.SPEED){
                        this.LastPowered = TimeUtils.millis();
                        this.activePower = PowerType.SPEED;
                        HUD.powerLbl.setText("speed boost");
                        it.remove();
                    }
                    if(p.type == PowerType.FIRERATE){
                        this.LastPowered = TimeUtils.millis();
                        this.activePower = PowerType.FIRERATE;
                        HUD.powerLbl.setText("automatic");
                        it.remove();
                    }
                    if(p.type == PowerType.DAMAGE){
                        this.LastPowered = TimeUtils.millis();
                        this.activePower = PowerType.DAMAGE;
                        HUD.powerLbl.setText("BIG SPEED");
                        it.remove();                        
                    }
                    if(p.type == PowerType.IMMUNE){
                        this.LastPowered = TimeUtils.millis();
                        this.activePower = PowerType.IMMUNE;
                        HUD.powerLbl.setText("immunity");
                        it.remove();                        
                    }
                    if(p.type == PowerType.HEAL){
                        this.LastPowered = TimeUtils.millis();
                        this.activePower = PowerType.HEAL;
                        HUD.powerLbl.setText("healing");
                        it.remove();                        
                    }
                }
            }
        }
        
    }

    /**
     *  Calculate if the current player position is safe to be in.
     * @param edges A 2d array containing safe/unsafe positions to be in.
     * @return      If the current position is safe.
     */
    private Boolean safeMove(Array<Array<Boolean>> edges){
        return (
                        edges.get((int)((y+height/2)/16)).get((int)((x+width/2)/16)) &&
                        edges.get((int)((y+height/2)/16)).get((int)((x-width/2)/16)) &&
                        edges.get((int)((y-height/2)/16)).get((int)((x+width/2)/16)) &&
                        edges.get((int)((y-height/2)/16)).get((int)((x-width/2)/16))
        );
    }

    /**
     * Moves the player within the x and y-axis of the game world.
     * @param x     The amount to move the object within the x-axis.
     * @param y     The amount to move the object within the y-axis.
     */
    @Override
    public void move(float x, float y){
        this.x += x * Gdx.graphics.getDeltaTime();
        this.y += y * Gdx.graphics.getDeltaTime();
        float speedtext = SPEED;
        if (activePower == PowerType.SPEED){
            speedtext += 50;
        }
        HUD.speedLbl.setText(speedtext + "mph");
        playerHealth.move(this.x, this.y + height/2 + 2f); // Healthbar moves with player
    }
    
    public void checkForWeather(GameScreen gameScreen){
   
        WeatherType type = Weather.WhichWeather((int)this.x, (int)this.y, GameScreen.weathers);
        // HUD.UpdateWeatherLabel(this.x + " | " + this.y,weatherLabel);
        //only check if its different weather
        if(currentWeatherType != type){
            Weather.ResetPlayerDisadvantage(this);
            if(type == WeatherType.NONE){

                HUD.UpdateWeatherLabel("",weatherLabel);
            } else {
                //update weather label to show user which weather event they're in 
                HUD.UpdateWeatherLabel(Weather.getWeatherLabelText(type),weatherLabel);
                //need to disadvantage the player in some way
                if(type == WeatherType.RAIN){
                    Weather.DisadvantagePlayer(gameScreen,this,type,gameScreen.rains);
                }else if (type == WeatherType.SNOW){
                    Weather.DisadvantagePlayer(gameScreen,this,type,gameScreen.snows);
                }else if (type == WeatherType.STORM){
                    Weather.DisadvantagePlayer(gameScreen,this,type,gameScreen.storms);
                }else if(type == WeatherType.MORTAR){
                    Weather.DisadvantagePlayer(gameScreen,this,type,gameScreen.mortars);
                }
            }
        }
        currentWeatherType = type;
    }
    
    /**
     * Called when a projectile hits the college.
     * @param screen            The main game screen.
     * @param damage            The damage dealt by the projectile.
     * @param projectileTeam    The team of the projectile.
     */
    @Override
    public void takeDamage(GameScreen screen, float damage, String projectileTeam){
        timeLastHit = TimeUtils.millis();
        //immunity power up
        if (activePower != PowerType.IMMUNE){
        currentHealth -= damage + ARMOUR;
        doBloodSplash = true;
        }

        // Health-bar reduction
        playerHealth.resize(currentHealth);
        if(currentHealth <= 0){
            //this is to run it on the main thread as to not crash the whole thing
            Gdx.app.postRunnable(new Runnable() {
                public void run () {
                    screen.gameEnd(false);
                }
            });
           
        }
    }

    /**
     * Called after update(), calculates whether the camera should follow the player and passes it to the game screen.
     * @param screen    The main game screen.
     * @param camera    The player camera.
     */
    private void ProcessCamera(GameScreen screen, OrthographicCamera camera) {
        Vector2 camDiff = new Vector2(x - camera.position.x, y - camera.position.y);
        screen.toggleFollowPlayer(Math.abs(camDiff.x) > camera.viewportWidth / 2 * CAMERA_SLACK || Math.abs(camDiff.y) > camera.viewportWidth / 2 * CAMERA_SLACK);
    }

    /**
     * Called when drawing the player.
     * @param batch         The batch to draw the player within.
     * @param elapsedTime   The current time the game has been running for.
     */
    @Override
    public void draw(SpriteBatch batch, float elapsedTime){

        // Generates the sprite
        if(doBloodSplash){
            // batch.setShader(shader); // Set our grey-out shader to the batch
        } float rotation = (float) Math.toDegrees(Math.atan2(previousDirectionY, previousDirectionX));

        // Draws sprite and health-bar
        batch.draw(texture, x - width/2, y - height/2, width/2, height/2, width, height, 1f, 1f, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
        batch.setShader(null);
    }

    @Override
    public void dispose() {
        playerHealth.dispose();
        super.dispose();
    }

    public void drawHealthBar(SpriteBatch batch){
        if(!(playerHealth == null)) playerHealth.draw(batch, 0);
    }

    public float getDistance() {
        return distance;
    }

    public String getArmourString(){
        return Float.toString(ARMOUR);
    }
    
    public String getDamageString(){
        return Float.toString(DAMAGE);
    }

    public String getSpeedString(){
        return Float.toString(SPEED);
    }
}
