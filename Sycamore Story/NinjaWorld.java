import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class NinjaWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class NinjaWorld extends World
{
    private final ArrayList<CollisionActor> allCollisionActors;
    private int cameraOffsetX, cameraOffsetY;
    private int worldX, worldY, worldWidth, worldHeight;
    
    // Respawns monsters every X seconds
    public static ArrayList<Monster> monsterRespawns = new ArrayList<>();
    private int RESPAWN_TIME = 1000, respawnCounter = 0;
    public int absoluteScroll = 0; // This is used for respawning relative to the scroll position
    
    private int INVENTORY_DISTANCE = 50;
    
    private static final GreenfootSound music = new GreenfootSound("bgm.mp3");
    
    /**
     * Constructor for objects of class NinjaWorld.
     * 
     */
    public NinjaWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(900, 500, 1, false); // Unbounded
        allCollisionActors = new ArrayList<CollisionActor>();
        addActors();
        worldWidth = getWidth();
        worldHeight = getHeight();
        music.setVolume(35);
    }
    
    @Override
    public void act()
    {
        //checkIfWin();
        if(!music.isPlaying())
        {
            music.playLoop();
        }
        
        // This handles the respawning of monsters
        // TODO might need to account for where the camera is shifted??
        if (respawnCounter > RESPAWN_TIME) {
            for (Monster m : monsterRespawns) {
                // Need to create a new instance of the monster?
                if (m instanceof Monster1) {
                    Monster m1 = new Monster1(m.xRange, m.spawnX, m.spawnY);
                    handleAddMonsterToWorld(m1);
                }
                if (m instanceof Monster2) {
                    Monster m2 = new Monster2(m.xRange, m.spawnX, m.spawnY);
                    handleAddMonsterToWorld(m2);
                }
                if (m instanceof Monster3) {
                    Monster m3 = new Monster3(m.xRange, m.spawnX, m.spawnY);
                    handleAddMonsterToWorld(m3);
                }
            }
            respawnCounter = 0;
            monsterRespawns.clear();
        }
        
        respawnCounter++;
        
        handleDisplayItems();
    }
    
    public ArrayList<CollisionActor> getCollisionActors()
    {
        return allCollisionActors;
    }
    
    @Override
    public void removeObject(Actor object) 
    {
        super.removeObject(object);
        
        if(object instanceof CollisionActor)
        {
            allCollisionActors.remove(object);
        }
    }
     
    public int getCameraX()
    {
        return cameraOffsetX;
    }
    
    public int getCameraY()
    {
        return cameraOffsetY;
    }
    
    public void setCameraX(int x)
    {
        cameraOffsetX = x;
    }
    
    public void setCameraY(int y)
    {
        cameraOffsetY = y;
    }
    
    public void setWorldDimensions(int x, int y, int width, int height)
    {
        worldX = x;
        worldY = y;
        worldWidth = width;
        worldHeight = height;
    }
    
    public int getWorldX()
    {
        return worldX;
    }
    
    public int getWorldY()
    {
        return worldY;
    }
    
    public int getWorldWidth()
    {
        return worldWidth;
    }
    
    public int getWorldHeight()
    {
        return worldHeight;
    }
    
    public void handleDisplayItems() {
        int location = 25;
        for (Map.Entry<String, Integer> entry : Ned.inventory.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            int required = entry.getKey().equals(new Drop4().imagePath) ? 1 : 3;

            // Display key and value
            showText(""+value + " / " + required, 50, location);
            location += INVENTORY_DISTANCE;
        }
    }
    
    // This will add a monster to the world and init the health bar
    public void handleAddMonsterToWorld(Monster m) {
        // TODO remove this print
        // System.out.println("Adding Monster: Camera X: " + getCameraX() + " Y: " + getCameraY() + "Monster X: " + m.getSpawnX() + " Y: " + m.getSpawnY());
        
        addObject(m, m.getSpawnX() + getCameraX(), m.getSpawnY() + getCameraY() + absoluteScroll);
        m.initHealthBar();
    }
    
    
    private void addActors() {
        Ned ned = new Ned();
        Background bkgrd = new Background();
        addObject(bkgrd, 450, -200);
        addObject(ned, 300, 200);
        
        // Inventory
        Ned.inventory.put(new Drop1().imagePath, 0);
        Ned.inventory.put(new Drop2().imagePath, 0);
        Ned.inventory.put(new Drop3().imagePath, 0);
        Ned.inventory.put(new Drop4().imagePath, 0);

        int location = 25;
        for (Map.Entry<String, Integer> entry : Ned.inventory.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();

            ActorWithImage actor = new ActorWithImage(key);  // Replace "image.png" with your actual image file
            addObject(actor, 50, location);  // Add the actor to the world at the specified location

            location += INVENTORY_DISTANCE;
        }
        
        // Monsters (spawnY = mapY - 33)
        //floor 1 (346)
        Monster m1 = new Monster1(100, 650, 313);
        //addObject(m1, m1.getSpawnX(), m1.getSpawnY());
        handleAddMonsterToWorld(m1);
        Monster m2 = new Monster1(100, 450, 313);
        handleAddMonsterToWorld(m2);
        //floor 2 (126?)
        Monster m3 = new Monster1(300, 300, 93);
        handleAddMonsterToWorld(m3);
        //floor 3 (-103 -> change to monster2)
        Monster m4 = new Monster2(150, 100, -136);
        handleAddMonsterToWorld(m4);
        Monster m5 = new Monster2(150, 600, -136);
        handleAddMonsterToWorld(m5);
        //floor 4 (-444 -> change to monster3)
        Monster m6 = new Monster3(350, 250, -477);
        handleAddMonsterToWorld(m6);
        //floor 5 (-665 -> change first to monster2, second to monster 3)
        Monster m7 = new Monster2(110, 100, -698);
        handleAddMonsterToWorld(m7);
        Monster m8 = new Monster3(110, 725, -698);
        handleAddMonsterToWorld(m8);
        //floor 6 (-878 -> change to monster 3)
        Monster m9 = new Monster3(50, 100, -911);
        handleAddMonsterToWorld(m9);
        Monster m10 = new Monster3(80, 780, -911);
        handleAddMonsterToWorld(m10);
    
        
        ///////// Final object at peak (-1240)
        MonsterDrop finalFood = new Drop4();
        addObject(finalFood, 170, -1279);
        
        
        ////////
        MapDesign md = new MapDesign();
        addObject(md, 50, 50);
        md.LoadMap();
        
        
        //SPEECH BUBBLE
        addObject(new SpeechBubble("startBubble.png", bkgrd, 1000), -500, -500);
        //addObject(new SpeechBubble("startBubble.png", bkgrd, -100), 450, 250);
        
    }
}
