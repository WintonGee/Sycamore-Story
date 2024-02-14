import greenfoot.*;

public class Ned extends PhysicsActor {
    private static final int SCROLL_WIDTH = 160;
    private static final float JUMP = 8.0f, WALK = 4.0f;
   
    private int absoluteScroll, initialXPosition, initialYPosition;
    private boolean punching;
    private GreenfootImage ninjaPunch;
    private GreenfootSound walkSound, powSound;
    private boolean resetting;
    
    public Ned() {
        super("images/warrior", ".png", 7, 2);
        absoluteScroll = 0;
        punching = false;
        ninjaPunch = new GreenfootImage("warriorattack7.png");
        walkSound = new GreenfootSound("sounds/shuffle.wav");
        powSound = new GreenfootSound("sounds/pow.wav");
        walkSound.setVolume(95);
        powSound.setVolume(95);
        resetting = false;
    }
    
    @Override
    protected void addedToWorld(World world) {
        initialXPosition = getX();
        initialYPosition = getY();
    }
    
    @Override
    protected void useDefaultOrientation() {
        if (!isDefaultOrientation()) ninjaPunch.mirrorHorizontally();
        super.useDefaultOrientation();
    }
    
    @Override
    protected void useFlippedOrientation() {
        if (isDefaultOrientation()) ninjaPunch.mirrorHorizontally();
        super.useFlippedOrientation();
    }
    
    public boolean isPunching() {
        return punching;
    }
    
    public void act() {
        handleJump();
        handlePunch();
        // handleMovementKeys();
        handleAnimation();
        adjustCamera();
        super.act();
        handleReset();
    } 
    
    public void reset() {
        resetting = true;
    }
    
    public void kill() {
        walkSound.stop();
    }
    
    public void adjustCamera() {
        NinjaWorld world = (NinjaWorld) getWorld();
        resetCameraPosition();
        
        if (Greenfoot.isKeyDown("r")) reset();
        
        if (resetting) {
            setCameraOnReset(world);
        }
        
        handleScrolling(world);
    }
    
    private void handleJump() {
        if (Greenfoot.isKeyDown("space") && onGround) {
            velocity.y -= JUMP;
            onGround = false;
            getWorld().addObject(new SpeechBubble("jump-bubble.png", this, .5), -200, -200);
        }
    }
    
    private void handlePunch() {
        if (Greenfoot.isKeyDown("control")) {
            handlePunchKeyDown();
        } else {
            handleMovementKeys();
            punching = false;
        }
    }
    
    private void handleMovementKeys() {
        if (Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("left")) handleLeftMovement();
        else if (Greenfoot.isKeyDown("d") || Greenfoot.isKeyDown("right")) handleRightMovement();
        else handleNoMovement();
    }
    
    private void handlePunchKeyDown() {
        if (!punching) {
            punching = true;
            useSpecialImage(ninjaPunch);
            getWorld().addObject(new SpeechBubble("pow-bubble.png", this, 0.3), -200, -200);
            powSound.play();
        }
    }
    
    private void handleLeftMovement() {
        handleMovementAction(-WALK, true);
    }
    
    private void handleRightMovement() {
        handleMovementAction(WALK, false);
    }
    
    private void handleNoMovement() {
        if (isAnimating() || punching) rest();
        velocity.x = 0.0f;
    }
    
    private void handleMovementAction(float walkDirection, boolean defaultOrientation) {
        if (!isAnimating()) animate();
        // setOrientation(walkDirection, defaultOrientation);
        if (defaultOrientation) 
            useDefaultOrientation();
        else
            useFlippedOrientation();
        velocity.x = walkDirection;
    }
    
    private void handleAnimation() {
        if (isAnimating()) playWalkSound();
        else stopWalkSound();
        
        if (!isAnimating() && punching && onGround) velocity.x = 0.0f;
    }
    
    private void playWalkSound() {
        if (!walkSound.isPlaying()) walkSound.playLoop();
    }
    
    private void stopWalkSound() {
        if (walkSound.isPlaying()) walkSound.stop();
    }
    
    private void handleReset() {
        if (resetting) {
            setLocation(initialXPosition, initialYPosition);
            // Call save here
        }
        
        resetting = false;
    }
    
    private void resetCameraPosition() {
        NinjaWorld world = (NinjaWorld) getWorld();
        world.setCameraX(0);
        world.setCameraY(0);
    }
    
    private void setCameraOnReset(NinjaWorld world) {
        world.setCameraX(-absoluteScroll);
        absoluteScroll = 0;
        velocity.x = 0.0f;
        velocity.y = 0.0f;
        useDefaultOrientation();
    }
    
    private void handleScrolling(NinjaWorld world) {
        if (getX() < SCROLL_WIDTH && world.getWorldX() < world.getWorldWidth() / 2 - 5) {
            world.setCameraX(SCROLL_WIDTH - getX());
            absoluteScroll += SCROLL_WIDTH - getX();
        } else if (getX() > world.getWidth() - SCROLL_WIDTH && world.getWorldX() > -690) {
            world.setCameraX(world.getWidth() - SCROLL_WIDTH - getX());
            absoluteScroll += world.getWidth() - SCROLL_WIDTH - getX();
        }
    }
}
