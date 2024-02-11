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
        // Add your action code here.
    }    
    
    public void SaveMap() {
        SaveMap("map.txt");
    }
    
    public void SaveMap(String filename) {
        NinjaWorld world = (NinjaWorld)getWorld();
        ArrayList<CollisionActor> actors = world.getCollisionActors();
        List<Fire> fire = world.getObjects(Fire.class);
        Writer writer = null;
        
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filename), "utf-8"));
            for (CollisionActor a : actors) {
                if (a.getClass().toString().substring(6).equals("OscillatingPlatform")) {
                    OscillatingPlatform op = (OscillatingPlatform)a;
                    writer.write(a.getClass().toString().substring(6)
                        + "," + op.getXRange() + "," + op.getYRange() + "," + op.getSpeed()
                        + "," + op.getStartX() + "," + op.getStartY() + ",");
                } else {
                    writer.write(a.getClass().toString().substring(6) 
                        + "," + a.getX()+","+a.getY()+",");
                }
            }
            for (Fire f : fire) {
                writer.write("Fire,"+f.getX()+","+f.getY()+",");
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
                } else if (className.equals("Wall")) {
                    Wall w = new Wall();
                    world.addObject(w, sc.nextInt(), sc.nextInt());
                } else if (className.equals("OscillatingPlatform")) {
                    OscillatingPlatform op = new OscillatingPlatform(sc.nextInt(), sc.nextInt(), sc.nextInt());
                    world.addObject(op, sc.nextInt(), sc.nextInt());
                } else if (className.equals("Fire")) {
                    Fire f = new Fire();
                    world.addObject(f, sc.nextInt(), sc.nextInt());
                }
            }
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
    }
}
