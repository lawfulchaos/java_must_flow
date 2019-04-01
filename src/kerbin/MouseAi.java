package kerbin;
//ИИ мыши, передвигается случайно на свободную клетку
import asciiPanel.AsciiPanel;

import java.util.concurrent.ThreadLocalRandom;

public class MouseAi extends CreatureAi {

    public MouseAi(Creature creature) {
            super(creature);
    }
    public void onEnter(int x, int y, Tile tile) {
        if (tile.isGround()) {

            //что по поводу айтемов для мышей? пока закомментил их код
        /*if (tile.item != null)
        {
            creature.inv.add(tile.item);
            int priority = 2;
            if (priority >= Event.getInstance().getPriority())
                Event.getInstance().init(String.format("You picked up a %s", tile.item.name()), 2, 6, AsciiPanel.brightWhite);
            tile.item = null;
        }*/

            Creature c = creature.getWorld().creature(x, y);
            if (c != null && c.name != "mouse") {
                int priority = 2;
                if (priority >= Event.getInstance().getPriority()) {
                    Event.getInstance()
                            .init(String.format("%s: You shall not pass! %s: It`s going to be a great battle! \n You`ve been attacked by mouse", c.name, c.name), 2, 1, AsciiPanel.brightWhite);
                }
                //боевка мышб лупит игрока
                c.hp -= creature.dmg - c.def;
                //если умер плеер написали говно пока
                if (creature.hp <= 0) return;
                //если умерла мышб
                if (c.hp <= 0) {
                    creature.getWorld().creatures.remove(creature);
                    Event.getInstance()
                            .init(String.format("Congrats, warrior, you have killed a mouse!"), 2, 3, AsciiPanel.brightWhite);
                }
            } else {
                creature.x = x;
                creature.y = y;
            }
        }
    }
}
