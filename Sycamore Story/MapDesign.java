import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import java.io.*;

/**
 * Saves and Loads a map of Collision Actors
 * 
 * @author Blue
 * @version (a version number or a date)
 */
public class MapDesign extends Actor
{
    /**
     * Act - do whatever the MapDesign wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (Greenfoot.isKeyDown("m")) {
            System.out.println("saving map");
            SaveMap("map.txt");
            while (Greenfoot.isKeyDown("m")){ continue; }
        }
    }    
    
    public void SaveMap() {
        SaveMap("map.txt");
    }
    
    public void SaveMap(String filename) {
        NinjaWorld world = (NinjaWorld)getWorld();
        ArrayList<CollisionActor> actors = world.getCollisionActors();
        Writer writer = null;
        
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filename), "utf-8"));
            for (CollisionActor a : actors) {
                writer.write(a.getClass().toString().substring(6) + "," + a.getX()+","+a.getY()+",");
            }
        } catch (IOException ex){
            System.out.println("Problem Writing to file");
        } finally {
            try {writer.close();} catch (Exception ex) {}
        }
    }
    
    public void LoadMap() {
        LoadMap("map.txt");
    }
    
    public void LoadMap(String filename) {
        
        NinjaWorld world = (NinjaWorld)getWorld();
        String className = "";
        Scanner sc = null;
        
        try {
            sc = new Scanner(getClass().getResourceAsStream(filename));
            sc.useDelimiter(",");
            while (sc.hasNext()) {
                className = sc.next();
                if (className.equals("Ned")) {
                    if (world.getObjects(Ned.class).size() == 0) {
                        Ned n = new Ned();
                        world.addObject(n, sc.nextInt(), sc.nextInt());
                    }
                } else if (className.equals("Platform")) {
                    Platform p = new Platform();
                    world.addObject(p, sc.nextInt(), sc.nextInt());
                }
            }
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
    }
}
