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
        if (modifier != null) {
            this.dmg += (Integer) modifier[2];
            if (name == "Battleaxe" && modifier[0] == "Cursed")
            {
                this.desc = "BLOOD FOR THE BLOOD GOD";
                this.dmg += Math.abs((Integer) modifier[2] * 2);
            }
        }
    }
}
