package kerbin;

public class PlayerAi extends CreatureAi {

    public PlayerAi(Creature creature) {
        super(creature);
    }
    public void onEnter(int x, int y, Tile tile) {
        if (tile.isGround()) {
            creature.x = x;
            creature.y = y;
            if (tile.item != null)
            {
                creature.inv.add(tile.item);
                tile.item = null;

            }
        }
    }
    public void onTurn(){}
}
