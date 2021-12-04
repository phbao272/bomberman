package uet.oop.bomberman.entities.movable;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.audio.Audio;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.movable.enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends Movable {
    private boolean gameOver = false;

    public Bomber(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void kill() {
        setAlive(false);
        Audio.playSound("res/audio/dead.wav", 0);
        if (BombermanGame.lives > 0) {
            BombermanGame.lives -= 1;

        }
    }

    @Override
    public void afterKill() {
        if (BombermanGame.lives > 0) {
                BombermanGame.restartMap();
//            setAlive(true);
        } else {
            setGameOver(true);
            BombermanGame.entities.remove(this);
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    @Override
    protected void randomMove() {

    }

    public void moveRight() {
        if (canMoveRight()) {
            this.x = this.x + speed;
        }
        step++;
        switch (step % 20) {
            case 1:
                setImg(Sprite.player_right_1.getFxImage());
                Audio.playSound("res/audio/walk.wav", 0);
                break;
            case 10:
                setImg(Sprite.player_right_2.getFxImage());
                Audio.playSound("res/audio/walk.wav", 0);
                break;
        }
    }

    public void moveLeft() {
        if (canMoveLeft()) {
            this.x = this.x - speed;
        }
        step++;
        switch (step % 20) {
            case 1:
                setImg(Sprite.player_left_1.getFxImage());
                Audio.playSound("res/audio/walk.wav", 0);
                break;
            case 10:
                setImg(Sprite.player_left_2.getFxImage());
                Audio.playSound("res/audio/walk.wav", 0);
                break;
        }
    }

    public void moveUp() {
        if (canMoveUp()) {
            this.y = this.y - speed;
        }

        step++;
        switch (step % 20) {
            case 1:
                setImg(Sprite.player_up_1.getFxImage());
                Audio.playSound("res/audio/walk.wav", 0);
                break;
            case 10:
                setImg(Sprite.player_up_2.getFxImage());
                Audio.playSound("res/audio/walk.wav", 0);
                break;
        }
    }

    public void moveDown() {
        if (canMoveDown()) {
            this.y = this.y + speed;
        }

        step++;
        switch (step % 20) {
            case 1:
                setImg(Sprite.player_down_1.getFxImage());
                Audio.playSound("res/audio/walk.wav", 0);
                break;
            case 10:
                setImg(Sprite.player_down_2.getFxImage());
                Audio.playSound("res/audio/walk.wav", 0);
                break;
        }
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

        for (Entity entity : BombermanGame.entities) {
            if (entity instanceof Enemy) {
                if (entity.intersectLeft(this)) {
                    BombermanGame.bomberman.kill();
                    System.out.println(1);
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

        for (Entity entity : BombermanGame.entities) {
            if (entity instanceof Enemy) {
                if (entity.intersectRight(this)) {
                    BombermanGame.bomberman.kill();
                    System.out.println(3);
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

        for (Entity entity : BombermanGame.entities) {
            if (entity instanceof Enemy) {
                if (entity.intersectDown(this)) {
                    BombermanGame.bomberman.kill();
                    System.out.println(0);
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

        for (Entity entity : BombermanGame.entities) {
            if (entity instanceof Enemy) {
                if (entity.intersectUp(this)) {
                    BombermanGame.bomberman.kill();
                    System.out.println(2);
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
                afterKill();
                frameToDisappear = 48;
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
