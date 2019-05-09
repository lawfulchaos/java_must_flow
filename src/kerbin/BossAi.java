package kerbin;
//ИИ мыши, передвигается случайно на свободную клетку
import asciiPanel.AsciiPanel;
import kerbin.items.ItemFactory;
import kerbin.items.Weapon;

import java.awt.*;
import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

public class BossAi extends MouseAi implements Serializable {

    private int kd_mod = 0;

    public BossAi(Creature creature) {
        super(creature);
    }
    public void onEnter(int x, int y, Tile tile) {
        super.onEnter(x, y, tile);
        if (creature.kd == 0) {
            if (creature.name.equals("Lord Mousarium")) {
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
            else if (creature.name.equals("Reality Bender")) {
                if (creature.hp < creature.max_hp - 20) {
                    int priority = 3;
                    if (priority >= Event.getInstance().getPriority()) {
                        Event.getInstance()
                                .init("Reality Bender executes this.hp = max_hp, replenishing his health", priority, 3, Color.RED);
                    }
                    creature.hp = creature.max_hp;
                    creature.dmg -= 7;
                    if (creature.dmg < 1) creature.dmg = 1;
                    creature.kd = 15 + (15 * kd_mod);
                    kd_mod += 1;
                }
            }
        }
        else creature.kd--;
    }
}
