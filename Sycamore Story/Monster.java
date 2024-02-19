import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class OscillatingActor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Monster extends CollisionActor
{
    public static final int MOVING_LEFT = 1, MOVING_RIGHT = 2, MOVING_UP = 4, MOVING_DOWN = 8;
    private int xRange, yRange, xOscillation, yOscillation, speed;
    private boolean movingRight, movingDown;
    
    // TODO add in the monster images to he super??
    public Monster(int xRange, int yRange, int speed)
    {
        super(); // This is the code that should have
        
        this.xRange = xRange;
        this.yRange = yRange;
        this.speed = speed;
        this.xOscillation = 0;
        this.yOscillation = 0;
        movingRight = true;
        movingDown = true;
    }
    
    public Monster(int xRange, int yRange, int speed, String basename, String suffix, int noOfImages, int delay)
    {
        super(basename, suffix, noOfImages, delay);
        
        this.xRange = xRange;
        this.yRange = yRange;
        this.speed = speed;
        this.xOscillation = 0;
        this.yOscillation = 0;
        movingRight = true;
        movingDown = true;
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

    /**
     * Act - do whatever the OscillatingActor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        
        // Randomize the direction monster travels in
        handleRandomDirectionChange();
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
    }
}
