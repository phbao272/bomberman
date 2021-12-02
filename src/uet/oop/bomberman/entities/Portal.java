package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.movable.Bomber;
import uet.oop.bomberman.entities.movable.enemy.Enemy;
import uet.oop.bomberman.entities.movable.enemy.Minvo;


public class Portal extends Entity {
    private boolean open = false;
    private boolean allKilled = true;

    public Portal(int x, int y, Image img) {
        super(x, y, img);
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isAllKilled() {
        return allKilled;
    }

    public void setAllKilled(boolean allKilled) {
        this.allKilled = allKilled;
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
            if (entity instanceof Enemy && !(entity instanceof Minvo)) {
                allKilled = false;
            } else  allKilled = true;
        }

        for (int i = 0; i < BombermanGame.entities.size(); i++) {
            Entity entity = BombermanGame.entities.get(i);
            if (entity.isCollided(this) && this.isOpen() && this.allKilled) {
                BombermanGame.nextMap();
                System.out.println("Level: " + BombermanGame.currentLevel);
                BombermanGame.stillObjects.remove(this);
            }
        }
        gc.drawImage(img, x, y);
    }

    @Override
    public void update() {

    }
}