import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ActorWithImage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ActorWithImage extends Actor {
    public ActorWithImage(String imageName) {
        setImage(imageName);
        
        GreenfootImage image = getImage();
        
        if (image != null) {

            // Scale the image to the desired width and height
            image.scale(35, 35);
        }
    }

    public void act() {
        // Add any additional behavior if needed
    }
}