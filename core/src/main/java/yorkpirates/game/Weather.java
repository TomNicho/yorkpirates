package yorkpirates.game;

import java.util.ArrayList;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;


public class Weather {

    public int xpos, ypos, xoff, yoff;
    public WeatherType weatherType;

    private static int disSpeed = 0;

    public Weather(int xpos, int ypos, int xoff, int yoff, WeatherType weatherType){
        this.xpos = xpos;
        this.ypos = ypos;
        this.xoff = xoff;
        this.yoff = yoff;
        this.weatherType = weatherType;
    
    }
    /**
     * Displays to the user what weather effect is being encountered
     * @param weatherType   The type of weather effect
     * @return              A string describing the weather
     */
    public static String getWeatherLabelText(WeatherType weatherType){
        String ret = "";
        // Most weather is displayed by their textures instead of text after updates
        if(weatherType == WeatherType.RAIN){
            ret = "";
        }else if(weatherType == WeatherType.SNOW){
            ret = "";
        }else if(weatherType == WeatherType.STORM){
            ret = "";
        }else if(weatherType == WeatherType.MORTAR){
            ret = "A MORTAR has been spotted...";
        }else if(weatherType == WeatherType.NONE){
            ret = "";
        }
        return ret;
    }

    public static WeatherType WhichWeather(int x, int y, ArrayList<Weather> weathers){
        for(int i =0; i < weathers.size();i++){
            if(x >= weathers.get(i).xpos -weathers.get(i).xoff  && x <=weathers.get(i).xpos + weathers.get(i).xoff && y >= weathers.get(i).ypos - weathers.get(i).yoff && y <= weathers.get(i).ypos + weathers.get(i).yoff){
                return weathers.get(i).weatherType;
            }
        }
        return WeatherType.NONE;
    }
    /**
     * Changes the players attributes to debuff them in different weather conditions
     * @param gameScreen    The main game screen
     * @param player        The player to be effected
     * @param weatherType   The type of weather
     * @param disList       The list of disadvantages
     */
    public static void DisadvantagePlayer(GameScreen gameScreen, Player player, WeatherType weatherType,ArrayList<Actor> disList) {
        //set players attributes so they have a disadvantage
        //we also need to draw some rectangles to represent rain/snow so their
        //visibility is impeded.
        gameScreen.mortarable = false;
        
        if(weatherType == WeatherType.RAIN){
            disSpeed = 5;
        }else if (weatherType == WeatherType.SNOW){
            disSpeed = 10;
            player.projectileShootCooldown = 0.5f;
            
        }else if(weatherType == WeatherType.STORM){
            disSpeed = 20;
            player.projectileShootCooldown = 0.8f;
        }else if (weatherType == WeatherType.MORTAR){
            disSpeed = 30;
            //Long timer for player damage via mortar
            gameScreen.mortarable = true;
        
            player.projectileShootCooldown = 0.9f;
        }
        player.SPEED-=disSpeed;

        if (HUD.stage == null) return;

        for(Actor r : disList){
            HUD.stage.addActor(r);
        }
    }
    /**
     * Resets the players stats after leaving the weather
     * @param gameScreen    The main game screen
     * @param player        The player affected
     */
    public static void ResetPlayerDisadvantage(GameScreen gameScreen,Player player){

        //remove timer
        if(gameScreen.mortarable == true){
            gameScreen.mortarable = false;
        }
       
        //disadvatange player attributes
        player.SPEED+=disSpeed;
        player.projectileShootCooldown = 0.1f;

        if (HUD.stage == null) return;

        Array<Actor> actors = HUD.stage.getActors();
        for(int i = actors.size-1; i> 0;i--){
            Actor a = actors.get(i);
            
            if(a instanceof Rectangle || a instanceof RectangleColour){
                a.remove();
            }
        }
        disSpeed = 0;
    }
}

