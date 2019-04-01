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
    //оружие, броня для игрока
    public Weapon newWeapon(Creature owner){
        Weapon weapon = new Weapon('!', AsciiPanel.green, "Sword", owner, 10, true);
        if (owner == null) world.addAtEmptyLocation(weapon);
        return weapon;
    }
    public Armor newArmor(Creature owner){
        Armor armor = new Armor((char)127, AsciiPanel.blue, "Chainmail", owner, 10, true);
        if (owner == null) world.addAtEmptyLocation(armor);
        return armor;
    }
    //Оружие, броня для мобов
    public Weapon newTeeth(Creature owner){
        Weapon weapon = new Weapon('(', AsciiPanel.green, "Teeth", owner, 1, false);
        return weapon;
    }
    public Armor newHide(Creature owner){
        Armor armor = new Armor(')', AsciiPanel.blue, "Hide", owner, 1, false);
        return armor;
    }
    public Usable newHeal(Creature owner){
        Usable heal = new Usable('+', AsciiPanel.cyan, "Heal potion", owner, (int)(5+Math.random()*10), false);
        if (owner == null) world.addAtEmptyLocation(heal);
        return heal;
    }
}
