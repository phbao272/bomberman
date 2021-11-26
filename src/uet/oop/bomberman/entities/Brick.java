package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Entity{
    private boolean isDestroyed = false;
    private int frameToDisappear = 24;

    public Brick(int x, int y, Image img) {
        super(x, y, img);
    }

    public void setDestroyed(boolean destroyed) {
        this.isDestroyed = destroyed;
    }

    @Override
    public void update() {
        if (isDestroyed) {
            if (frameToDisappear > 0) {
                switch (frameToDisappear) {
                    case 24: {
                        img = Sprite.brick_exploded.getFxImage();
                        break;
                    }
                    case 16: {
                        img = Sprite.brick_exploded1.getFxImage();
                        break;
                    }
                    case 8: {
                        img = Sprite.brick_exploded2.getFxImage();
                        break;
                    }
                }
                frameToDisappear--;
            } else {
                BombermanGame.stillObjects.remove(this);
            }
        }
    }
}
