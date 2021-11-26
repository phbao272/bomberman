package uet.oop.bomberman.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

public class DirectionalExplosion extends Entity {
    private int direction;
    private int radius;
    private Explosion[] explosions;
    private int cnt = 1;
    public DirectionalExplosion(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public DirectionalExplosion(int x, int y, int direction, int radius) {
        super();
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.radius = radius;
        explosions = new Explosion[calculatePermitedDistance()];
        createExplosions();
    }

    public Explosion[] getExplosions() {
        return explosions;
    }

    // TODO: Tạo ra vụ nổ
    public void createExplosions() {
        boolean finalFlame = false;
        int xUnit = Coordinates.pixelToTile(x);
        int yUnit = Coordinates.pixelToTile(y);

        for (int i = 0; i < explosions.length; i++) {
            finalFlame = (i == explosions.length - 1);

            switch (direction) {
                case 0:
                    yUnit--;
                    break;
                case 1:
                    xUnit++;
                    break;
                case 2:
                    yUnit++;
                    break;
                case 3:
                    xUnit--;
                    break;
            }
            explosions[i] = new Explosion(xUnit, yUnit, direction, finalFlame);
        }

        System.out.println("Tạo ra vụ nổ hướng " + direction + " độ dài là: " + explosions.length);
    }

    // TODO: Tính toán radius có thể của mỗi hướng của vụ nổ
    private int calculatePermitedDistance() {
        int allowRadius = 0;
        int xExp = this.getX();
        int yExp = this.getY();

        while (allowRadius < radius) {
            switch (direction) {
                case 0:
                    yExp -= Sprite.SCALED_SIZE;
                    break;
                case 1:
                    xExp += Sprite.SCALED_SIZE;
                    break;
                case 2:
                    yExp += Sprite.SCALED_SIZE;
                    break;
                case 3:
                    xExp -= Sprite.SCALED_SIZE;
                    break;
            }

            Entity entity = BombermanGame.getEntity(xExp, yExp);
            System.out.println(entity);
            if (entity instanceof Brick) {
                ((Brick) entity).setDestroyed(true);
                allowRadius++;
                break;
            } else if (entity instanceof Wall) {
                break;
            }
            allowRadius++;
        }
        return allowRadius;
    }

    @Override
    public void render(GraphicsContext gc) {
        for (int i = 0; i < explosions.length; i++) {
            explosions[i].render(gc);
        }
    }

    @Override
    public void update() {

    }
}
