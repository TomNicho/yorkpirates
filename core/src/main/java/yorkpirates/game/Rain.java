package yorkpirates.game;

import com.badlogic.gdx.graphics.Texture;


public class Rain extends Rectangle {
    private int dropSpeed, wHeight; 

    public Rain(float x, float y, float width, float height, Texture texture, float alpha, int wHeight) {
        super(x, y, width, height, texture,alpha);
        this.dropSpeed = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1);
        this.wHeight = wHeight;
    } 

    @Override
    public void act(float delta) {
        if(getY() <= (wHeight/2)-200){
            setPosition(getX(),(wHeight/2)+200);
        }
        setPosition(getX(), getY() - dropSpeed);
    }

    public int getDropSpeed() {
        return dropSpeed;
    }
}