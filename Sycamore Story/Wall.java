import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Wall here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Wall extends CollisionActor
{
    /**
     * Act - do whatever the Wall wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */  
    public void act() 
    {
        super.act();
        checkPunchingStatus();
    }    
    
    public void checkPunchingStatus()
    {
        Ned n = (Ned)getOneIntersectingObject(Ned.class); 
        if (n!=null)
        {
            if(n.isPunching())
            {
                getWorld().removeObject(this);
                return;
            }
        }

    }
    
}
