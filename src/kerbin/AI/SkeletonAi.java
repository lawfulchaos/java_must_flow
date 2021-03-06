package kerbin.AI;
//ИИ скелета

import asciiPanel.AsciiPanel;
import kerbin.Event;
import kerbin.creatures.Creature;
import kerbin.world.Tile;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

public class SkeletonAi extends CreatureAi implements Serializable {
    public SkeletonAi(Creature creature) {
        super(creature);
    }

    private boolean CheckVisibility(int mx, int my) {
        boolean isMoved = false;
        if (creature.getWorld().tile(creature.x + mx, creature.y + my).isGround()) {
            this.creature.moveBy(mx, my);
            isMoved = true;
            boolean isVisible = true;
            for (int i = 1; i <= 5; i++) {
                if (creature.getWorld().tile(creature.x + i * mx, creature.y + i * my).isGround() && isVisible) {
                } else {
                    isVisible = false;
                }
            }
            if (isVisible && creature.kd == 0) {
                creature.getWorld().projectileFactory.newBullet(creature, creature.x + mx, creature.y + my, mx, my, mx, my, 1 + (int) (Math.random() * 10));
                creature.kd = 3;
            }
        }
        return isMoved;
    }

    public void onTurn(Creature player) {
        if (Math.pow(Math.pow(creature.x - player.x, 2) + Math.pow(creature.y - player.y, 2), 0.5) > creature.radius || Math.pow(Math.pow(creature.x - player.x, 2) + Math.pow(creature.y - player.y, 2), 0.5) < 3) {
            int mx = ThreadLocalRandom.current().nextInt(-1, 2);
            int my = ThreadLocalRandom.current().nextInt(-1, 2);
            this.creature.moveBy(mx, my);
        } else {
            int mx, my;
            boolean isMoved = false;
            if (creature.x - player.x > 0 && creature.y - player.y > 0) { //мышь справа сверху от игрока
                mx = -1;
                my = -1;
                isMoved = CheckVisibility(mx, my);
            }

            if (creature.x - player.x < 0 && creature.y - player.y < 0 && !isMoved) { //мышь слева снизу от игрока
                mx = 1;
                my = 1;
                isMoved = CheckVisibility(mx, my);
            }

            if (creature.x - player.x > 0 && creature.y - player.y < 0 && !isMoved) { //мышь справа снизу от игрока
                mx = -1;
                my = 1;
                isMoved = CheckVisibility(mx, my);
            }

            if (creature.x - player.x < 0 && creature.y - player.y > 0 && !isMoved) { //мышь слева сверху от игрока
                mx = 1;
                my = -1;
                isMoved = CheckVisibility(mx, my);
            }
            if (creature.x - player.x == 0 && creature.y - player.y > 0 && !isMoved) { //мышь ровно сверху от игрока
                mx = 0;
                my = -1;
                isMoved = CheckVisibility(mx, my);
            }
            if (creature.x - player.x == 0 && creature.y - player.y < 0 && !isMoved) { //мышь ровно снизу от игрока
                mx = 0;
                my = 1;
                isMoved = CheckVisibility(mx, my);
            }
            if (creature.x - player.x > 0 && creature.y - player.y == 0 && !isMoved) { //мышь ровно справа от игрока
                mx = -1;
                my = 0;
                isMoved = CheckVisibility(mx, my);
            }
            if (creature.x - player.x < 0 && creature.y - player.y == 0 && !isMoved) { //мышь ровно слева от игрока
                mx = 1;
                my = 0;
                isMoved = CheckVisibility(mx, my);
            }
            if (!isMoved) {
                moveRandom();
            }
        }
        if (creature.kd > 0) creature.kd--;
    }

    public void onEnter(int x, int y, Tile tile) {
        if (tile.isGround()) {
            Creature c = creature.getWorld().creature(x, y);
            if (c != null && !(c.name.equals("skeleton")) && !(c.name.equals("mouse")) && !(c.name.equals("mob"))) {
                int priority = 2;
                if (priority >= Event.getInstance().getPriority()) {
                    Event.getInstance()
                            .init(String.format("%s: You`ve been attacked by skeleton", c.name), priority, 3, AsciiPanel.brightWhite);
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