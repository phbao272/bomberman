package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Explosion extends Entity {
    protected boolean finalFlame = false;
    private int direction;

    public Explosion(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public Explosion(int x, int y, int direction, boolean finalFlame) {
        super(x, y, null);
        this.direction = direction;

        switch (direction) {
            case 0:
                if (finalFlame) {
                    img = Sprite.explosion_vertical_top_last2.getFxImage();
                } else {
                    img = Sprite.explosion_vertical2.getFxImage();
                }
                break;
            case 1:
                if (finalFlame) {
                    img = Sprite.explosion_horizontal_right_last2.getFxImage();
                } else {
                    img = Sprite.explosion_horizontal2.getFxImage();
                }
                break;
            case 2:
                if (finalFlame) {
                    img = Sprite.explosion_vertical_down_last2.getFxImage();
                } else {
                    img = Sprite.explosion_vertical2.getFxImage();
                }
                break;
            case 3:
                if (finalFlame) {
                    img = Sprite.explosion_horizontal_left_last2.getFxImage();
                } else {
                    img = Sprite.explosion_horizontal2.getFxImage();
                }
                break;
        }
    }

    public int getDirection() {
        return direction;
    }

    @Override
    public void update() {

    }
}
