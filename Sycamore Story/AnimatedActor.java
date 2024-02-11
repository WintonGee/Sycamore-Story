import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class AnimatedActor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AnimatedActor extends Actor
{
    public enum AnimationState
    {
        ANIMATING, FROZEN, RESTING, SPECIAL;
    }

    private GreenfootImage[] images = null;
    private GreenfootImage specialImage = null;
    private int currentImage = 0, animationCounter = 0, delay = 0;
    private AnimationState animationState;
    private boolean isDefaultOrientation;

    public AnimatedActor()
    {
        super();
        animationState = AnimationState.FROZEN;
        isDefaultOrientation = true;
    }
    
    public AnimatedActor(String basename, String suffix, int noOfImages, int delay)
    {
        super();
        
        images = new GreenfootImage[noOfImages];
        for(int i = 0; i < noOfImages; ++i)
        {
            images[i] = new GreenfootImage(basename + i + suffix);
        }
        setImage(images[currentImage]);
        
        this.delay = delay;
        animationState = AnimationState.ANIMATING;
        isDefaultOrientation = true;
    }

    /**
     * Act - do whatever the AnimatedActor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(images != null)
        {
            if(animationState == AnimationState.ANIMATING)
            {
                if(animationCounter++ > delay)
                {
                    animationCounter = 0;
                    currentImage = (currentImage + 1) % images.length;
                    setImage(images[currentImage]);
                }
            }
            else if(animationState == AnimationState.SPECIAL)
            {
                setImage(specialImage);
            }
            else if(animationState == AnimationState.RESTING)
            {
                animationState = AnimationState.FROZEN;
                animationCounter = 0;
                currentImage = 0;
                setImage(images[currentImage]);
            }
        }
    }
    
    protected void useSpecialImage(GreenfootImage image)
    {
        this.specialImage = image;
        animationState = AnimationState.SPECIAL;
    }
    
    protected void animate()
    {
        animationState = AnimationState.ANIMATING;
    }
    
    protected void rest()
    {
        animationState = AnimationState.RESTING;
    }
    
    protected boolean isAnimating()
    {
        return animationState == AnimationState.ANIMATING;
    }
    
    protected boolean isDefaultOrientation()
    {
        return isDefaultOrientation;
    }
    
    protected void useDefaultOrientation()
    {
        if(!isDefaultOrientation)
        {
            isDefaultOrientation = true;
            flipOrientation();
        }
    }
    
    protected void useFlippedOrientation()
    {
        if(isDefaultOrientation)
        {
            isDefaultOrientation = false;
            flipOrientation();
        }
    }
    
    private void flipOrientation()
    {
        for(int i = 0; i < images.length; ++i)
        {
            images[i].mirrorHorizontally();
        }
    }
}
