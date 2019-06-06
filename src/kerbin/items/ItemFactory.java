package kerbin.items;
//Общий Factory для итемов, генерирует в т.ч. классы-наследники

import asciiPanel.AsciiPanel;
import kerbin.creatures.Creature;
import kerbin.world.World;

import java.io.Serializable;

public class ItemFactory implements Serializable {
    private World world;

    public ItemFactory(World world) {
        this.world = world;
    }

    //Случайный предмет
    public Item newRandom(Creature owner) {
        int choice = (int) (Math.random() * 7);
        switch (choice) {
            case 0:
                return newLootbox(owner);
            case 1:
                return newSword(owner);
            case 2:
                return newBattleaxe(owner);
            case 3:
                return newMail(owner);
            case 4:
                return newPlate(owner);
            case 5:
                return newHeal(owner);
            case 6:
                return newTeleport(owner);
            case 7:
                return newBow(owner);
        }
        return null;
    }

    //оружие, броня для игрока
    public Weapon newSword(Creature owner) {
        Weapon weapon = new Weapon('!', AsciiPanel.green, "Sword", owner, 4, true, "Stick them with the pointy end", 100, 6);
        if (owner == null) world.addAtEmptyLocation(weapon);
        return weapon;
    }

    public Weapon newBattleaxe(Creature owner) {
        Weapon weapon = new Weapon('%', AsciiPanel.green, "Battleaxe", owner, 6, true, "It's heavy, it's dangerous, it will do", 150, 6);
        if (owner == null) world.addAtEmptyLocation(weapon);
        return weapon;
    }

    public Ranged newBow(Creature owner) {
        Ranged bow = new Ranged('}', AsciiPanel.brightMagenta, "Bow", owner, 4, true, "A stick with a string to dominate over distance", 150, 6);
        if (owner == null) world.addAtEmptyLocation(bow);
        return bow;
    }

    public Armor newMail(Creature owner) {
        Armor armor = new Armor((char) 127, AsciiPanel.blue, "Chainmail", owner, 10, true, "Basic defence, and nothing more", 50);
        if (owner == null) world.addAtEmptyLocation(armor);
        return armor;
    }

    public Armor newPlate(Creature owner) {
        Armor armor = new Armor('i', AsciiPanel.blue, "Plate armor", owner, 15, true, "Looks sturdy enough", 70);
        if (owner == null) world.addAtEmptyLocation(armor);
        return armor;
    }


    //Оружие, броня для мобов
    public Weapon newTeeth(Creature owner) {
        return new Weapon('(', AsciiPanel.green, "Teeth", owner, 1, false, "Monster fang, looks sharp enough", 10, 12000);
    }

    public Armor newHide(Creature owner) {
        return new Armor(')', AsciiPanel.blue, "Hide", owner, 1, false, "Mouse hide, almost useless", 10);
    }

    //стрелы
    public Item newArrows(Creature owner) {
        Item arrows = new Item(';', AsciiPanel.cyan, "Bunch of arrows", owner, false, "Bunch of arrows", 30);
        if (owner == null) world.addAtEmptyLocation(arrows);
        return arrows;
    }

    //сюжет
    public void newStory(Creature owner, String name, String disc) {
        Story storyb = new Story((char) 63, AsciiPanel.white, name, owner, false, disc, 30);
        if (owner == null) world.addAtEmptyLocation(storyb);
    }

    //Зелья и иже с ними
    public Usable newHeal(Creature owner) {
        Usable heal = new Usable('+', AsciiPanel.cyan, "Heal potion", owner, 20, false, "Glows red", 60);
        if (owner == null) world.addAtEmptyLocation(heal);
        return heal;
    }

    public Usable newTeleport(Creature owner) {
        Usable heal = new Usable(')', AsciiPanel.cyan, "Teleport", owner, -15, false, "Glows white. It will get you away, for a price", 70);
        if (owner == null) world.addAtEmptyLocation(heal);
        return heal;
    }

    public Usable newLootbox(Creature owner) {
        Usable lootbox = new Usable((char) 155, AsciiPanel.cyan, "Lootbox", null, 0, false, "A box, probably containing brand new skin for your sword, or maybe something more useful. Only for 39.99 $", 40);
        if (owner == null) world.addAtEmptyLocation(lootbox);
        return lootbox;
    }
}
