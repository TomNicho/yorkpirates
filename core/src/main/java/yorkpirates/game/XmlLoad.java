package yorkpirates.game;
import com.badlogic.gdx.Gdx;

import java.io.IOException;
import java.io.FileWriter;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.badlogic.gdx.utils.XmlWriter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.ArrayList;
import java.util.Set;


public class XmlLoad {

    //creates strings showing where files should be stored and loaded. should point to same file. trying to work out how to store in assets atm bear with me.
    static String LoadPath = "YorkPirates/assets/save.xml";
    static String StorePath = "YorkPirates/assets/save.xml";

    //loads xml file into a root element
    private static Element LoadFile (){
        FileHandle LoadHandle = Gdx.files.internal(LoadPath);
        XmlReader reader = new XmlReader();
        Element root = reader.parse(LoadHandle);
        return root;
    }

    //returns a player object loaded from xml file
    public static Player LoadPlayer (Texture texture){
        Element root = LoadFile();
        Element playerElem = root.getChildByName("player");
        Float playerX = Float.parseFloat(playerElem.get("x"));
        System.out.println(String.valueOf(playerX));
        Float playerY = Float.parseFloat(playerElem.get("y"));
        Label playerWeather = HUD.AddWeatherLabel(playerElem.get("weather"));
        Float playerArmour = Float.parseFloat(playerElem.get("armour"));
        Float playerDamage = Float.parseFloat(playerElem.get("damage"));
        Float playerSpeed = Float.parseFloat(playerElem.get("speed"));
        Player toRet = new Player(texture, playerX, playerY, 32, 16, "PLAYER", playerWeather);
        toRet.ARMOUR = playerArmour;
        toRet.DAMAGE = playerDamage;
        toRet.SPEED = playerSpeed;
        System.out.println(String.valueOf(toRet.x));
        return toRet;
    }

    //returns position of college with collegeName in form [x,y]
    public static Float[] LoadCollegePosition (String collegeName){
        Element root = LoadFile();
        Element colleges = root.getChildByName("colleges");
        Element thisCollege = colleges.getChildByName(collegeName);
        Float[] toReturn = {Float.parseFloat(thisCollege.get("x")), Float.parseFloat(thisCollege.get("y"))};
        return toReturn;
    }

    //returns team of college with collegeName
    public static String LoadCollegeTeam (String collegeName){
        Element root = LoadFile();
        Element colleges = root.getChildByName("colleges");
        Element thisCollege = colleges.getChildByName(collegeName);
        return thisCollege.get("team");
    }

    //returns 2d array containing coods of boats attacked to collegename
    public static Float[][] LoadCollegeBoats (String collegeName){
        System.out.println(collegeName);
        Element root = LoadFile();
        Element colleges = root.getChildByName("colleges");
        Element thisCollege = colleges.getChildByName(collegeName);
        Element thisCollegeBoats = thisCollege.getChildByName("boats");
        if (thisCollegeBoats != null) {
            int toRetLength = 0;
            for (Element boat : thisCollegeBoats.getChildrenByName("boat")) {
                toRetLength = toRetLength + 1;
            }
            Float[][] toReturn = new Float[toRetLength][2];
            int loop = 0;
            for (Element boat : thisCollegeBoats.getChildrenByName("boat")) {
                System.out.println(String.valueOf(loop));
                toReturn[loop][0] = Float.parseFloat(boat.get("x"));
                System.out.println(String.valueOf(toReturn[loop][0]));
                toReturn[loop][1] = Float.parseFloat(boat.get("y"));
                loop = loop + 1;
            }
            return toReturn;
        } else {
            Float[][] toReturn = {{}};
            return toReturn;
        }
    }

