package kerbin.items;
//Класс оружия, наследуется от итема, создается в ItemFactory TODO
import kerbin.Creature;

import java.awt.*;

public class Weapon extends Item{
    public int dmg;

    public Weapon(char glyph, Color color, String name, Creature owner, int dmg, boolean isEquipable, String desc)
    {
        super(glyph, color, name, owner, isEquipable, desc);
        this.dmg = dmg;
    }
}
