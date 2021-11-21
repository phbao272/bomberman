package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Entity {
    private int timeToExplode = 200;
    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if (timeToExplode > 0) {
            switch (timeToExplode % 60) {
                case 0: {
                    img = Sprite.bomb.getFxImage();
                    break;
                }
                case 20: {
                    img = Sprite.bomb_1.getFxImage();
                    break;
                }
                case 40: {
                    img = Sprite.bomb_2.getFxImage();
                    break;
                }
            }
            timeToExplode--;
        } else {
            BombermanGame.listBombs.remove(this);
        }
    }
}