    //creates an array of objects using xml data
    public static ArrayList<Obstacle> loadObstacles () {

        //creates textures
        Texture barrel_brown = new Texture("barrel.png");
        Texture barrel_gold = new Texture("barrel_gold.png");
        Texture iceberg = new Texture("iceberg.png");
        Texture speed = new Texture("speed.png");
        Texture firerate = new Texture("firerate.png");
        Texture damage = new Texture("damage.png");
        Texture immune = new Texture("immune.png");
        Texture health = new Texture("health.png");

        //iterates through xml creating obstacle objects
        Element root = LoadFile();
        Element obstacles = root.getChildByName("obstacles");
        ArrayList<Obstacle> toRet = new ArrayList<Obstacle>();
        for (Element obs : obstacles.getChildrenByName("barrel")) {
            String x = obs.get("x");
            String y =obs.get("y");
            String type = obs.get("type");
            Barrel toAdd;
            if (type.equals("BROWN")){
                toAdd = new Barrel(barrel_brown, Float.parseFloat(x), Float.parseFloat(y), 20f, 20f, "ENEMY", 40, BarrelType.BROWN);
            } else {
                toAdd = new Barrel(barrel_gold, Float.parseFloat(x), Float.parseFloat(y), 20f, 20f, "ENEMY", 0, BarrelType.GOLD);
            }
            toRet.add(toAdd);
        }
        for (Element obs : obstacles.getChildrenByName("powerup")) {
            String x = obs.get("x");
            String y =obs.get("y");
            String type = obs.get("type");
            PowerUp toAdd;
            if (type.equals("SPEED")){
                toAdd = new PowerUp(speed, Float.parseFloat(x), Float.parseFloat(y), 20f, 20f, "ENEMY", 0, PowerType.SPEED);
            } else if (type.equals("DAMAGE")) {
                toAdd = new PowerUp(damage, Float.parseFloat(x), Float.parseFloat(y), 20f, 20f, "ENEMY", 0, PowerType.DAMAGE);
            } else if (type.equals("FIRERATE")) {
                toAdd = new PowerUp(firerate, Float.parseFloat(x), Float.parseFloat(y), 20f, 20f, "ENEMY", 0, PowerType.FIRERATE);
            } else if (type.equals("HEAL")) {
                toAdd = new PowerUp(health, Float.parseFloat(x), Float.parseFloat(y), 20f, 20f, "ENEMY", 0, PowerType.HEAL);
            } else {
                toAdd = new PowerUp(immune, Float.parseFloat(x), Float.parseFloat(y), 20f, 20f, "ENEMY", 0, PowerType.IMMUNE);
            }
            toRet.add(toAdd);
        }
        for (Element obs : obstacles.getChildrenByName("iceberg")) {
            String x = obs.get("x");
            String y = obs.get("y");
            Obstacle toAdd = new Obstacle(iceberg, Float.parseFloat(x), Float.parseFloat(y), 50, 40, "ENEMY", 100);
            toRet.add(toAdd);
        }
        return toRet;
    }

    //writes to xml file
    public static void Save (Player player, Set<College> colleges){
        try{
        //creates file writer
        FileWriter fw = new FileWriter(StorePath);
        XmlWriter writer = new XmlWriter(fw);
        //saves player
        writer.element("all");
            writer.element("player");
                writer.element("x");
                    writer.text(String.valueOf(player.x));
                writer.pop();
                writer.element("y");
                    writer.text(String.valueOf(player.y));
                writer.pop();
                writer.element("weather");
                    writer.text("None");
                writer.pop();
                writer.element("health");
                    writer.text(String.valueOf(player.currentHealth));
                writer.pop();
                writer.element("speed");
                    writer.text(String.valueOf(player.SPEED));
                writer.pop();
                writer.element("damage");
                    writer.text(String.valueOf(player.DAMAGE));
                writer.pop();
                writer.element("armour");
                    writer.text(String.valueOf(player.ARMOUR));
                writer.pop();
            writer.pop();

            //writes colleges
            writer.element("colleges");
            for (College xmlCollege : colleges){
                writer.element(xmlCollege.collegeName);
                    writer.element("x");
                        writer.text(String.valueOf(xmlCollege.x));
                    writer.pop();
                    writer.element("y");
                        writer.text(String.valueOf(xmlCollege.y));
                    writer.pop();
                    writer.element("team");
                        writer.text(xmlCollege.team);
                    writer.pop();
                writer.element("boats");
                    for (GameObject xmlBoat : xmlCollege.boats) {
                        writer.element("boat");
                            writer.element("x");
                                writer.text(String.valueOf(xmlBoat.x-xmlCollege.x));
                            writer.pop();
                            writer.element("y");
                                writer.text(String.valueOf(xmlBoat.y-xmlCollege.y));
                            writer.pop();
                        writer.pop();
                    }
                    writer.pop();
                writer.pop();
            }
            writer.pop();

            //writes barrels
            writer.element("obstacles");
                for (Obstacle obs: GameScreen.obstacles) {
                    if (obs instanceof Barrel){
                        writer.element("barrel");
                            writer.element("x");
                                writer.text(String.valueOf(obs.x));
                            writer.pop();
                            writer.element("y");
                                writer.text(String.valueOf(obs.y));
                            writer.pop();
                            writer.element("type");
                                Barrel obs1 = (Barrel)obs;
                                writer.text(obs1.type);
                            writer.pop();
                        writer.pop();
                    }
                    if (obs instanceof PowerUp){
                        writer.element("powerup");
                            writer.element("x");
                                writer.text(String.valueOf(obs.x));
                            writer.pop();
                            writer.element("y");
                                writer.text(String.valueOf(obs.y));
                            writer.pop();
                            writer.element("type");
                                PowerUp obs1 = (PowerUp)obs;
                                writer.text(obs1.type);
                            writer.pop();
                        writer.pop();
                    }
                    if (obs.damage == 100){
                        writer.element("iceberg");
                            writer.element("x");
                                writer.text(String.valueOf(obs.x));
                            writer.pop();
                            writer.element("y");
                                writer.text(String.valueOf(obs.y));
                            writer.pop();
                        writer.pop();
                    }
                }
            writer.pop();
        
        //closes all
        writer.pop();

        //closes writer and saves file
        writer.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
}
