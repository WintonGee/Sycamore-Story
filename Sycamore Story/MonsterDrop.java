import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class OscillatingActor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MonsterDrop extends ScrollingActor
{
    public static final int MOVING_LEFT = 1, MOVING_RIGHT = 2, MOVING_UP = 4, MOVING_DOWN = 8;
    private int xRange, yRange, xOscillation, yOscillation, speed;
    private boolean movingRight, movingDown;
    private String basename;
    
    // Used for handling delay
    int delayCounter = 0, DELAY_AMOUNT = 2;
    
    
    String imagePath;
    
    public MonsterDrop(String basename, String suffix, int noOfImages, int delay)
    {
        super(basename, suffix, noOfImages, delay);
        imagePath = basename + "0" + suffix; // This is only used for the inventory display, so only show first frame is needed
        
        this.basename = basename;
        
        this.yRange = 20;
        this.speed = 1;
        this.xOscillation = 0;
        this.yOscillation = 0;
        movingRight = true;
        movingDown = true;
        
        
        // Make all the drop sizes the same
        for(GreenfootImage gfi : this.images)
        {
            gfi.scale(20, 20);
        }
    }
    
    
    
    public int getStartX() {
        return getX()-xOscillation;
    }
    
    public int getStartY() {
        return getY()-yOscillation;
    }
    
    public int getXRange() {
        return xRange;
    }
    
    public int getYRange() {
        return yRange;
    }
    
    public int getSpeed() {
        return speed;
    }
    
    public int getMovementState()
    {
        int state = 0;
        
        if(yRange > 0)
        {
            if(movingDown)
            {
                state |= MOVING_DOWN;
            }
            else
            {
                state |= MOVING_UP;
            }
        }
        
        return state;
    }
    
    public boolean shouldDelayOscillation() {
        return delayCounter < DELAY_AMOUNT;
    }
    
    public void resetDelay() {
        delayCounter = 0;
    }
    
    public void checkCollectionStatus()
    {
        Ned n = (Ned)getOneIntersectingObject(Ned.class); 
        if (n!=null)
        {
            int newCount = Ned.inventory.getOrDefault(imagePath, 0) + 1;
            Ned.inventory.put(imagePath, newCount);
            getWorld().removeObject(this);
            return;
        }

    }
    
    /**
     * Act - do whatever the OscillatingActor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(yRange > 0)
        {
            if (shouldDelayOscillation()) {
                delayCounter++;
            }
            else if(movingDown)
            {
                yOscillation += speed;
                if(yOscillation >= yRange)
                {
                    movingDown = false;
                }
                setLocation(getX(), getY() + speed);
                resetDelay();
            }
            else
            {
                yOscillation -= speed;
                if(yOscillation <= 0)
                {
                    movingDown = true;
                }
                setLocation(getX(), getY() - speed);
                resetDelay();
            }
        }
        
        super.act();
        checkCollectionStatus();
    }
}
