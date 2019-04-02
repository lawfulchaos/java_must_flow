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
                tile.item.owner = creature;
                int priority = 2;
                if (priority >= Event.getInstance().getPriority())
                    Event.getInstance().init(String.format("You picked up a %s", tile.item.name()), 2, 6, AsciiPanel.brightWhite);
                tile.item = null;
            }
            Creature c = creature.getWorld().creature(x,y);
            if (c != null && c.name != "Player")
            {
                int priority = 2;
                if (priority > Event.getInstance().getPriority()) {
                    Event.getInstance()
                            .init(String.format("%s: You shall not pass! %s: It`s going to be a great battle! %s %s", c.name, c.name, creature.dmg, creature.hp), 2, 2, AsciiPanel.brightWhite);
                }
                super.battle(c);
            }
            else{ creature.x=x; creature.y=y;}
            }

        if(tile.glyph() == 'O'){
            creature.getWorld().addAtEmptyLocation(creature);
        }
    }
    public void onTurn(){}
}
