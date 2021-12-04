package uet.oop.bomberman.entities.movable.enemy.ai;

import uet.oop.bomberman.entities.movable.Bomber;
import uet.oop.bomberman.entities.movable.enemy.Enemy;

public class AIMedium extends AI{
    Bomber player;
    Enemy enemy;

    public AIMedium(Bomber player, Enemy e) {
        this.player = player;
        this.enemy = e;
    }

    @Override
    public int calculateDirection() {

        if(player == null)
            return random.nextInt(4);

        int vertical = random.nextInt(2);

        if(vertical == 1) {
            int v = calculateRowDirection();
            if(v != -1)
                return v;
            else
                return calculateColDirection();

        } else {
            int h = calculateColDirection();

            if(h != -1)
                return h;
            else
                return calculateRowDirection();
        }

    }

    protected int calculateColDirection() {
        if(player.getTileX() < enemy.getTileX())
            return 3;
        else if(player.getTileX() > enemy.getTileX())
            return 1;

        return -1;
    }

    protected int calculateRowDirection() {
        if(player.getTileY() < enemy.getTileY())
            return 0;
        else if(player.getTileY() > enemy.getTileY())
            return 2;

        return -1;
    }
}
