package kerbin.AI;
//ИИ мыши, передвигается случайно на свободную клетку

import kerbin.Event;
import kerbin.creatures.Creature;
import kerbin.creatures.CreatureFactory;
import kerbin.world.Tile;

import java.awt.*;
import java.io.Serializable;

public class BossAi extends MouseAi implements Serializable {

    private int kd_mod = 0;
    private boolean reducedDmg = false;

    public BossAi(Creature creature) {
        super(creature);
    }

    public void onEnter(int x, int y, Tile tile) {
        super.onEnter(x, y, tile);
        if (creature.kd == 0) {
            switch (creature.name) {
                case "Lord Mousarium": {
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
                    break;
                }
                case "Reality Hacker":
                    if (creature.hp < creature.max_hp - 20) {
                        int priority = 3;
                        if (priority >= Event.getInstance().getPriority()) {
                            Event.getInstance()
                                    .init("Reality Hacker executes this.hp = max_hp, replenishing his health", priority, 3, Color.RED);
                        }
                        creature.hp = creature.max_hp;
                        creature.dmg -= 7;
                        if (creature.dmg < 1) creature.dmg = 1;
                        creature.kd = 15 + (15 * kd_mod);
                        kd_mod += 1;
                    }
                    break;
                case "Father of skeletons": {
                    int priority = 3;
                    if (priority >= Event.getInstance().getPriority()) {
                        Event.getInstance()
                                .init("Father of skeletons glitches out of reality, shooting out of nowhere", priority, 3, Color.RED);
                    }
                    boolean flag = true;
                    for (int mx = -1; mx <= 1; mx++) {
                        for (int my = -1; my <= 1; my++) {
                            if (creature.getWorld().tile(creature.getWorld().player.x + (mx * 4), creature.getWorld().player.y + (my * 4)).isGround() && creature.getWorld().creature(creature.getWorld().player.x + (mx * 4), creature.getWorld().player.y + (my * 4)) == null) {
                                creature.getWorld().projectileFactory.newBullet(this.creature, creature.getWorld().player.x + (mx * 4), creature.getWorld().player.y + (my * 4), -mx, -my, -mx, -my, 20);
                                if (flag) {
                                    creature.x = creature.getWorld().player.x + (mx * 4);
                                    creature.y = creature.getWorld().player.y + (my * 4);
                                    flag = false;
                                }
                            }
                        }
                    }
                    creature.kd = 8;
                    break;
                }
                case "Hikkihino":
                    int priority = 3;
                    if (!reducedDmg) {
                        if (priority >= Event.getInstance().getPriority()) {
                            Event.getInstance()
                                    .init("Hikkihino cyberbullies you, reducing damage", priority, 3, Color.RED);
                        }
                        creature.getWorld().player.dmg /= 2;
                        reducedDmg = true;
                        creature.kd = 3;
                    } else {
                        reducedDmg = false;
                        creature.getWorld().player.dmg = 3 + creature.getWorld().player.player_level;
                        if (creature.getWorld().player.weapon != null)
                            creature.getWorld().player.dmg += creature.getWorld().player.weapon.dmg;
                        creature.kd = 12;
                    }
                    break;
            }
        } else creature.kd--;
    }
}
