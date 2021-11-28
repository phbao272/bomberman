package uet.oop.bomberman.entities.PowerUps;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.audio.Audio;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.movable.Bomber;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.NumberofBombs;

public class Speed extends PowerUp{

    public Speed(int x, int y, Image img) {
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
                if (entity.intersect(this) && this.isActive()) {
                    BombermanGame.bomberman.setSpeed();
                    myAudio.playSound("res/audio/power_up.wav", 0);
                    System.out.println("Speed + 2");
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
