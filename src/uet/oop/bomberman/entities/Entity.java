package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {
    private int step = 0;
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    protected Image img;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity(int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public void moveRight() {
        if (this.x / Sprite.SCALED_SIZE < BombermanGame.WIDTH - 2) {
            this.x = this.x + 5;
        }
        step++;
        switch (step % 5) {
            case 0:
                setImg(Sprite.player_right_1.getFxImage());
                break;
            case 3:
                setImg(Sprite.player_right_2.getFxImage());
                break;
        }
        System.out.println("Right: " + x / Sprite.SCALED_SIZE);
    }

    public void moveLeft() {
        if (this.x / Sprite.SCALED_SIZE > 1) {
            this.x = this.x - 5;
            setImg(Sprite.player_left.getFxImage());
        }

        step++;
        switch (step % 10) {
            case 0:
                setImg(Sprite.player_left_1.getFxImage());
                break;
            case 5:
                setImg(Sprite.player_left_2.getFxImage());
                break;
        }

        System.out.println("Left: " + x / Sprite.SCALED_SIZE);
    }

    public void moveUp() {
        if (this.y > 32) {
            this.y = this.y - 5;
        }
        System.out.println("Up: " + y / Sprite.SCALED_SIZE);
    }

    public void moveDown() {
        if (this.y < 352) {
            this.y = this.y + 5;
        }
        System.out.println("Down: " + y / Sprite.SCALED_SIZE);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public abstract void update();
}
