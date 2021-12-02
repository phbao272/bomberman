package uet.oop.bomberman.entities.PowerUps;

import javafx.scene.image.Image;
import uet.oop.bomberman.audio.Audio;
import uet.oop.bomberman.entities.Entity;

public abstract class PowerUp extends Entity {
    public Audio myAudio = new Audio();
    private  boolean Active = false;
    private boolean isCollected = false;

    public PowerUp(int x, int y, Image img) {
        super(x, y, img);
    }

    public void setCollected(boolean collected) {
        this.isCollected = collected;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }

}
