package uet.oop.bomberman.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.audio.Audio;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.movable.Bomber;
import uet.oop.bomberman.entities.movable.Movable;
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

    public void explosiveEffect() {
        for (int i = 0; i < explosions.length; ++i) {
            Explosion[] exp = explosions[i].getExplosions();
            bombCollide(exp, i);
        }
    }
    // TODO: Xét va chạm với bomb
    // Chưa xét item flame pass
    public void bombCollide(Explosion[] exp, int direction) {
        if (exp.length > 0) {
            Explosion expBegin = exp[0];
            Explosion expEnd = exp[exp.length - 1];

            switch (direction) {
                case 0:
                    for (Entity entity : BombermanGame.entities) {
                        Movable movable = (Movable) entity;
                        if (entity.getMaxY() > expEnd.getY() && entity.getMaxY() < expBegin.getMaxY() + Sprite.SCALED_SIZE + 12
                                && (entity.getX() >= expEnd.getX() && entity.getX() < expEnd.getMaxX()
                                || entity.getMaxX() - 18 > expEnd.getX() && entity.getMaxX() - 18 < expEnd.getMaxX())) {
                            if (movable.isAlive()) {
                                movable.kill();
                            }
                        }
                    }
                    break;
                case 1:
                    for (Entity entity : BombermanGame.entities) {
                        Movable movable = (Movable) entity;
                        if (entity.getX() < expEnd.getMaxX() && entity.getX() > expBegin.getX() - Sprite.SCALED_SIZE - 12
                                && (entity.getY() >= expEnd.getY() && entity.getY() < expEnd.getMaxY()
                                || entity.getMaxY() > expEnd.getY() && entity.getMaxY() < expEnd.getMaxY())) {
                            if (movable.isAlive()) {
                                movable.kill();
                            }
                        }
                    }
                    break;
                case 2:
                    for (Entity entity : BombermanGame.entities) {
                        Movable movable = (Movable) entity;
                        if (entity.getY() < expEnd.getMaxY() && entity.getY() > expBegin.getY() - Sprite.SCALED_SIZE - 12
                                && (entity.getX() >= expEnd.getX() && entity.getX() < expEnd.getMaxX()
                                || entity.getMaxX() > expEnd.getX() && entity.getMaxX() < expEnd.getMaxX())) {
                            if (movable.isAlive()) {
                                movable.kill();
                            }
                        }
                    }
                    break;
                case 3:
                    for (Entity entity : BombermanGame.entities) {
                        Movable movable = (Movable) entity;
                        if (entity.getMaxX() > expEnd.getX() && entity.getMaxX() < expBegin.getMaxX() + Sprite.SCALED_SIZE + 12
                                && (entity.getY() >= expEnd.getY() && entity.getY() < expEnd.getMaxY()
                                || entity.getMaxY() >= expEnd.getY() && entity.getMaxY() < expEnd.getMaxY())) {
                            if (movable.isAlive()) {
                                movable.kill();
                            }
                        }
                    }
                    break;
            }
        }
    }

    public void exploded() {
        setExploded(true);
        createExplosion();
        explosiveEffect();
    }

    @Override
    public void render(GraphicsContext gc) {
        if (isExploded) {
            if (frameToDisappear == 12) {
                System.out.println("Bom đã nổ");
                Audio.playSound("res/audio/explosion.wav", 0);
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
