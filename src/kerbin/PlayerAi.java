package kerbin;

import asciiPanel.AsciiPanel;

//ИИ игрока, при заходе на свободную клетку подбирает предмет в инвентарь
public class PlayerAi extends CreatureAi {

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
                int priority = 2;
                if (priority >= Event.getInstance().getPriority())
                    Event.getInstance().init(String.format("You picked up a %s", tile.item.name()), 2, 6, AsciiPanel.brightWhite);
                tile.item = null;
            }
            Creature c = creature.getWorld().creature(x,y);
            if (c != null && c.name != "Player")
            {
                int priority = 2;
                if (priority >= Event.getInstance().getPriority()) {
                    Event.getInstance()
                            .init(String.format("%s: You shall not pass! %s: It`s going to be a great battle!", c.name, c.name), 2, 1, AsciiPanel.brightWhite);
                }
                //боевка игрок лупит мышб
                c.hp-=creature.dmg-c.def;
                //если умер плеер написали говно пока
                if (creature.hp<=0) return;
                //если умерла мышб
                if (c.hp<=0) {
                    creature.getWorld().creatures.remove(c);
                    Event.getInstance()
                            .init(String.format("Congrats, warrior, you have killed a mouse!"), 2, 1, AsciiPanel.brightWhite);
                }
            }
            else{ creature.x=x; creature.y=y;}
            }
    }
    public void onTurn(){}
}
