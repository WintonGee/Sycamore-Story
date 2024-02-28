import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class OscillatingPlatform here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Drop4 extends MonsterDrop
{
    
    public Drop4()
    {
        super("images/finaldrop", ".png", 1, 2);
        for(GreenfootImage gfi : this.images)
        {
            gfi.scale(45, 45);
        }
    }
    
    /**
     * Act - do whatever the OscillatingPlatform wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        super.act();
    }    
}
