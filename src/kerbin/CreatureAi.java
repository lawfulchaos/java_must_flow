package kerbin;

import java.util.concurrent.ThreadLocalRandom;

public class CreatureAi {
    protected Creature creature;
    public CreatureAi(Creature creature){
        this.creature = creature;
        this.creature.setCreatureAi(this);
    }

    public void onEnter(int x, int y, Tile tile) {
        if (tile.isGround()) {
            creature.x = x;
            creature.y = y;
        }
    }
    public void onTurn() {
        int mx = ThreadLocalRandom.current().nextInt(-1, 2);
        int my = ThreadLocalRandom.current().nextInt(-1, 2);
        this.creature.moveBy(mx, my);
    }
}
