import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Scenery here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Background extends ScrollingActor
{
    /**
     * Act - do whatever the Scenery wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        super.act();
        ((NinjaWorld)getWorld()).setWorldDimensions(getX(), getY(), getImage().getWidth(), getImage().getHeight());
        /*
        System.out.println("BG Img width = " + getImage().getWidth());
        System.out.println("BG Img height = " + getImage().getHeight());
        */
    }    
}
