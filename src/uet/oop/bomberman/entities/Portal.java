package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import static uet.oop.bomberman.BombermanGame.entities;
import uet.oop.bomberman.entities.movable.Bomber;
import uet.oop.bomberman.entities.movable.enemy.Enemy;


public class Portal extends Entity {
    private boolean Open = false;
    private boolean AllKilled = true;

    public Portal(int x, int y, Image img) {
        super(x, y, img);
    }

    public boolean isOpen() {
        return Open;
    }

    public void setOpen(boolean open) {
        Open = open;
    }

    public boolean isAllKilled() {
        return AllKilled;
    }

    public void setAllKilled(boolean allKilled) {
        AllKilled = allKilled;
    }

    @Override
    public void render(GraphicsContext gc) {
        for (int i = 0; i < BombermanGame.stillObjects.size(); i++) {
            if ((BombermanGame.stillObjects.get(i) instanceof Portal)
                    && !(BombermanGame.stillObjects.get(i + 1) instanceof Brick)) {
                ((Portal) BombermanGame.stillObjects.get(i)).setOpen(true);
            }
        }
        for (Entity entity : BombermanGame.entities) {
            if (entity instanceof Enemy) {
                AllKilled = false;
            } else  AllKilled = true;
        }
        for (Entity entity : BombermanGame.entities) {
            if (entity instanceof Bomber) {
                //System.out.println(AllKilled);
                if (entity.intersect(this) && this.isOpen() && this.AllKilled) {
                    BombermanGame.nextMap();
                    System.out.println("Level: " + BombermanGame.currentLevel);
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
