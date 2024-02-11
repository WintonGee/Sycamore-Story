import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class PhysicsObject here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PhysicsActor extends CollisionActor
{
    public static final float GRAVITY = 0.4f;
    
    protected Vec2D velocity;
    protected boolean onGround;
    
    public PhysicsActor()
    {
        super();
        
        velocity = new Vec2D();
        onGround = false;
    }
    
    public PhysicsActor(String basename, String suffix, int noOfImages, int delay)
    {
        super(basename, suffix, noOfImages, delay);
        
        velocity = new Vec2D();
        onGround = false;
    }

    /**
     * Act - do whatever the PhysicsObject wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        int newXPos, newYPos, oldXPos, oldYPos;
        if(!onGround)
        {
            velocity.y += GRAVITY;
        }
        
        boolean wasOnGround = onGround;
        oldXPos = getX();
        oldYPos = getY();
        newXPos = (int)(getX() + velocity.x + 0.5f);
        newYPos = (int)(getY() + velocity.y + 0.5f);
        setLocation(newXPos, newYPos);
        
        ArrayList<CollisionActor> collisionActors = ((NinjaWorld)getWorld()).getCollisionActors();
        CollisionActor other;
        onGround = false;
        for(int i = 0; i < collisionActors.size(); ++i)
        {
            other = collisionActors.get(i);
            
            if(this != other)
            {
                int coverType = getCoverType(oldXPos, oldYPos, getImage().getWidth(), getImage().getHeight(), other.getX(), other.getY(), other.getImage().getWidth(), other.getImage().getHeight());
                
                if(this.intersects(other))
                {
                    if(coverType == HORIZ_VERT_COVER)
                    {
                        int thisLeft = this.getX() - (this.getImage().getWidth() / 2);
                        int thisRight = this.getX() + (this.getImage().getWidth() / 2);
                        int thisTop = this.getY() - (this.getImage().getHeight() / 2);
                        int thisBottom = this.getY() + (this.getImage().getHeight() / 2);
                        
                        int otherLeft = other.getX() - (other.getImage().getWidth() / 2);
                        int otherRight = other.getX() + (other.getImage().getWidth() / 2);
                        int otherTop = other.getY() - (other.getImage().getHeight() / 2);
                        int otherBottom = other.getY() + (other.getImage().getHeight() / 2);
                        
                        int rightmostLeftEdge = thisLeft > otherLeft ? thisLeft : otherLeft;
                        int leftmostRightEdge = thisRight < otherRight ? thisRight : otherRight;
                        int bottommostTopEdge = thisTop > otherTop ? thisTop : otherTop;
                        int topmostBottomEdge = thisBottom < otherBottom ? thisBottom : otherBottom;
                        
                        int collisionWidth = leftmostRightEdge - rightmostLeftEdge;
                        int collisionHeight = topmostBottomEdge - bottommostTopEdge;
                        
                        coverType = collisionWidth < collisionHeight ? VERT_COVER : HORIZ_COVER;
                    }
                    
                    if(coverType == VERT_COVER)
                    {
                        if(this.getX() <= other.getX())
                        {
                            newXPos = other.getX() - (this.getImage().getWidth() / 2) - (other.getImage().getWidth() / 2);
                        }
                        else
                        {
                            newXPos = other.getX() + (this.getImage().getWidth() / 2) + (other.getImage().getWidth() / 2);
                        }
                    }
                    else if(coverType == HORIZ_COVER)
                    {
                        if(this.getY() <= other.getY())
                        {
                            newYPos = other.getY() - (this.getImage().getHeight() / 2) - (other.getImage().getHeight() / 2);
                            velocity.y = 0.0f;
                            onGround = true;
                        }
                        else
                        {
                            newYPos = other.getY() + (this.getImage().getHeight() / 2) + (other.getImage().getHeight() / 2);
                            if(velocity.y < 0.0f)
                            {
                                velocity.y = 0.0f;
                            }
                        }
                    }
                    
                    if(other instanceof OscillatingActor)
                    {
                        OscillatingActor oscillator = (OscillatingActor)other;
                        int oscillationState = oscillator.getMovementState();
                        int speed = oscillator.getSpeed();
                        
                        if((oscillationState & OscillatingActor.MOVING_LEFT) != 0 && coverType == HORIZ_COVER)
                        {
                            newXPos -= speed;
                        }
                        else if((oscillationState & OscillatingActor.MOVING_RIGHT) != 0 && coverType == HORIZ_COVER)
                        {
                            newXPos += speed;
                        }
                        if((oscillationState & OscillatingActor.MOVING_UP) != 0 && coverType == VERT_COVER)
                        {
                            newYPos -= speed;
                        }
                        if((oscillationState & OscillatingActor.MOVING_DOWN) != 0 && coverType == VERT_COVER)
                        {
                            newYPos += speed;
                        }
                    }
                }
                else if(this.isDirectlyOver(other) && coverType == HORIZ_COVER)
                {
                    onGround = true;
                }
            }
        }
        
        if(wasOnGround && !onGround)
        {
            velocity.y += GRAVITY;
        }
        
        NinjaWorld world = (NinjaWorld)getWorld();
        if(newYPos + (getImage().getHeight() / 2) > world.getWorldHeight())
        {
            newYPos = world.getWorldHeight() - (getImage().getHeight() / 2);
            velocity.y = 0.0f;
            onGround = true;
        }
        else if(newYPos < getImage().getHeight() / 2)
        {
            newYPos = getImage().getHeight() / 2;
            if(velocity.y < 0.0f)
            {
                velocity.y = 0.0f;
            }
        }
        
        if(newXPos + (getImage().getWidth() / 2) > 900)
        {
            newXPos = 900 - (getImage().getWidth() / 2);
        }
        else if(newXPos < getImage().getWidth() / 2)
        {
            newXPos = getImage().getWidth() / 2;
        }
        
        setLocation(newXPos, newYPos);
        
        super.act();
    }
    
    private boolean isDirectlyOver(Actor other)
    {
        return (this.getY() + this.getImage().getHeight() / 2) == (other.getY() - other.getImage().getHeight() / 2);
    }
    
    public static final int NO_COVER = 0, HORIZ_COVER = 1, VERT_COVER = 2, HORIZ_VERT_COVER = 3;
    private static int getCoverType(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2)
    {
        boolean horizCover = false, vertCover = false;
        
        if(((x1 + w1 / 2) > (x2 - w2 / 2)) && ((x1 - w1 / 2) < (x2 + w2 / 2)))
        {
            horizCover = true;
        }
        
        if(((y1 + h1 / 2) > (y2 - h2 / 2)) && ((y1 - h1 / 2) < (y2 + h2 / 2)))
        {
            vertCover = true;
        }
        
        if(horizCover && vertCover)
        {
            return HORIZ_VERT_COVER;
        }
        else if(horizCover)
        {
            return HORIZ_COVER;
        }
        else if(vertCover)
        {
            return VERT_COVER;
        }
        
        return NO_COVER;
    }
}
