import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GameOverScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameOverScreen extends World
{
    private double timer;
    private long startTime;
    private boolean waitTimeOver = false;

    /**
     * Constructor for objects of class GameOverScreen.
     * 
     */
    public GameOverScreen(double timer)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(900, 500, 1);
        setBackground("gameEndPage.png");
        repaint();
        this.timer = timer * 1000;
        this.startTime = System.currentTimeMillis();
    }

    public void act()
    {
        if(!waitTimeOver && System.currentTimeMillis() > startTime + timer){
            waitTimeOver = true;
        }
        
        if (waitTimeOver && Greenfoot.isKeyDown("space"))
        {
            NinjaWorld w = new NinjaWorld();
            Greenfoot.setWorld(w);
        }
    }

}
