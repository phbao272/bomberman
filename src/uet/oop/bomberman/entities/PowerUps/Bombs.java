package uet.oop.bomberman.entities.PowerUps;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.movable.Bomber;

import static uet.oop.bomberman.BombermanGame.numberOfBombs;

public class Bombs extends PowerUp{
    public Bombs(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void render(GraphicsContext gc) {
        for (int i = 0; i < BombermanGame.stillObjects.size(); i++) {
            if ((BombermanGame.stillObjects.get(i) instanceof PowerUp)
                    && !(BombermanGame.stillObjects.get(i + 1) instanceof Brick)) {
                ((PowerUp) BombermanGame.stillObjects.get(i)).setActive(true);
            }
        }
        for (Entity entity : BombermanGame.entities) {
            if (entity instanceof Bomber) {
                if (entity.isCollided(this) && this.isActive()) {
                    numberOfBombs++;
                    myAudio.playSound("res/audio/power_up.wav", 0);
                    System.out.println("Bomb: " + numberOfBombs);
                    BombermanGame.stillObjects.remove(this);
                }
            }
        }
        gc.drawImage(img, x, y);
    }

    @Override
    public void update() {

    }

}
