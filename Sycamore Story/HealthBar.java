import greenfoot.*;

public class HealthBar extends Actor {
    private int maxHealth; 
    
    public HealthBar(int width, int height, int maxHealth, float healthPercentage) {
        this.maxHealth = maxHealth; 
        updateHealthBar(width, height, healthPercentage);
    }

    public void updateHealthBar(int width, int height, float healthPercentage) {
        GreenfootImage bar = new GreenfootImage(width + 2, height + 2);
        bar.setColor(Color.GRAY);
        bar.fillRect(0, 0, width + 2, height + 2);
        
        
        if (healthPercentage > 0.5) {
            bar.setColor(Color.GREEN);
        } else if (healthPercentage > 0.25) {
            bar.setColor(Color.ORANGE);
        } else {
            bar.setColor(Color.RED);
        }
        
        bar.fillRect(1, 1, (int)(healthPercentage * width), height);
        setImage(bar);
    }
    
    
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
}