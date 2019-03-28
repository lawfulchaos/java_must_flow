package kerbin.items;
//Общий Factory для итемов, генерирует в т.ч. классы-наследники
import asciiPanel.AsciiPanel;
import kerbin.Creature;
import kerbin.World;

public class ItemFactory {
    private World world;

    public ItemFactory(World world){
        this.world = world;
    }

    public Weapon newWeapon(Creature owner){
        Weapon weapon = new Weapon('!', AsciiPanel.green, "Sword", owner, 10);
        if (owner == null) world.addAtEmptyLocation(weapon);
        return weapon;
    }
    public Armor newArmor(Creature owner){
        Armor armor = new Armor((char)127, AsciiPanel.blue, "Chainmail", owner, 10);
        if (owner == null) world.addAtEmptyLocation(armor);
        return armor;
    }
}
