package kerbin;
//ИИ мыши, передвигается случайно на свободную клетку
import asciiPanel.AsciiPanel;

import java.util.concurrent.ThreadLocalRandom;

public class SkeletonAi extends CreatureAi {

    public SkeletonAi(Creature creature) {
        super(creature);
    }
    public void onEnter(int x, int y, Tile tile) {
        if (tile.isGround()) {
            Creature c = creature.getWorld().creature(x, y);
            if (c != null && c.name != "skeleton" && c.name != "mouse") {
                int priority = 2;
                if (priority >= Event.getInstance().getPriority()) {
                    Event.getInstance()
                            .init(String.format("%s: You`ve been attacked by skeleton", c.name), priority, 3, AsciiPanel.brightWhite);
                }
                super.battle(c);
            } else {
                creature.x = x;
                creature.y = y;
            }
        }
    }
}