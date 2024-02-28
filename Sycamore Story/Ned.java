import greenfoot.*;
import java.util.*;

public class Ned extends PhysicsActor {
    private static final int SCROLL_WIDTH = 160;
    private static final int SCROLL_HEIGHT = 200;
    private final int LOWER_BOUND = 700;
    private static final float JUMP = 10.0f, WALK = 4.0f;
   
    private int absoluteScroll, initialXPosition, initialYPosition, hitpoints;
    private boolean punching;
    private GreenfootImage ninjaPunch;
    private GreenfootSound walkSound, attackSound;
    private boolean resetting, gameOver, gameWin;
    
    public static HashMap<String, Integer> inventory = new HashMap<>();
    
    public Ned() {
        super("images/warrior", ".png", 7, 2);
        absoluteScroll = 0;
        hitpoints = 100;
        punching = false;
        ninjaPunch = new GreenfootImage("warriorattack7.png");
        walkSound = new GreenfootSound("sounds/shuffle.wav");
        attackSound = new GreenfootSound("sounds/playerAttack.mp3");
        walkSound.setVolume(95);
        attackSound.setVolume(95);
        resetting = false;
        gameOver = false;
        gameWin = false;
        for(GreenfootImage gfi : this.images)
        {
            gfi.scale(75, 75);
        }
        ninjaPunch.scale(75, 75);
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
    
    
    public void handleDisplayHealth() {
        getWorld().showText("Health: " + hitpoints, 500, 25);
    }
    
    public void act() {
        handleJump();
        handlePunch();
        handleMonsterDamage();
        handleDisplayHealth();
        handleAnimation();
        adjustCamera();
        super.act();
        handleReset();
        checkDeath();
        checkWinCondition();
        handleGameOver();
        handleWin();
    } 
    
    public void checkWinCondition(){
        if(!gameWin &&
        inventory.get("images/bananas0.png") >= 3 &&
        inventory.get("images/orange0.png") >= 3 &&
        inventory.get("images/plum0.png") >= 3 &&
        inventory.get("images/pumpkin0.png") >= 1)
        {
            gameWin = true;
            getWorld().addObject(new SpeechBubble("endBubble.png", this, 10), 250, 250);
        }
    }
    
    public void handleMonsterDamage() {
        if (getOneObjectAtOffset(-5, 0, Monster.class) != null && getOneObjectAtOffset(5, 0, Monster.class) != null && !isPunching()) {
            hitpoints--;
            // TODO knockback
        }
    }
    
    public void reset() {
        resetting = true;
    }
    
    public void kill() {
        walkSound.stop();
    }
    
    public void checkDeath(){
        if(hitpoints <= 0){
            getWorld().addObject(new SpeechBubble("failBubble.png", this, 999), -200, -200);
            gameOver = true;
        }
    }
    
    public void handleGameOver(){
        if(gameOver && Greenfoot.isKeyDown("space")){
            gameOver = false;
            reset();
            Greenfoot.setWorld(new NinjaWorld());
        }
    }
    
    public void handleWin(){
        if(gameWin && Greenfoot.isKeyDown("space")){
            gameWin = false;
            Greenfoot.setWorld(new GameOverScreen(3));
        }
    }
    
    private void handleReset() {
        if (resetting) {
            setLocation(initialXPosition, initialYPosition);
        }
        resetting = false;
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
            getWorld().addObject(new SpeechBubble("jumpBubble.png", this, .5), -200, -200);
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
            getWorld().addObject(new SpeechBubble("attackBubble.png", this, 0.3), -200, -200);
            attackSound.play();
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
        int actorY = getY();
        int cameraY = 0;

        if (getY() < SCROLL_HEIGHT && world.getWorldY() < world.getWorldHeight() / 2 - 5) {
            world.setCameraY(SCROLL_HEIGHT - getY());
            absoluteScroll += SCROLL_HEIGHT - getY();
        } else if (getY() > world.getHeight() - SCROLL_HEIGHT && world.getWorldY() > -690) {
            int newY = world.getHeight() - SCROLL_HEIGHT - getY();
            int absoluteScrollIncrement = world.getHeight() - SCROLL_HEIGHT - getY();
            world.setCameraY(newY);
            absoluteScroll += absoluteScrollIncrement;
            //System.out.println("Change Y: NewY: " + newY + ", Abs Increment: " + absoluteScrollIncrement + " Scroll: " + absoluteScroll);
        }
        
        world.absoluteScroll = absoluteScroll; // This is used for respawning
    }
}
