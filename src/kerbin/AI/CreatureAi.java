package kerbin.AI;
// Поведение существа, создается в CreatureFactory
import asciiPanel.AsciiPanel;
import kerbin.creatures.Creature;
import kerbin.Event;
import kerbin.world.Tile;

import java.awt.*;
import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

public class CreatureAi  implements Serializable {
    protected Creature creature;
    public CreatureAi(Creature creature){
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
                if (creature.getWorld().tile(creature.x + mx, creature.y + my).isGround()) {
                    this.creature.moveBy(mx, my);
                    isMoved = true;
                }
            }

            if (creature.x - player.x < 0 && creature.y - player.y < 0 && !isMoved) { //мышь слева снизу от игрока
                mx = 1;
                my = 1;
                if (creature.getWorld().tile(creature.x + mx, creature.y + my).isGround()) {
                    this.creature.moveBy(mx, my);
                    isMoved = true;
                }
            }

            if (creature.x - player.x > 0 && creature.y - player.y < 0 && !isMoved) { //мышь справа снизу от игрока
                mx = -1;
                my = 1;
                if (creature.getWorld().tile(creature.x + mx, creature.y + my).isGround()) {
                    this.creature.moveBy(mx, my);
                    isMoved = true;
                }
            }

            if (creature.x - player.x < 0 && creature.y - player.y > 0 && !isMoved) { //мышь слева сверху от игрока
                mx = 1;
                my = -1;
                if (creature.getWorld().tile(creature.x + mx, creature.y + my).isGround()) {
                    this.creature.moveBy(mx, my);
                    isMoved = true;
                }
            }
            if (creature.x - player.x == 0 && creature.y - player.y > 0 && !isMoved) { //мышь ровно сверху от игрока
                mx = 0;
                my = -1;
                if (creature.getWorld().tile(creature.x + mx, creature.y + my).isGround()) {
                    this.creature.moveBy(mx, my);
                    isMoved = true;
                }
            }
            if (creature.x - player.x == 0 && creature.y - player.y < 0 && !isMoved) { //мышь ровно снизу от игрока
                mx = 0;
                my = 1;
                if (creature.getWorld().tile(creature.x + mx, creature.y + my).isGround()) {
                    this.creature.moveBy(mx, my);
                    isMoved = true;
                }
            }
            if (creature.x - player.x > 0 && creature.y - player.y == 0 && !isMoved) { //мышь ровно справа от игрока
                mx = -1;
                my = 0;
                if (creature.getWorld().tile(creature.x + mx, creature.y + my).isGround()) {
                    this.creature.moveBy(mx, my);
                    isMoved = true;
                }
            }
            if (creature.x - player.x < 0 && creature.y - player.y == 0 && !isMoved) { //мышь ровно слева от игрока
                mx = 1;
                my = 0;
                if (creature.getWorld().tile(creature.x + mx, creature.y + my).isGround()) {
                    this.creature.moveBy(mx, my);
                    isMoved = true;
                }
            }
            if (!isMoved) {
                mx = ThreadLocalRandom.current().nextInt(-1, 2);
                my = ThreadLocalRandom.current().nextInt(-1, 2);
                while (!creature.getWorld().tile(creature.x + mx, creature.y + my).isGround()) {
                    mx = ThreadLocalRandom.current().nextInt(-1, 2);
                    my = ThreadLocalRandom.current().nextInt(-1, 2);
                }
                this.creature.moveBy(mx, my);
            }
        }
        else {
            int mx,my;
            mx = ThreadLocalRandom.current().nextInt(-1, 2);
            my = ThreadLocalRandom.current().nextInt(-1, 2);
            while (!creature.getWorld().tile(creature.x + mx, creature.y + my).isGround()) {
                mx = ThreadLocalRandom.current().nextInt(-1, 2);
                my = ThreadLocalRandom.current().nextInt(-1, 2);
            }
            this.creature.moveBy(mx, my);
        }
    }

    public void battle(Creature c){
        int damage = creature.dmg;
        if (creature.weapon != null && creature.weapon.modifier != null && creature.weapon.modifier[0] != null && creature.weapon.modifier[0].equals("Cursed")) { creature.hp -= creature.dmg; }
        if (c.weapon != null && c.weapon.modifier != null && c.weapon.modifier[0] != null && c.weapon.modifier[0].equals("Cursed")) { c.hp -= c.dmg; }
        //боевка атакующий лупит аутиста
        if(c.def<=damage){
            damage-=c.def;
            c.def=0;
            c.armor = null;
            c.hp -= damage - c.def;
        }
        else{
            int x=damage;
            c.armor.def-=x;
            damage=0;
            c.def-=x;
        }
        damage=c.dmg;
        if(creature.def<=damage){
            damage-=creature.def;
            creature.def=0;
            creature.armor = null;
            creature.hp-=damage - creature.def;
        }
        else{
            int x=damage;
            damage=0;
            creature.def-=x;
            creature.armor.def-=x;
        }
        //если умер аутист
        if (c.hp <= 0) {
            creature.getWorld().creatures.remove(c);
            if (c.inv.size()!=0)creature.getWorld().tile(c.x, c.y).item = c.inv.get(0);
            switch (c.name){
                case("mouse"):
                    creature.honor = creature.honor + 15;
                    break;

                case("skeleton"):
                    creature.honor = creature.honor + 20;
                    break;

                case("mob"):
                    creature.honor = creature.honor + 10;
                    break;


            }

            if(creature.honor >= creature.max_honor){
                creature.player_level++;
                creature.max_hp=creature.max_hp+15;
                creature.hp=creature.max_hp;
                creature.honor=0;
                creature.dmg++;
                creature.gold+=100;
                creature.max_honor= creature.max_honor*2;
            }

            kerbin.Event.getInstance()
                    .init(String.format("A %s was killed!",c.name), 2, 3, AsciiPanel.brightWhite);
        }
        if (creature.hp <= 0&& creature.name!="player") {
            creature.getWorld().creatures.remove(creature);
            if (creature.inv.size() != 0)creature.getWorld().tile(creature.x, creature.y).item = creature.inv.get(0);

            switch (creature.name){
                case("mouse"):
                    c.honor = c.honor + 15;
                    break;

                case("skeleton"):
                    c.honor = c.honor + 20;
                    break;

                case("mob"):
                    c.honor = c.honor + 10;
                    break;


            }

            if(c.honor >= c.max_honor){
                c.player_level++;
                c.max_hp=c.max_hp+15;
                c.hp=c.max_hp;
                c.honor=0;
                c.dmg++;
                c.gold+=100;
                c.max_honor= c.max_honor*2;
            }

            kerbin.Event.getInstance()
                    .init(String.format("A %s was killed!",creature.name), 2, 3, AsciiPanel.brightWhite);
        }
        if (creature.weapon != null)
        {
            if (creature.weapon.durability != 0) creature.weapon.durability--;
            else
            {
                int priority = 4;
                if (priority >= kerbin.Event.getInstance().getPriority()) {
                    if (creature.weapon.modifier == null) kerbin.Event.getInstance()
                            .init(String.format("Your %s is broken", creature.weapon.name()), priority, 3, new Color(255, 88, 0));
                    else
                    {
                        kerbin.Event.getInstance()
                                .init(String.format("Your %s %s is broken", creature.weapon.modifier[0], creature.weapon.name()), priority, 3, new Color(255, 88, 0));
                    }
                }
                creature.dmg = 2 + creature.player_level;
                creature.weapon = null;
            }
        }
        if (c.weapon != null)
        {
            if (c.weapon.durability != 0) c.weapon.durability--;
            else
            {
                int priority = 4;
                if (priority >= kerbin.Event.getInstance().getPriority()) {
                    if (c.weapon.modifier == null) kerbin.Event.getInstance()
                            .init(String.format("Your %s is broken", c.weapon.name()), priority, 3, new Color(255, 88, 0));
                    else
                    {
                        Event.getInstance()
                                .init(String.format("Your %s %s is broken", c.weapon.modifier[0], c.weapon.name()), priority, 3, new Color(255, 88, 0));
                    }
                }
                c.dmg -= c.weapon.dmg;
                c.weapon = null;
            }
        }
    }

    public void teleport(Tile tile){
        if(tile.glyph() == 'O'){
            creature.getWorld().addAtEmptyLocation(creature);
        }
    }
}
