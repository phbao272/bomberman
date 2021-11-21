package uet.oop.bomberman.entities.movable;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends Movable {
    public Bomber(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void kill() {

    }

    @Override
    public void afterKill() {

    }

    public void moveRight() {
        if (canMoveRight()) {
            this.x = this.x + speed;
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
            this.x = this.x - speed;
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
            this.y = this.y - speed;
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
            this.y = this.y + speed;
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

    @Override
    public void update() {
        // TODO: Die
        if (!isAlive) {
            if (frameToDisappear > 0) {
                switch (frameToDisappear) {
                    case 48: {
                        img = Sprite.player_dead1.getFxImage();
                        break;
                    }
                    case 32: {
                        img = Sprite.player_dead2.getFxImage();
                        break;
                    }
                    case 16: {
                        img = Sprite.player_dead3.getFxImage();
                        break;
                    }
                }
                frameToDisappear--;
            } else {
                BombermanGame.entities.remove(this);
            }
        }

        // TODO: Dừng lại
        if (!isMoving && isAlive) {
            this.setStep(0);
            switch (lastDirection) {
                case "RIGHT":
                    img = Sprite.player_right.getFxImage();
                    break;
                case "LEFT":
                    img = Sprite.player_left.getFxImage();
                    break;
                case "UP":
                    img = Sprite.player_up.getFxImage();
                    break;
                case "DOWN":
                    img = Sprite.player_down.getFxImage();
                    break;
                default:
                    break;
            }
        }

        // TODO: Di chuyen
        if (isMoving && isAlive) {
            switch (lastDirection) {
                case "RIGHT":
                    moveRight();
                    break;
                case "LEFT":
                    moveLeft();
                    break;
                case "UP":
                    moveUp();
                    break;
                case "DOWN":
                    moveDown();
                    break;
                default:
                    break;
            }
        }
    }
}
