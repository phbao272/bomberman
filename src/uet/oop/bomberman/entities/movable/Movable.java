package uet.oop.bomberman.entities.movable;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Sprite;

public class Movable extends Entity {
    private final int allowDistance = 8;
    private int step = 0;
    private int speed = 4;
    protected boolean isMoving = false;
    protected boolean isAlive = true;
    protected int frameToDisapear = 48;

    public Movable(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void moveRight() {
        if (canMoveRight()) {
            this.x = this.x + speed;
        }
        step++;
        System.out.println("Step: " + step);
        switch (step % 5) {
            case 0:
                setImg(Sprite.player_right_1.getFxImage());
                break;
            case 3:
                setImg(Sprite.player_right_2.getFxImage());
                break;
        }
        System.out.println("Right: " + x);
        System.out.println("Max-Right: " + getMaxX());
        System.out.println("Tọa độ người chơi: X: " + x
                + " Y: " + y);
        System.out.println("Tọa độ người chơi max: X: " + getMaxX()
                + " Y: " + getMaxY());
    }

    public void moveLeft() {
        if (canMoveLeft()) {
            this.x = this.x - speed;
        }
        step++;
        switch (step % 10) {
            case 0:
                setImg(Sprite.player_left_1.getFxImage());
                break;
            case 5:
                setImg(Sprite.player_left_2.getFxImage());
                break;
        }
        System.out.println("Left: " + x);
        System.out.println("Tọa độ người chơi: X: " + x
                + " Y: " + y);
        System.out.println("Tọa độ người chơi max: X: " + getMaxX()
                + " Y: " + getMaxY());
    }

    public void moveUp() {
        if (canMoveUp()) {
            this.y = this.y - speed;
        }

        System.out.println("Up: " + y);
        System.out.println("Tọa độ người chơi: X: " + x
                + " Y: " + y);
        System.out.println("Tọa độ người chơi max: X: " + getMaxX()
                + " Y: " + getMaxY());
    }

    public void moveDown() {
        if (canMoveDown()) {
            this.y = this.y + speed;
        }
        System.out.println("Down: " + getY());
        System.out.println("Down: " + getMaxY());
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
