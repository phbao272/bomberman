package uet.oop.bomberman.entities.movable;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends Movable {
    public Bomber(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if (!isAlive) {
            if (frameToDisapear > 0) {
                switch (frameToDisapear) {
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
                frameToDisapear--;
            } else {
                BombermanGame.entities.remove(this);
            }
        }
    }
}
