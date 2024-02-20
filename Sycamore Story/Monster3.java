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
        super(xRange, 0, 1, "images/evileye", ".png", 4, 4, spawnX, spawnY);
        for(GreenfootImage gfi : this.images)
        {
            gfi.scale(100, 50);
        }
        
        this.monsterDrop = new Drop3();
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
