package uet.oop.bomberman.entities.movable.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.movable.Movable;

public abstract class Enemy extends Movable {
    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void kill() {
        
    }

    @Override
    public void afterKill() {

    }

    public void randomMove() {

    }

    public abstract void chooseSprite();

    @Override
    public void update() {
        if(!isAlive) {
            afterKill();
            return;
        } else {
            randomMove();
        }
    }
}
