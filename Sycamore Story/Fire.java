import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Scenery here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Fire extends ScrollingActor
{
    public Fire()
    {
        super("images/Fire", ".png", 3, 2);
    }

    /**
     * Act - do whatever the Scenery wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        super.act();
        checkFireStatus();
    }  
    public void checkFireStatus()
    {
        Ned n = (Ned)getOneIntersectingObject(Ned.class); 
        if (n!=null)
        {
               n.kill();
               NinjaWorld w = new NinjaWorld();
               Greenfoot.setWorld(w);
               w.addObject(new SpeechBubble("fail-bubble.png", n, 1000), 250, 250);
               return;
        }
    }
}
