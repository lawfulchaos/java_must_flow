package kerbin;
// Поведение существа, создается в CreatureFactory
import asciiPanel.AsciiPanel;

import java.util.concurrent.ThreadLocalRandom;

public class CreatureAi {
    protected Creature creature;
    public CreatureAi(Creature creature){
        this.creature = creature;
        this.creature.setCreatureAi(this);
    }
//Реакция на перемещение
    public void onEnter(int x, int y, Tile tile) {
        if (tile.isGround()) {
            creature.x = x;
            creature.y = y;
        }
    }
//Ход существа
    public void onTurn() {
        int mx = ThreadLocalRandom.current().nextInt(-1, 2);
        int my = ThreadLocalRandom.current().nextInt(-1, 2);
        this.creature.moveBy(mx, my);
    }

    public void battle(Creature c){
        int damage = creature.dmg;
        //боевка атакующий лупит аутиста
        if(c.def<=damage){
            damage-=c.def;
            c.def=0;
        }
        else{
            int x=damage;
            damage-=c.def;
            c.def-=x;
        }
        c.hp -= damage - c.def;
        damage=c.dmg;
        if(creature.def<=damage){
            damage-=creature.def;
            creature.def=0;
        }
        else{
            int x=damage;
            damage-=creature.def;
            creature.def-=x;
        }
        creature.hp-=c.dmg - creature.def;
        //если умер аутист
        if (c.hp <= 0) {
            creature.getWorld().creatures.remove(c);
            Event.getInstance()
                    .init(String.format("Congrats, warrior, you have killed a mouse! %s %s",creature.dmg, creature.hp), 2, 3, AsciiPanel.brightWhite);
        }
    }
}
