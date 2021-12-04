package uet.oop.bomberman.entities.movable;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public abstract class Movable extends Entity {
    protected final int allowDistance = 12;
    protected int step = 0;
    protected int speed = 4;
    protected boolean isMoving = false;
    protected boolean isAlive = true;
    protected String lastDirection = "RIGHT";
    protected int frameToDisappear = 48;
    protected boolean wallPass = false;

    public Movable(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public abstract void kill();

    public abstract void afterKill();

    public void setStep(int step) {
        this.step = step;
    }

    public void setSpeed(int speed) {
       this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public String getLastDirection() {
        return lastDirection;
    }

    public void setLastDirection(String lastDirection) {
        this.lastDirection = lastDirection;
    }

    protected abstract void randomMove();

    @Override
    public void update() {
        
    }
}
