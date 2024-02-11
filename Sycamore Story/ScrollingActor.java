import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ScrollingActor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ScrollingActor extends AnimatedActor
{
    public ScrollingActor()
    {
        super();
    }
    
    public ScrollingActor(String basename, String suffix, int noOfImages, int delay)
    {
        super(basename, suffix, noOfImages, delay);
    }

    /**
     * Act - do whatever the CollisionObject wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        super.act();
        setLocation(getX() + ((NinjaWorld)getWorld()).getCameraX(), getY());
    }   
}
