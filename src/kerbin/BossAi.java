package kerbin;
//ИИ мыши, передвигается случайно на свободную клетку
import asciiPanel.AsciiPanel;
import kerbin.items.ItemFactory;
import kerbin.items.Weapon;

import java.awt.*;
import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

public class BossAi extends MouseAi implements Serializable {

    public BossAi(Creature creature) {
        super(creature);
    }

  /*  public void onTurn(Creature player)
    {
        super.onTurn(player);
        if (creature.hp <= 0) {
            player.honor += 200;
            creature.getWorld().tile(creature.x, creature.y).item = new Weapon('%', Color.yellow, "Guitar", null, 15, true,
                    "A strange instrument from foreign lands, disturbingly glowing with radiation, deeply beloved by Mouse Lord. You find no use for it, other of macing enemies on your path", 450);

            int priority = 5;
            if (priority >= Event.getInstance().getPriority()) {
                Event.getInstance()
                        .init("MOUSE WAYS BETRAYED ME", priority, 3, Color.RED);
            }
        }
    }*/

    public void onEnter(int x, int y, Tile tile) {
        super.onEnter(x, y, tile);
        if (creature.kd == 0) {
            int priority = 3;
            if (priority >= Event.getInstance().getPriority()) {
                Event.getInstance()
                        .init("ARISE, MY MINIONS", priority, 3, Color.RED);
            }
            creature.kd = 14;
            CreatureFactory creatureFactory = new CreatureFactory(creature.getWorld());
            for (int i = 0; i < 3; i++) {
                Creature mouse = creatureFactory.newMouse();
                creature.getWorld().creatures.add(mouse);
            }
        }
        else creature.kd--;
    }
}
