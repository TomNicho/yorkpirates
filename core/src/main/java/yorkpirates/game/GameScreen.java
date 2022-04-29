package yorkpirates.game;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import com.badlogic.gdx.utils.viewport.FitViewport;



import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class GameScreen extends ScreenAdapter {
    // Team name constants
    public static final String playerTeam = "PLAYER";
    public static final String enemyTeam = "ENEMY";


    //weathers and positions
    private static Weather rain =  new Weather(820, 980, 1000,100, WeatherType.RAIN);
    private static Weather rain2 =  new Weather(1770, 2300, 150,150, WeatherType.RAIN);
    private static Weather snow =  new Weather(1190, 911, 100,100, WeatherType.SNOW);
    private static Weather storm =  new Weather(1700, 678, 100,100, WeatherType.STORM);
    private static Weather storm2 =  new Weather(670, 700, 150,150, WeatherType.STORM);
    private static Weather storm3 =  new Weather(400,1000, 200,150, WeatherType.STORM);
    private static Weather james =  new Weather(1380, 1770, 200,200, WeatherType.JAMESFURY);
    public static final ArrayList<Weather> weathers = new ArrayList<Weather> (Arrays.asList(rain,rain2,snow,storm,james,storm2,storm3));

    // Score managers
    public ScoreManager points;
    public ScoreManager loot;

    // Colleges
    public Set<College> colleges;
    public Set<Projectile> projectiles;

    // Sound
    public Music music;

    // Main classes
    private final YorkPirates game;

    // Player
    private final Player player;
    private String playerName;
    private Vector3 followPos;
    private boolean followPlayer = false;

    // UI & Camera
    private final HUD gameHUD;
    private final SpriteBatch HUDBatch;
    private final OrthographicCamera HUDCam;
    private final FitViewport viewport;

    // Tilemap
    private final TiledMap tiledMap;
    private final OrthogonalTiledMapRenderer tiledMapRenderer;

    // Trackers
    private float elapsedTime = 0;
    private boolean isPaused = false;
    private boolean canFire = true;
    private float lastPause = 0;

    public static ArrayList<Actor> rains = new ArrayList<Actor>();
    public static ArrayList<Actor> snows = new ArrayList<Actor>();
    public static ArrayList<Actor> storms = new ArrayList<Actor>();
    public static ArrayList<Actor> jamesa = new ArrayList<Actor>(Arrays.asList(new RectangleColour(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new Color(255, 0, 0, 0.5f))));
    
    public static ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
    // public static ArrayList<Obstacle> icebergs = new ArrayList<Obstacle>();
    

    /**
     * Initialises the main game screen, as well as relevant entities and data.
     * @param game  Passes in the base game class for reference.
     */
    public GameScreen(YorkPirates game){
        this.game = game;
        playerName = "Player";
        // Initialise points and loot managers
        points = new ScoreManager();
        loot = new ScoreManager();

        // Initialise HUD
        HUDBatch = new SpriteBatch();
        HUDCam = new OrthographicCamera();
        HUDCam.setToOrtho(false, game.camera.viewportWidth, game.camera.viewportHeight);
        viewport = new FitViewport( Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), HUDCam);
        gameHUD =  new HUD(this);
        Label l = HUD.AddWeatherLabel("");
        //initialise sound
        music = Gdx.audio.newMusic(Gdx.files.internal("Pirate1_Theme1.ogg"));
        music.setLooping(true);
        music.setVolume(0);
        music.play();

        // Initialise sprites array to be used generating GameObjects
        Array<Texture> sprites = new Array<>();

        // Initialise player
        sprites.add(new Texture("ship1.png"), new Texture("ship2.png"), new Texture("ship3.png"));
        player = new Player(sprites, 2, 821, 489, 32, 16, playerTeam,l);
        sprites.clear();
        followPos = new Vector3(player.x, player.y, 0f);
        game.camera.position.lerp(new Vector3(760, 510, 0f), 1f);

        // Initialise tilemap
        tiledMap = new TmxMapLoader().load("FINAL_MAP.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        // Initialise colleges
        College.capturedCount = 0;
        colleges = new HashSet<>();
        College newCollege;
        Array<Texture> collegeSprites = new Array<>();

        // Add alcuin
        collegeSprites.add( new Texture("alcuin.png"),
                            new Texture("alcuin_2.png"));
        newCollege = new College(collegeSprites, 1492, 665, 0.5f,"Alcuin", enemyTeam, player, "alcuin_boat.png");
        newCollege.addBoat(30, -20, -60);
        newCollege.addBoat(-50, -40, -150);
        newCollege.addBoat(-40, -70, 0);
        colleges.add(newCollege);
        collegeSprites.clear();

        // Add derwent
        collegeSprites.add( new Texture("derwent.png"),
                            new Texture("derwent_2.png"));
        newCollege = (new College(collegeSprites, 1815, 2105, 0.8f,"Derwent", enemyTeam, player, "derwent_boat.png"));
        newCollege.addBoat(-70, -20, 60);
        newCollege.addBoat(-70, -60, 70);
        colleges.add(newCollege);
        collegeSprites.clear();

        // Add langwith
        collegeSprites.add( new Texture("langwith.png"),
                            new Texture("langwith_2.png"));
        newCollege = (new College(collegeSprites, 1300, 1530, 1.0f,"Langwith", enemyTeam, player, "langwith_boat.png"));
        newCollege.addBoat(-150, -50, 60);
        newCollege.addBoat(-120, -10, -60);
        newCollege.addBoat(-10, -40, 230);
        newCollege.addBoat(140, 10, 300);
        newCollege.addBoat(200, 35, 135);
        colleges.add(newCollege);
        collegeSprites.clear();

        // Add goodricke
        collegeSprites.add( new Texture("goodricke.png"));
        colleges.add(new College(collegeSprites, 700, 525, 0.7f,"Home",playerTeam,player, "ship1.png"));

        // Initialise projectiles array to be used storing live projectiles
        projectiles = new HashSet<>();
        //check for weather
        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            public void run(){
                player.checkForWeather();
            }
        };
        t.scheduleAtFixedRate(tt, 500, 700);


        //barrels
        Array<Texture> barrel1 = new Array<Texture>();
        barrel1.add(new Texture(Gdx.files.internal("barrel.png")));

        Array<Texture> barrel2 = new Array<Texture>();
        barrel2.add(new Texture(Gdx.files.internal("barrel_gold.png")));

        //icebergs
        Array<Texture> iceberg = new Array<Texture>();
        iceberg.add(new Texture(Gdx.files.internal("iceberg.png")));
        
        //barrels 
        Barrel b1 = new Barrel(barrel1, 0f, 821f, 608f, 20f, 20f, "ENEMY",40,BarrelType.BROWN);
        Barrel b2 = new Barrel(barrel2, 0f,1086f, 787f, 20f, 20f, "ENEMY",0,BarrelType.GOLD);
        Barrel b3 = new Barrel(barrel1, 0f,1299f, 605f, 20f, 20f, "ENEMY",40,BarrelType.BROWN);
        Barrel b4 = new Barrel(barrel1, 0f,1619f, 524f, 20f, 20f, "ENEMY",40,BarrelType.BROWN);
        Barrel b5 = new Barrel(barrel2, 0f,1936f, 801f, 20f, 20f, "ENEMY",0,BarrelType.GOLD);
        Barrel b6 = new Barrel(barrel1, 0f,1700f, 1532f, 20f, 20f, "ENEMY",40,BarrelType.BROWN);
        Barrel b7 = new Barrel(barrel1, 0f,546f, 1131f, 20f, 20f, "ENEMY",40,BarrelType.BROWN);
        //icebergs
        Obstacle ice1 = new Obstacle(iceberg,0, 950, 600, 50, 40, "ENEMY",100);
        Obstacle ice2 = new Obstacle(iceberg,0, 1530, 500, 50, 40, "ENEMY",100);
        Obstacle ice3 = new Obstacle(iceberg,0, 565, 1075, 50, 40, "ENEMY",100);
        Obstacle ice4 = new Obstacle(iceberg,0, 1111, 1729, 50, 40, "ENEMY",100);
        
        obstacles.add(b1);
        obstacles.add(b2);
        obstacles.add(b3);
        obstacles.add(b4);
        obstacles.add(b5);
        obstacles.add(b6);
        obstacles.add(b7);
        
        obstacles.add(ice1);
        obstacles.add(ice2);
        obstacles.add(ice3);
        obstacles.add(ice4);

        generateRain();
        generateSnow();
        generateStorm();
    }
    private void generateRain(){
        Texture rain = new Texture(Gdx.files.internal("rain.png"));
        // int numOfDrops = (int)Math.floor(Math.random()*(8-6+1)+6);

        //left
        for(int i =0;i<8;i++){
            int x = (int)Math.floor(Math.random()*(((Gdx.graphics.getWidth()/2)+ 200) - ((Gdx.graphics.getWidth()/2)-200)+1) + (Gdx.graphics.getWidth()/2)-200);
            int y = (int)Math.floor(Math.random()*(((Gdx.graphics.getHeight()/2)+200) - ((Gdx.graphics.getHeight()/2)-200)+1) + (Gdx.graphics.getHeight()/2)-200);
            int size = (int)Math.floor(Math.random()*(80-40+1)+40);

            Rain rrain = new Rain(x, y, size,size,rain,0.6f);
            rains.add(rrain);
        }
    }
    private void generateSnow(){
        Texture snow = new Texture(Gdx.files.internal("snow.png"));
        // int numOfFlakes = (int)Math.floor(Math.random()*(8-6+1)+6);
        //left
        for(int i =0;i<8;i++){
            int x = (int)Math.floor(Math.random()*(((Gdx.graphics.getWidth()/2)+ 200) - ((Gdx.graphics.getWidth()/2)-200)+1) + (Gdx.graphics.getWidth()/2)-200);
            int y = (int)Math.floor(Math.random()*(((Gdx.graphics.getHeight()/2)+200) - ((Gdx.graphics.getHeight()/2)-200)+1) + (Gdx.graphics.getHeight()/2)-200);
            int size = (int)Math.floor(Math.random()*(80-40+1)+40);

            Snow rsnow = new Snow(x, y, size,size,snow,0.7f);
            snows.add(rsnow);
        }
    }

    private void generateStorm(){
        Texture rain = new Texture(Gdx.files.internal("rain.png"));
        Texture snow = new Texture(Gdx.files.internal("snow.png"));
        for(int i =0;i<4;i++){
            int x = (int)Math.floor(Math.random()*(((Gdx.graphics.getWidth()/2)+ 200) - ((Gdx.graphics.getWidth()/2)-200)+1) + (Gdx.graphics.getWidth()/2)-200);
            int y = (int)Math.floor(Math.random()*(((Gdx.graphics.getHeight()/2)+200) - ((Gdx.graphics.getHeight()/2)-200)+1) + (Gdx.graphics.getHeight()/2)-200);
            int size = (int)Math.floor(Math.random()*(80-40+1)+40);

            Snow rsnow = new Snow(x, y, size,size,snow,0.7f);
            storms.add(rsnow);
        }
        
        for(int i =0;i<4;i++){
            int x = (int)Math.floor(Math.random()*(((Gdx.graphics.getWidth()/2)+ 200) - ((Gdx.graphics.getWidth()/2)-200)+1) + (Gdx.graphics.getWidth()/2)-200);
            int y = (int)Math.floor(Math.random()*(((Gdx.graphics.getHeight()/2)+200) - ((Gdx.graphics.getHeight()/2)-200)+1) + (Gdx.graphics.getHeight()/2)-200);
            int size = (int)Math.floor(Math.random()*(80-40+1)+40);
         
            Rain rrain = new Rain(x, y, size,size,rain,0.6f);
            storms.add(rrain);
            
        }
        // Texture stormt = new Texture(Gdx.files.internal("transparent.png"));
        
        RectangleColour stormback = new RectangleColour(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),new Color(0,0,0,0.5f));
        storms.add(stormback);
    
    }

    /**
     * Is called once every frame. Runs update(), renders the game and then the HUD.
     * @param delta The time passed since the previously rendered frame.
     */
    @Override
    public void render(float delta){
        // Only update if not paused
        if(!isPaused) {
            elapsedTime += delta;
            update();
        }
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        ScreenUtils.clear(0.1f, 0.6f, 0.6f, 1.0f);

        // Gameplay drawing batch
        game.batch.begin();
        tiledMapRenderer.setView(game.camera); // Draw map first so behind everything
        tiledMapRenderer.render();

        // Draw Projectiles
        for (Projectile p : projectiles) {
            p.draw(game.batch, 0);
        }

        // Draw Player, Player Health and Player Name
        if(!isPaused) {
            player.drawHealthBar(game.batch);
            player.draw(game.batch, elapsedTime);
            HUDBatch.begin();
            Vector3 pos = game.camera.project(new Vector3(player.x, player.y, 0f));
            game.font.draw(HUDBatch, playerName, pos.x, pos.y + 170f, 1f, Align.center, true);
            HUDBatch.end();
        }

        // Draw Colleges
        for (College c : colleges) {
            c.draw(game.batch, 0);
        }
        for(Obstacle o : obstacles){
            o.draw(game.batch, 0);
        }
        
        game.batch.end();

        // Draw HUD
        HUDBatch.setProjectionMatrix(HUDCam.combined);
        if(!isPaused) {
            // Draw UI
            gameHUD.renderStage(this);
            HUDCam.update();
        }
    }

    /**
     * Is called once every frame. Used for game calculations that take place before rendering.
     */
    private void update() {

        // Call updates for all relevant objects
        player.update(this, game.camera);
        
        Iterator<College> cIterator = colleges.iterator();

        while (cIterator.hasNext()) {
            College c = cIterator.next();
            c.update(this);
        }
        
        

        // for (College c : colleges) {
        //     c.update(this);
        // }

        // Check for projectile creation, then call projectile update
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            if(canFire){
                Vector3 mouseVector = new Vector3(Gdx.input.getX(), Gdx.input.getY(),0);
                Vector3 mousePos = game.camera.unproject(mouseVector);
    
                Array<Texture> sprites = new Array<>();
                sprites.add(new Texture("tempProjectile.png"));
                projectiles.add(new Projectile(sprites, 0, player, mousePos.x, mousePos.y, playerTeam));
                gameHUD.endTutorial();
                canFire = false;
                Thread t = new Thread(){
                    public void run(){
                        try{
                            Thread.sleep((int) (1000 * player.projectileShootCooldown));
                        }catch(InterruptedException e){}
                        canFire = true;
                    }
                };
                t.start();
            }
           
        } 
        
        Iterator<Projectile> pIterator = projectiles.iterator();

        while (pIterator.hasNext()) {
            Projectile p = pIterator.next();
            if (p.update(this) == 0) {
                pIterator.remove();
            }
        }

        // for (int i = projectiles.size - 1; i >= 0; i--) {
        //     projectiles.get(i).update(this);
        // }

        // Camera calculations based on player movement
        if(followPlayer) followPos = new Vector3(player.x, player.y, 0);
        if(Math.abs(game.camera.position.x - followPos.x) > 1f || Math.abs(game.camera.position.y - followPos.y) > 1f){
            game.camera.position.slerp(followPos, 0.1f);
        }

        // Call to pause the game
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && elapsedTime - lastPause > 0.1f){
            gamePause();
        }
    }

    /**
     * Called to switch from the current screen to the pause screen, while retaining the current screen's information.
     */
    public void gamePause(){
        isPaused = true;
        game.setScreen(new PauseScreen(game,this));
    }

    /**
     * Called to switch from the current screen to the end screen.
     * @param win   The boolean determining the win state of the game.
     */
    public void gameEnd(boolean win){
        game.setScreen(new EndScreen(game, this, win));
    }

    /**
     * Called to switch from the current screen to the title screen.
     */
    public void gameReset(){
        game.setScreen(new TitleScreen(game));
    }

    /**
     * Used to encapsulate elapsedTime.
     * @return  Time since the current session started.
     */
    public float getElapsedTime() { return elapsedTime; }

    /**
     * Used to toggle whether the camera follows the player.
     * @param follow  Whether the camera will follow the player.
     */
    public void toggleFollowPlayer(boolean follow) { this.followPlayer = follow; }

    /**
     * Get the player's name for the current session.
     * @return  Player's name.
     */
    public String getPlayerName() { return playerName; }

    /**
     * Set the player's name.
     * @param playerName    Chosen player name.
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
        gameHUD.updateName(this);
    }

    /**
     * Get the player.
     * @return  The player.
     */
    public Player getPlayer() { return player; }

    /**
     * Get the main game class.
     * @return  The main game class.
     */
    public YorkPirates getMain() { return game; }

    /**
     * Get the game's HUD.
     * @return  The HUD.
     */
    public HUD getHUD() { return gameHUD; }

    /**
     * Set whether the game is paused or not.
     * @param paused    Whether the game is paused.
     */
    public void setPaused(boolean paused) {
        if (!paused && isPaused) lastPause = elapsedTime;
        isPaused = paused;
    }

    /**
     * Gets whether the game is paused.
     * @return  True if the game is paused.
     */
    public boolean isPaused() { return  isPaused; }

    /**
     * Get the viewport.
     * @return  The viewport.
     */
    public FitViewport getViewport() { return viewport; }

    /**
     * Disposes of disposables when game finishes execution.
     */
    @Override
    public void dispose(){
        HUDBatch.dispose();
        tiledMap.dispose();
        music.dispose();
    }
}
