import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class OscillatingActor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Monster extends ScrollingActor
{
    public static final int MOVING_LEFT = 1, MOVING_RIGHT = 2, MOVING_UP = 4, MOVING_DOWN = 8;
    public int xRange, yRange, xOscillation, yOscillation, speed;
    private boolean movingRight, movingDown;
    MonsterDrop monsterDrop;
    public int spawnX, spawnY;
    
    // Hitpoints
    public HealthBar healthBar;
    private final int MAX_HEALTH = 2;
    public int hitpoints = 2;
    public int hitDelayCounter = 0, HIT_DELAY = 20; // Use this to avoid hitting monsters too fast
    
    
    public Monster(int xRange, int yRange, int speed, String basename, String suffix, int noOfImages, int delay, int spawnX, int spawnY)
    {
        super(basename, suffix, noOfImages, delay);
        
        this.xRange = xRange;
        this.yRange = yRange;
        this.speed = speed;
        this.xOscillation = 0;
        this.yOscillation = 0;
        movingRight = true;
        movingDown = true;
        
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        
        this.monsterDrop = new Drop1();
        healthBar = new HealthBar(100, 10, MAX_HEALTH, hitpoints / (float) MAX_HEALTH);
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
    
    public int getSpawnX() {
        return spawnX;
    }
    
    public int getSpawnY() {
        return spawnY;
    }
    
    public int getMovementState()
    {
        int state = 0;
        
        if(xRange > 0)
        {
            if(movingRight)
            {
                state |= MOVING_RIGHT;
            }
            else
            {
                state |= MOVING_LEFT;
            }
        }
        
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
    
    // This handles the random direction changes
    public void handleRandomDirectionChange() {
        boolean shouldChangeDirection = Greenfoot.getRandomNumber(102) > 100;
        if (shouldChangeDirection) {
            movingRight = movingRight ? false : true;
        }
    }
    
    // This handles the direction a character is facing
    public void handleFacingDirection() {
        if (movingRight)
            super.useFlippedOrientation();
        else
            super.useDefaultOrientation();
    }
    
    
    public void checkPunchingStatus()
    {
        hitDelayCounter--;
        Ned n = (Ned)getOneIntersectingObject(Ned.class); 
        if (n!=null)
        { 
            if(n.isPunching() && hitDelayCounter <= 0)
            { 
                hitpoints--;
                hitDelayCounter = HIT_DELAY;
            }
            
            if (hitpoints <= 0) {
                handleItemDrop();
                NinjaWorld.monsterRespawns.add(this);
                getWorld().removeObject(this);
                return;
            }
        }

    }
    
     // This should only be called when the monster is dead
    public void handleItemDrop() {
        getWorld().addObject(monsterDrop, getX(), getY());
    }

    /**
     * Act - do whatever the OscillatingActor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Health Bar
        healthBar.setLocation(getX(), getY() - getImage().getHeight() / 2 - 20);
        
        // Randomize the direction monster travels in
        // handleRandomDirectionChange();
        handleFacingDirection();
        
        if(xRange > 0)
        {
            if(movingRight)
            {
                xOscillation += speed;
                if(xOscillation >= xRange)
                {
                    movingRight = false;
                }
                setLocation(getX() + speed, getY());
            }
            else
            {
                xOscillation -= speed;
                if(xOscillation <= 0)
                {
                    movingRight = true;
                }
                setLocation(getX() - speed, getY());
            }
        }
        
        if(yRange > 0)
        {
            if(movingDown)
            {
                yOscillation += speed;
                if(yOscillation >= yRange)
                {
                    movingDown = false;
                }
                setLocation(getX(), getY() + speed);
            }
            else
            {
                yOscillation -= speed;
                if(yOscillation <= 0)
                {
                    movingDown = true;
                }
                setLocation(getX(), getY() - speed);
            }
        }
        
        super.act();
        checkPunchingStatus();
    }
}
