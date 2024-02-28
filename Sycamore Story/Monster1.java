import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class OscillatingPlatform here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Monster1 extends Monster
{
    public Monster1(int xRange, int spawnX, int spawnY)
    {
        super(xRange, 0, 1, "images/rockymask", ".png", 5, 2, spawnX, spawnY, 20);
        for(GreenfootImage gfi : this.images)
        {
            gfi.scale(50, 50);
        }
        this.monsterDrop = new Drop1();
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
