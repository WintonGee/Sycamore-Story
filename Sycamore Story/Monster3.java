import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class OscillatingPlatform here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Monster3 extends Monster
{
    public Monster3(int xRange, int spawnX, int spawnY)
    {
        super(xRange, 0, 3, "images/warrior", ".png", 7, 2, spawnX, spawnY);
        for(GreenfootImage gfi : this.images)
        {
            gfi.scale(50, 50);
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