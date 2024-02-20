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
        music.setVolume(5);
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
        addObject(bkgrd, 450, -200);
        addObject(ned, 300, 200);
        
        // Monsters (spawnY = mapY - 33)
        //floor 1 (346)
        Monster m1 = new Monster1(100, 650, 313);
        addObject(m1, m1.getSpawnX(), m1.getSpawnY());
        Monster m2 = new Monster1(100, 450, 313);
        addObject(m2, m2.getSpawnX(), m2.getSpawnY());
        //floor 2 (126?)
        Monster m3 = new Monster1(300, 300, 93);
        addObject(m3, m3.getSpawnX(), m3.getSpawnY());
        //floor 3 (-103 -> change to monster2)
        Monster m4 = new Monster1(150, 100, -136);
        addObject(m4, m4.getSpawnX(), m4.getSpawnY());
        Monster m5 = new Monster1(150, 600, -136);
        addObject(m5, m5.getSpawnX(), m5.getSpawnY());
        //floor 4 (-444 -> change to monster3)
        Monster m6 = new Monster1(350, 250, -477);
        addObject(m6, m6.getSpawnX(), m6.getSpawnY());
        //floor 5 (-665 -> change first to monster2, second to monster 3)
        Monster m7 = new Monster1(110, 100, -698);
        addObject(m7, m7.getSpawnX(), m7.getSpawnY());
        Monster m8 = new Monster1(110, 725, -698);
        addObject(m8, m8.getSpawnX(), m8.getSpawnY());
        //floor 6 (-878 -> change to monster 3)
        Monster m9 = new Monster1(50, 100, -911);
        addObject(m9, m9.getSpawnX(), m9.getSpawnY());
        Monster m10 = new Monster1(80, 780, -911);
        addObject(m10, m10.getSpawnX(), m10.getSpawnY());
    
        
        ///////// Final object at peak (-1240)
        MonsterDrop monsterDrop1 = new Drop1();
        addObject(monsterDrop1, 170, -1279);
        
        
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
