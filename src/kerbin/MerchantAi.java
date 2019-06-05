package kerbin;
//ИИ торговка, передвигается случайно на свободную клетку в 50% случаев
import java.io.Serializable;

public class MerchantAi extends CreatureAi implements Serializable {

    public MerchantAi(Creature creature) {
        super(creature);
    }
    public void onEnter(int x, int y, Tile tile) {
        if (tile.isGround() && Math.random() > 0.5) {
            Creature c = creature.getWorld().creature(x, y);
            if (c == null) {
                creature.x = x;
                creature.y = y;
            }
        }
    }
}
