package kerbin.AI;
// Поведение существа, создается в CreatureFactory

import asciiPanel.AsciiPanel;
import kerbin.Event;
import kerbin.creatures.Creature;
import kerbin.world.Tile;

import java.awt.*;
import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

public class CreatureAi implements Serializable {
    protected Creature creature;

    CreatureAi(Creature creature) {
        this.creature = creature;
        this.creature.setCreatureAi(this);
    }

    //Реакция на перемещение
    public void onEnter(int x, int y, Tile tile) {
    }

    //Ход существа
    public void onTurn(Creature player) {
        if (Math.pow(Math.pow(creature.x - player.x, 2) + Math.pow(creature.y - player.y, 2), 0.5) <= creature.radius) {
            int mx, my;
            boolean isMoved = false;
            if (creature.x - player.x > 0 && creature.y - player.y > 0) { //мышь справа сверху от игрока
                mx = -1;
                my = -1;
                isMoved = isMoved(mx, my);
            } else if (creature.x - player.x < 0 && creature.y - player.y < 0) { //мышь слева снизу от игрока
                mx = 1;
                my = 1;
                isMoved = isMoved(mx, my);
            } else if (creature.x - player.x > 0 && creature.y - player.y < 0) { //мышь справа снизу от игрока
                mx = -1;
                my = 1;
                isMoved = isMoved(mx, my);
            } else if (creature.x - player.x < 0 && creature.y - player.y > 0) { //мышь слева сверху от игрока
                mx = 1;
                my = -1;
                isMoved = isMoved(mx, my);
            } else if (creature.x - player.x == 0 && creature.y - player.y > 0) { //мышь ровно сверху от игрока
                mx = 0;
                my = -1;
                isMoved = isMoved(mx, my);
            } else if (creature.x - player.x == 0 && creature.y - player.y < 0) { //мышь ровно снизу от игрока
                mx = 0;
                my = 1;
                isMoved = isMoved(mx, my);
            } else if (creature.x - player.x > 0 && creature.y - player.y == 0) { //мышь ровно справа от игрока
                mx = -1;
                my = 0;
                isMoved = isMoved(mx, my);
            } else if (creature.x - player.x < 0 && creature.y - player.y == 0) { //мышь ровно слева от игрока
                mx = 1;
                my = 0;
                isMoved = isMoved(mx, my);
            }
            if (!isMoved) {
                moveRandom();
            }
        } else {
            moveRandom();
        }
    }

    void moveRandom() {
        int mx;
        int my;
        mx = ThreadLocalRandom.current().nextInt(-1, 2);
        my = ThreadLocalRandom.current().nextInt(-1, 2);
        while (!creature.getWorld().tile(creature.x + mx, creature.y + my).isGround()) {
            mx = ThreadLocalRandom.current().nextInt(-1, 2);
            my = ThreadLocalRandom.current().nextInt(-1, 2);
        }
        this.creature.moveBy(mx, my);
    }

    private boolean isMoved(int mx, int my) {
        boolean isMoved = false;
        if (creature.getWorld().tile(creature.x + mx, creature.y + my).isGround()) {
            this.creature.moveBy(mx, my);
            isMoved = true;
        }
        return isMoved;
    }

    void battle(Creature c) {
        int damage = creature.dmg;
        checkCursed(creature);
        checkCursed(c);
        //боевка атакующий лупит аутиста
        if (c.def <= damage) {
            damage -= c.def;
            c.def = 0;
            c.armor = null;
            c.hp -= damage - c.def;
        } else {
            c.armor.def -= damage;
            c.def -= damage;
        }
        damage = c.dmg;
        if (creature.def <= damage) {
            damage -= creature.def;
            creature.def = 0;
            creature.armor = null;
            creature.hp -= damage - creature.def;
        } else {
            creature.def -= damage;
            creature.armor.def -= damage;
        }
        //если умер аутист
        if (c.hp <= 0) {
            onKill(creature, c);
        }
        if (creature.hp <= 0 && !creature.name.equals("player")) {
            onKill(c, creature);
        }
        if (creature.weapon != null) {
            if (creature.weapon.durability != 0) creature.weapon.durability--;
            else {
                int priority = 4;
                if (priority >= kerbin.Event.getInstance().getPriority()) {
                    if (creature.weapon.modifier == null) kerbin.Event.getInstance()
                            .init(String.format("Your %s is broken", creature.weapon.name()), priority, 3, new Color(255, 88, 0));
                    else {
                        kerbin.Event.getInstance()
                                .init(String.format("Your %s %s is broken", creature.weapon.modifier[0], creature.weapon.name()), priority, 3, new Color(255, 88, 0));
                    }
                }
                creature.dmg = 2 + creature.player_level;
                creature.weapon = null;
            }
        }
        if (c.weapon != null) {
            if (c.weapon.durability != 0) c.weapon.durability--;
            else {
                int priority = 4;
                if (priority >= kerbin.Event.getInstance().getPriority()) {
                    if (c.weapon.modifier == null) kerbin.Event.getInstance()
                            .init(String.format("Your %s is broken", c.weapon.name()), priority, 3, new Color(255, 88, 0));
                    else {
                        Event.getInstance()
                                .init(String.format("Your %s %s is broken", c.weapon.modifier[0], c.weapon.name()), priority, 3, new Color(255, 88, 0));
                    }
                }
                c.dmg -= c.weapon.dmg;
                c.weapon = null;
            }
        }
    }

    private void checkCursed(Creature creature) {
        if (creature.weapon != null && creature.weapon.modifier != null && creature.weapon.modifier[0] != null && creature.weapon.modifier[0].equals("Cursed")) {
            creature.hp -= creature.dmg;
        }
    }

    private void onKill(Creature c, Creature creature) {
        creature.getWorld().creatures.remove(creature);
        if (creature.inv.size() != 0) creature.getWorld().tile(creature.x, creature.y).item = creature.inv.get(0);

        switch (creature.name) {
            case ("mouse"):
                c.honor = c.honor + 15;
                break;

            case ("skeleton"):
                c.honor = c.honor + 20;
                break;

            case ("mob"):
                c.honor = c.honor + 10;
                break;


        }

        if (c.honor >= c.max_honor) {
            levelUp(c);
        }

        Event.getInstance()
                .init(String.format("A %s was killed!", creature.name), 2, 3, AsciiPanel.brightWhite);
    }

    private void levelUp(Creature c) {
        c.player_level++;
        c.max_hp = c.max_hp + 15;
        c.hp = c.max_hp;
        c.honor = 0;
        c.dmg++;
        c.gold += 100;
        c.max_honor = c.max_honor * 2;
    }

    void teleport(Tile tile) {
        if (tile.glyph() == 'O') {
            creature.getWorld().addAtEmptyLocation(creature);
        }
    }
}
