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
    
    private static final GreenfootSound music = new GreenfootSound("tsuruga.mp3");
    
    /**
     * Constructor for objects of class NinjaWorld.
     * 
     */
    public NinjaWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(900, 600, 1, false); // Unbounded
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
    
    private void addActors() {
        Ned ned = new Ned();
        Background forest = new Background();
        Chameleon chammy = new Chameleon();
        addObject(forest, 300, 300);
        addObject(ned, 160, 200);
        addObject(chammy, 200, 180);


        Finish f = new Finish();
        addObject(f, 1050, 195);

        MapDesign md = new MapDesign();
        addObject(md, 50, 50);
        md.LoadMap();
        
        //SPEECH BUBBLE
        addObject(new SpeechBubble("play-bubble.png", forest, 1000), -500, -500);
        addObject(new SpeechBubble("big-bubble.png", forest, -10), 450, 300);
        
    }
}
