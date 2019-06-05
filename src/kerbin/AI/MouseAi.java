package kerbin.AI;
//ИИ мыши, передвигается на свободную клетку в сторону игрока
import asciiPanel.AsciiPanel;
import kerbin.creatures.Creature;
import kerbin.Event;
import kerbin.world.Tile;

import java.io.Serializable;

public class MouseAi extends CreatureAi implements Serializable {

    public MouseAi(Creature creature) {
            super(creature);
    }
    public void onEnter(int x, int y, Tile tile) {
        if (tile.isGround()) {
            Creature c = creature.getWorld().creature(x, y);
            if (c != null && c != creature && !(c.name.equals("skeleton")) && !(c.name.equals("mouse")) && !(c.name.equals("mob"))) {
                int priority = 2;
                if (priority >= Event.getInstance().getPriority()) {
                    Event.getInstance()
                            .init(String.format("%s attacked by %s", c.name, creature.name), priority, 3, AsciiPanel.brightWhite);
                }
                super.battle(c);
                super.teleport(tile);
            } else {
                creature.x = x;
                creature.y = y;
            }
        }
    }
}
