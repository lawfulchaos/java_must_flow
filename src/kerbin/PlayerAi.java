package kerbin;

import asciiPanel.AsciiPanel;

import java.io.Serializable;

//ИИ игрока, при заходе на свободную клетку подбирает предмет в инвентарь
public class PlayerAi extends CreatureAi implements Serializable {

    public PlayerAi(Creature creature) {
        super(creature);
    }
    public void onEnter(int x, int y, Tile tile) {
        if (tile.isGround()) {
            /*Проверяется наличие в тайле итема/животного, совершает с ним действие,
            дальше проверяет приоритет ивента, если текущий важнее - заменяет глобальный.*/
            if (tile.item != null)
            {
                creature.inv.add(tile.item);
                tile.item.owner = creature;
                int priority = 2;
                if (priority >= Event.getInstance().getPriority())
                    if (tile.item.modifier != null)
                        Event.getInstance().init(String.format("You picked up a %s %s", tile.item.modifier[0], tile.item.name()), 2, 6, AsciiPanel.brightWhite);
                    else Event.getInstance().init(String.format("You picked up a %s", tile.item.name()), 2, 6, AsciiPanel.brightWhite);
                tile.item = null;
            }
            Creature c = creature.getWorld().creature(x,y);
            Creature m = creature.getWorld().npc(x,y);
            if (c != null && c.name != "Player")
            {
                int priority = 2;
                if (priority > Event.getInstance().getPriority()) {
                    Event.getInstance()
                            .init(String.format("%s: You shall not pass! %s: It`s going to be a great battle! %s %s", c.name, c.name, creature.dmg, creature.hp), 2, 2, AsciiPanel.brightWhite);
                }
                super.battle(c);
            }
            else if (m != null) {
                int priority = 1;
                if (priority > Event.getInstance().getPriority()) {
                    Event.getInstance()
                            .init(String.format("%s: Buy Something!", m.name), 2, 2, AsciiPanel.brightWhite);
                }
            }
            else{ creature.x=x; creature.y=y;}
            }
        super.teleport(tile);

    }
    //Проверяет наличие эффекта и его длительность
    public void onTurn(Creature player){
        if (creature.effect != null) {
            if (creature.effect[1] == 0) creature.effect = null;
            else creature.hp += creature.effect[0];
            creature.effect[1] -= 1;
        }
    }
}
