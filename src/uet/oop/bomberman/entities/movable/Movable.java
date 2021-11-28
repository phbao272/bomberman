package uet.oop.bomberman.entities.movable;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.entities.bomb.Bomb;

public abstract class Movable extends Entity {
    private final int allowDistance = 12;
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

    public boolean canMoveRight() {
        for (Entity entity : BombermanGame.stillObjects) {
            if (entity instanceof Wall || (entity instanceof Brick && !wallPass)) {
                if (entity.intersectLeft(this)) {
                    if (getMaxY() - entity.getY() <= allowDistance) {
                        y -= getMaxY() - entity.getY();
                    }
                    if (entity.getMaxY() - getY() <= allowDistance) {
                        y += entity.getMaxY() - getY();
                    }
                    return false;
                }
            }
        }

        for (Bomb bomb : BombermanGame.listBombs) {
            if (bomb.intersectLeft(this)) {
                return false;
            }
        }

        return true;
    }

    public boolean canMoveLeft() {
        for (Entity entity : BombermanGame.stillObjects) {
            if (entity instanceof Wall || (entity instanceof Brick && !wallPass)) {
                if (entity.intersectRight(this)) {
                    if (getMaxY() - entity.getY() <= allowDistance) {
                        y -= getMaxY() - entity.getY();
                    }
                    if (entity.getMaxY() - getY() <= allowDistance) {
                        y += entity.getMaxY() - getY();
                    }
                    return false;
                }
            }
        }

        for (Bomb bomb : BombermanGame.listBombs) {
            if (bomb.intersectRight(this)) {
                return false;
            }
        }

        return true;
    }

    public boolean canMoveUp() {
        for (Entity entity : BombermanGame.stillObjects) {
            if (entity instanceof Wall || (entity instanceof Brick)) {
                if (entity.intersectDown(this)) {
                    if (getMaxX() - entity.getX() <= allowDistance) {
                        x -= getMaxX() - entity.getX();
                    }
                    if (entity.getMaxX() - getX() <= allowDistance) {
                        x += entity.getMaxX() - getX();
                    }
                    return false;
                }
            }
        }

        for (Bomb bomb : BombermanGame.listBombs) {
            if (bomb.intersectDown(this)) {
                return false;
            }
        }

        return true;
    }

    public boolean canMoveDown() {
        for (Entity entity : BombermanGame.stillObjects) {
            if (entity instanceof Wall || (entity instanceof Brick && !wallPass)) {
                if (entity.intersectUp(this)) {
                    if (getMaxX() - entity.getX() <= allowDistance) {
                        x -= getMaxX() - entity.getX();
                    }
                    if (entity.getMaxX() - getX() <= allowDistance) {
                        x += entity.getMaxX() - getX();
                    }
                    return false;
                }
            }
        }

        for (Bomb bomb : BombermanGame.listBombs) {
            if (bomb.intersectUp(this)) {
                return false;
            }
        }

        return true;
    }

    protected abstract void randomMove();

    @Override
    public void update() {
        
    }
}
