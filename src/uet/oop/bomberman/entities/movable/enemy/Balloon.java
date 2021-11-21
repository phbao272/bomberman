package uet.oop.bomberman.entities.movable.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.movable.ai.AILow;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public class Balloon extends Enemy {

    public Balloon(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img, 1, 100);
        ai = new AILow();
        direction = ai.calculateDirection();
    }

    @Override
    public void kill() {
        isAlive = false;
    }

    @Override
    public void afterKill() {

    }

    //direction = 0, 1 -> balloom_right, else -> left
    //step = 0, 4, 8 -> image 1, 2, 3
    @Override
    protected void chooseSprite() {
        switch (step % MAX_STEP) {
            case 0: {
                if (direction == 0 || direction == 1) {
                    img = Sprite.balloom_right1.getFxImage();
                } else {
                    img = Sprite.balloom_left1.getFxImage();
                }
                break;
            }
            case 10: {
                if (direction == 0 || direction == 1) {
                    img = Sprite.balloom_right2.getFxImage();
                } else {
                    img = Sprite.balloom_left2.getFxImage();
                }
                break;
            }
            case 20: {
                if (direction == 0 || direction == 1) {
                    img = Sprite.balloom_right3.getFxImage();
                } else {
                    img = Sprite.balloom_left3.getFxImage();
                }
                break;
            }
        }
    }

    @Override
    public void render(Screen screen) {
        if (isAlive)
            chooseSprite();
        else {
            if (frameToDisapear > 0) {
                switch (frameToDisapear) {
                    case 18: {
                        img = Sprite.balloom_dead.getFxImage();
                        break;
                    }
                    case 12: {
                        img = Sprite.mob_dead1.getFxImage();
                        break;
                    }
                    case 6: {
                        img = Sprite.mob_dead2.getFxImage();
                        break;
                    }
                    case 0: {
                        img = Sprite.mob_dead3.getFxImage();
                        break;
                    }
                }
                frameToDisapear--;
            } else {
                BombermanGame.entities.remove(this);
            }
        }
        screen.getGraphicsContext().drawImage(img, x, y);
    }
}
