package yorkpirates.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.math.Rectangle;

public class Boat extends GameObject {
    public static int PROCESS_RANGE = 150;
    public static int STOP_RANGE = 50;
    public static int FIRE_RATE = 750;

    public static float ROTATION_ANCHOR = 2f;
    public static float MOVE_SPEED = 1f;

    public float rotation;
    private long lastShotFired;

    public Boat(Texture texture, float x, float y, float width, float height, float rotation, String team) {
        super(texture, x, y, width, height, team);
        this.rotation = rotation;
        setMaxHealth(50);
    }

    public void check_collision(GameScreen screen, float horizontal, float vertical){
        Vector2 oldPos = new Vector2(x, y);
        Rectangle thisHitbox = this.hitBox;
        if(horizontal !=0 || vertical !=0){
            move(horizontal, vertical);
            for(College c : screen.colleges){
                for(int i=0; i < c.boats.size; i++){
                    if(thisHitbox != c.boats.get(i).hitBox){
                        if(!safeMove(screen.getMain().edges) || overlaps(c.boats.get(i).hitBox)){
                            this.x = oldPos.x;
                            this.y = oldPos.y;
                        }
                    }
                }
            }
            
        }
    }

    public void fire(GameScreen screen){
        if (TimeUtils.timeSinceMillis(lastShotFired) > FIRE_RATE){
            lastShotFired = TimeUtils.millis();
            screen.projectiles.add(new Projectile(new Texture("tempProjectile.png"), this, screen.getPlayer().x, screen.getPlayer().y, team));
        }
    }

    private Boolean safeMove(Array<Array<Boolean>> edges){
        return (edges.get((int)((y+height/2)/16)).get((int)((x+width/2)/16)) &&
                edges.get((int)((y+height/2)/16)).get((int)((x-width/2)/16)) &&
                edges.get((int)((y-height/2)/16)).get((int)((x+width/2)/16)) &&
                edges.get((int)((y-height/2)/16)).get((int)((x-width/2)/16))
        );
    }

    @Override
    public void move(float x, float y) {
        if (!inProcess(new Vector2(x, y), PROCESS_RANGE)) return;

        float rotation = (float) Math.toDegrees(Math.atan2(y - this.y, x - this.x));
        // if (rotation - this.rotation < ROTATION_ANCHOR) this.rotation = rotation;
        // else if (rotation > this.rotation) this.rotation += ROTATION_ANCHOR;
        // else this.rotation -= ROTATION_ANCHOR;

        this.rotation = rotation;

        if (inProcess(new Vector2(x, y), STOP_RANGE)) return;
        
        this.x += MOVE_SPEED * Math.cos(Math.toRadians(this.rotation));
        this.y += MOVE_SPEED * Math.sin(Math.toRadians(this.rotation));
    }
    
    public boolean takeDamage(GameScreen screen, float damage){
        currentHealth -= damage;
        if (currentHealth <= 0){
            screen.loot.Add(5);
            screen.points.Add(10);
            return true;
        }
        else{
            return false;
        }
    }

    public void draw(SpriteBatch batch, float elapsedTime) {
        batch.draw(texture, x, y, width / 2, height / 2, width, height, 1f, 1f, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }
}  