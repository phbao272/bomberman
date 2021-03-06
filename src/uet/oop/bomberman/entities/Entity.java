package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    protected Image img;

    protected boolean removed = false;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity(int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public Entity() {

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getMaxX() {
        return x + Sprite.SCALED_SIZE;
    }

    public int getMaxY() {
        return y + Sprite.SCALED_SIZE;
    }

    public int getTileX() {
        return Coordinates.pixelToTile(x + Sprite.SCALED_SIZE / 2);
    }

    public int getTileY() {
        return Coordinates.pixelToTile(y + Sprite.SCALED_SIZE / 2);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean intersectLeft(Entity other) {
        // TODO: Kiểm tra xem có vật giao nhau bên trái
        boolean impactX = (this.getX() == other.getMaxX());
        boolean intersectY = (other.getY() >= this.getY() && other.getY() < this.getMaxY())
                || (other.getMaxY() > this.getY() && other.getMaxY() <= this.getMaxY());

        return impactX && intersectY;
    }

    public boolean intersectRight(Entity other) {
        // TODO: Kiểm tra xem có vật giao nhau bên phải
        boolean impactX = (this.getMaxX() == other.getX());
        boolean intersectY = (other.getY() >= this.getY() && other.getY() < this.getMaxY())
                || (other.getMaxY() > this.getY() && other.getMaxY() <= this.getMaxY());

        return impactX && intersectY;
    }

    public boolean intersectDown(Entity other) {
        // TODO: Kiểm tra xem có vật giao nhau bên trên
        boolean impactY = (this.getMaxY() == other.getY());
        boolean intersectX = (other.getX() <= this.getX() && other.getMaxX() > this.getX()
                || other.getMaxX() >= this.getMaxX() && other.getX() < this.getMaxX());

        return impactY && intersectX;
    }

    public boolean intersectUp(Entity other) {
        // TODO: Kiểm tra xem có vật giao nhau bên trên
        boolean impactY = (this.getY() == other.getMaxY());
        boolean intersectX = (other.getX() <= this.getX() && other.getMaxX() > this.getX()
                || other.getMaxX() >= this.getMaxX() && other.getX() < this.getMaxX());

        return impactY && intersectX;
    }

    public boolean isCollided(Entity e) {
        if ((e.getMaxX() - 8 <= this.getX()) || (e.getMaxY() - 8 <= this.getY())
                || (this.getMaxX() <= e.getX() + 8) || (this.getMaxY() - 8 <= e.getY())) {
            return false;
        }
        return true;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public abstract void update();

}
