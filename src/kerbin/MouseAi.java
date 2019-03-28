package kerbin;
//ИИ мыши, передвигается случайно на свободную клетку
import java.util.concurrent.ThreadLocalRandom;

public class MouseAi extends CreatureAi {

    public MouseAi(Creature creature) {
            super(creature);
    }
    public void onEnter(int x, int y, Tile tile) {
        if (tile.isGround()) {
            creature.x = x;
            creature.y = y;
        }
    }
}
