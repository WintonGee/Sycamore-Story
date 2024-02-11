import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GameOverScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameOverScreen extends World
{

    /**
     * Constructor for objects of class GameOverScreen.
     * 
     */
    public GameOverScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1);
        setBackground("end.png");
        repaint();
    }

    public void act()
    {
        if (Greenfoot.isKeyDown("space"))
        {
            NinjaWorld w = new NinjaWorld();
            Greenfoot.setWorld(w);
        }
    }

}
