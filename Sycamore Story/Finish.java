import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Finish here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Finish extends ScrollingActor
{
    /**
     * Act - do whatever the Finish wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {   super.act();
        checkWinStatus();
    }  
    
    public void checkWinStatus()
    {
        Ned n = (Ned)getOneIntersectingObject(Ned.class); 
        if (n!=null)
        {
                //getWorld().addObject(new SpeechBubble("end-bubble.png", n, 10), 250, 250);
                n.reset();
                Greenfoot.setWorld(new GameOverScreen());
                return;
        }
    }
}
