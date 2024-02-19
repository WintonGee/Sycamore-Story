import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

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

    
    private static final GreenfootSound music = new GreenfootSound("tsuruga.mp3");
    
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
    }
    
    @Override
    public void act()
    {
        if(!music.isPlaying())
        {
            music.playLoop();
        }
        
        // This handles the respawning of monsters
        if (respawnCounter > RESPAWN_TIME) {
            for (Monster m : monsterRespawns) {
                addObject(m, m.getSpawnX(), m.getSpawnY());
            }
            respawnCounter = 0;
            monsterRespawns.clear();
        }
        
        respawnCounter++;
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
    
    // This code handles the killing and respawning of monsters
    
    private void addActors() {
        Ned ned = new Ned();
        Background bkgrd = new Background();
        addObject(bkgrd, 450, 300);
        addObject(ned, 300, 200);
        
        // Monsters
        Monster monster1 = new Monster1(500, 350, 230);
        addObject(monster1, monster1.getSpawnX(), monster1.getSpawnY());
        addObject(monster1, monster1.getSpawnX(), monster1.getSpawnY());
        addObject(monster1, monster1.getSpawnX(), monster1.getSpawnY());
        
        ///////// TEST OBJECTS
        MonsterDrop monsterDrop1 = new Drop1();
        addObject(monsterDrop1, 500, 250);
        
        
        ////////

        Finish f = new Finish();
        addObject(f, 1050, 195);

        MapDesign md = new MapDesign();
        addObject(md, 50, 50);
        md.LoadMap();
        
        
        //SPEECH BUBBLE
        addObject(new SpeechBubble("play-bubble.png", bkgrd, 1000), -500, -500);
        addObject(new SpeechBubble("big-bubble.png", bkgrd, -10), 450, 300);
        
    }
}
