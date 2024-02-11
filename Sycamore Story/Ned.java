import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Ned here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ned extends PhysicsActor
{
    private static final int SCROLL_WIDTH = 160;
    private static final float JUMP = 8.0f, WALK = 4.0f;
   
    private int absoluteScroll, initialXPosition, initialYPosition;
    private boolean punching;
    private GreenfootImage ninjaPunch;
    private GreenfootSound walkSound, powSound;
    private boolean resetting;
    
    public Ned()
    {
        super("images/ninja", ".png", 4, 2);
        absoluteScroll = 0;
        punching = false;
        ninjaPunch = new GreenfootImage("ninjapow.png");
        walkSound = new GreenfootSound("sounds/shuffle.wav");
        powSound = new GreenfootSound("sounds/pow.wav");
        walkSound.setVolume(95);
        powSound.setVolume(95);
        resetting = false;
    }
    
    @Override
    protected void addedToWorld(World world)
    {
        initialXPosition = getX();
        initialYPosition = getY();
    }
    
    @Override
    protected void useDefaultOrientation()
    {
        if(!isDefaultOrientation())
        {
            ninjaPunch.mirrorHorizontally();
        }
        
        super.useDefaultOrientation();
    }
    
    @Override
    protected void useFlippedOrientation()
    {
        if(isDefaultOrientation())
        {
            ninjaPunch.mirrorHorizontally();
        }
        
        super.useFlippedOrientation();
    }
    
    public boolean isPunching()
    {
        return punching;
    }
    
    /**
     * Act - do whatever the Ned wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(Greenfoot.isKeyDown("space") && onGround)
        {
            velocity.y -= JUMP;
            onGround = false;
            getWorld().addObject(new SpeechBubble("jump-bubble.png", this, .5), -200, -200);
        }
        
        if(Greenfoot.isKeyDown("control"))
        {
            if(!punching)
            {
                punching = true;
                useSpecialImage(ninjaPunch);
                getWorld().addObject(new SpeechBubble("pow-bubble.png", this, 0.3), -200, -200);
                powSound.play();
            }
        }
        else
        {
            if(Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("left"))
            {
                if(!isAnimating())
                {
                    animate();
                }
                useFlippedOrientation();
                velocity.x = -WALK;
            }
            else if(Greenfoot.isKeyDown("d") || Greenfoot.isKeyDown("right"))
            {
                if(!isAnimating())
                {
                    animate();
                }
                useDefaultOrientation();
                velocity.x = WALK;
            }
            else
            {
                if(isAnimating() || punching)
                {
                    rest();
                }
                velocity.x = 0.0f;
            }
            
            punching = false;
        }
        
        if(!isAnimating() && punching && onGround)
        {
            velocity.x = 0.0f;
        }
        
        if(isAnimating())
        {
            if(!walkSound.isPlaying())
            {
                walkSound.playLoop();
            }
        }
        else
        {
            if(walkSound.isPlaying())
            {
                walkSound.stop();
            }
        }
        
        NinjaWorld world = ((NinjaWorld)getWorld());
        world.setCameraX(0);
        world.setCameraY(0);
        
        if(Greenfoot.isKeyDown("r"))
        {
            reset();
        }
        
        if(resetting)
        {
            world.setCameraX(-absoluteScroll);
            absoluteScroll = 0;
            velocity.x = 0.0f;
            velocity.y = 0.0f;
            useDefaultOrientation();
        }
        
        // Scrolling
        if((getX() < SCROLL_WIDTH) && (world.getWorldX() < world.getWorldWidth() / 2 - 5))
        {
            world.setCameraX(SCROLL_WIDTH - getX());
            absoluteScroll += SCROLL_WIDTH - getX();
        }
        else if((getX() > world.getWidth() - SCROLL_WIDTH) && (world.getWorldX() > -690))
        {
            world.setCameraX(world.getWidth() - SCROLL_WIDTH - getX());
            absoluteScroll += world.getWidth() - SCROLL_WIDTH - getX();
        }
        
        super.act();
        
        if(resetting)
        {
            setLocation(initialXPosition, initialYPosition);
            // Call save here
        }
        
        resetting = false;
    } 
    
    public void reset()
    {
        resetting = true;
    }
    
    public void kill()
    {
        walkSound.stop();
    }
    
}
