import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class CollisionObject here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CollisionActor extends ScrollingActor
{
    public CollisionActor()
    {
        super();
    }
    
    public CollisionActor(String basename, String suffix, int noOfImages, int delay)
    {
        super(basename, suffix, noOfImages, delay);
    }
    
    @Override
    protected void addedToWorld(World world)
    {
        ((NinjaWorld)world).getCollisionActors().add(this);
    }
}
