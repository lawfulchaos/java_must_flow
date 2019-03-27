package kerbin.items;

import asciiPanel.AsciiPanel;
import kerbin.Creature;
import kerbin.World;

public class ItemFactory {
    private World world;

    public ItemFactory(World world){
        this.world = world;
    }

    public Item newWeapon(Creature owner){
        Item weapon = new Item('!', AsciiPanel.green, "Sword", owner);
        if (owner == null) world.addAtEmptyLocation(weapon);
        return weapon;
    }
}
