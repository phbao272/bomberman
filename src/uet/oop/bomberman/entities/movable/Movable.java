package uet.oop.bomberman.entities.movable;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.audio.Audio;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Movable extends Entity {
    public int direction = -1;  //0 : up, 1 : right, 2 : down, 3 : left
    private final int allowDistance = 12;
    private int step = 0;
    private int speed = 6;
    protected boolean isMoving = false;
    protected boolean isAlive = true;
    protected String lastDirection = "RIGHT";
    protected int frameToDisapear = 48;

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

    protected abstract void calculateMove();

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public String getLastDirection() {
        return lastDirection;
    }

    public void setLastDirection(String lastDirection) {
        this.lastDirection = lastDirection;
    }

    public void moveRight() {
        if (canMoveRight()) {
            x += speed;
        }
        step++;
        System.out.println("Step: " + step);
        switch (step % 10) {
            case 1:
                setImg(Sprite.player_right_1.getFxImage());
                break;
            case 5:
                setImg(Sprite.player_right_2.getFxImage());
                break;
        }

        System.out.println("Tọa độ người chơi: X: " + x
                + " Y: " + y);
        System.out.println("Tọa độ người chơi max: X: " + getMaxX()
                + " Y: " + getMaxY());
    }

    public void moveLeft() {
        if (canMoveLeft()) {
            x -= speed;
        }
        step++;
        switch (step % 10) {
            case 1:
                setImg(Sprite.player_left_1.getFxImage());
                break;
            case 5:
                setImg(Sprite.player_left_2.getFxImage());
                break;
        }

        System.out.println("Tọa độ người chơi: X: " + x
                + " Y: " + y);
        System.out.println("Tọa độ người chơi max: X: " + getMaxX()
                + " Y: " + getMaxY());
    }

    public void moveUp() {
        if (canMoveUp()) {
            y -= speed;
        }

        step++;
        switch (step % 10) {
            case 1:
                setImg(Sprite.player_up_1.getFxImage());
                break;
            case 5:
                setImg(Sprite.player_up_2.getFxImage());
                break;
        }

        System.out.println("Tọa độ người chơi: X: " + x
                + " Y: " + y);
        System.out.println("Tọa độ người chơi max: X: " + getMaxX()
                + " Y: " + getMaxY());
    }

    public void moveDown() {
        if (canMoveDown()) {
            y += speed;
        }

        step++;
        switch (step % 10) {
            case 1:
                setImg(Sprite.player_down_1.getFxImage());
                break;
            case 5:
                setImg(Sprite.player_down_2.getFxImage());
                break;
        }

        System.out.println("Tọa độ người chơi: X: " + x
                + " Y: " + y);
        System.out.println("Tọa độ người chơi max: X: " + getMaxX()
                + " Y: " + getMaxY());
    }

    public boolean canMoveRight() {
        for (Entity entity : BombermanGame.stillObjects) {
            if (entity instanceof Wall || entity instanceof Brick) {
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

        return true;
    }

    public boolean canMoveLeft() {
        for (Entity entity : BombermanGame.stillObjects) {
            if (entity instanceof Wall || entity instanceof Brick) {
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
        return true;
    }

    public boolean canMoveUp() {
        for (Entity entity : BombermanGame.stillObjects) {
            if (entity instanceof Wall || entity instanceof Brick) {
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
        return true;
    }

    public boolean canMoveDown() {
        for (Entity entity : BombermanGame.stillObjects) {
            if (entity instanceof Wall || entity instanceof Brick) {
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
        return true;
    }

    @Override
    public void update() {
        
    }
}
