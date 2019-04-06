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
    public Weapon newSword(Creature owner){
        Weapon weapon = new Weapon('!', AsciiPanel.green, "Sword", owner, 4, true, "Stick them with the pointy end");
        if (owner == null) world.addAtEmptyLocation(weapon);
        return weapon;
    }
    public Weapon newBattleaxe(Creature owner){
        Weapon weapon = new Weapon('%', AsciiPanel.green, "Battleaxe", owner, 6, true, "It's heavy, it's dangerous, it will do");
        if (owner == null) world.addAtEmptyLocation(weapon);
        return weapon;
    }
    public Armor newMail(Creature owner){
        Armor armor = new Armor((char)127, AsciiPanel.blue, "Chainmail", owner, 10, true, "Basic defence, and nothing more");
        if (owner == null) world.addAtEmptyLocation(armor);
        return armor;
    }
    public Armor newPlate(Creature owner){
        Armor armor = new Armor('i', AsciiPanel.blue, "Plate armor", owner, 15, true, "Looks sturdy enough");
        if (owner == null) world.addAtEmptyLocation(armor);
        return armor;
    }


    //Оружие, броня для мобов
    public Weapon newTeeth(Creature owner){
        Weapon weapon = new Weapon('(', AsciiPanel.green, "Teeth", owner, 1, false, "A handful of monster teeth");
        return weapon;
    }
    public Armor newHide(Creature owner){
        Armor armor = new Armor(')', AsciiPanel.blue, "Hide", owner, 1, false, "Mouse hide, almost useless");
        return armor;
    }


    //Зелья и иже с ними
    public Usable newHeal(Creature owner){
        Usable heal = new Usable('+', AsciiPanel.cyan, "Heal potion", owner, 20, false, "Glows red");
        if (owner == null) world.addAtEmptyLocation(heal);
        return heal;
    }
    public Usable newTeleport(Creature owner){
        Usable heal = new Usable(')', AsciiPanel.cyan, "Teleport potion", owner, -15, false, "Glows white. It will get you away, for a price");
        if (owner == null) world.addAtEmptyLocation(heal);
        return heal;
    }
}
