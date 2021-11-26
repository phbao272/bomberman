package uet.oop.bomberman.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.audio.Audio;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Entity {
    private int timeToExplode = 120; // 200 / 60 (s)
    private int frameToDisappear = 12;
    private boolean isExploded = false;
    private DirectionalExplosion[] explosions = null;

    public void setExploded(boolean exploded) {
        isExploded = exploded;
    }

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public void renderExplosions(GraphicsContext gc) {
        for (int i = 0; i < explosions.length; i++) {
            explosions[i].render(gc);
        }
    }

    public void createExplosion() {
        explosions = new DirectionalExplosion[4];

        for (int i = 0; i < 4; i++) {
            explosions[i] = new DirectionalExplosion(x, y, i, BombermanGame.bombRadius);
        }
    }

    public void exploded() {
        setExploded(true);
        createExplosion();
    }

    @Override
    public void render(GraphicsContext gc) {
        if (isExploded) {
            if (frameToDisappear == 12) {
                System.out.println("Bom đã nổ");
                Audio.playSound("res/audio/explosion.wav");
            }

            img = Sprite.bomb_exploded2.getFxImage();
            if (frameToDisappear > 0) {
                frameToDisappear--;
                renderExplosions(gc);
            } else {
                BombermanGame.listBombs.remove(this);
            }
        }
        gc.drawImage(img, x, y);
    }

    @Override
    public void update() {
        // TODO: Bom chuẩn bị nổ
        if (timeToExplode > 0) {
            switch (timeToExplode % 60) {
                case 0: {
                    System.out.println("Bom chuẩn bị nổ ...");
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
            if (!isExploded) {
                exploded();
            }
        }
    }
}
