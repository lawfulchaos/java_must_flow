package kerbin;

import asciiPanel.AsciiPanel;

//ИИ игрока, при заходе на свободную клетку подбирает предмет в инвентарь
public class PlayerAi extends CreatureAi {

    public PlayerAi(Creature creature) {
        super(creature);
    }
    public void onEnter(int x, int y, Tile tile) {
        if (tile.isGround()) {
            creature.x = x;
            creature.y = y;
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
                if (priority > Event.getInstance().getPriority())
                    Event.getInstance()
                            .init(String.format("You stepped on %s. %s excuses your clumsiness", c.name, c.name), 2, 3, AsciiPanel.brightWhite);
            }
            }
    }
    public void onTurn(){}
}
