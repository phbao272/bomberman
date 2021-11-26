package uet.oop.bomberman.entities.movable.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.movable.enemy.ai.AILow;

public class Doll extends Enemy {
    public Doll(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img, 1, 100);
        ai = new AILow();
        direction = ai.calculateDirection();
    }

    @Override
    public void chooseSprite() {

    }

    @Override
    public void update() {
        if (isAlive) {
            randomMove();
            chooseSprite();
        }
    }
}
