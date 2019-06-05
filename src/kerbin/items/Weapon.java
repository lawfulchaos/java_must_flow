package kerbin.items;
//Класс оружия, наследуется от итема, создается в ItemFactory TODO
import kerbin.creatures.Creature;

import java.awt.*;
import java.io.Serializable;

public class Weapon extends Item implements Serializable {
    public int dmg;
    public int durability;

    public Weapon(char glyph, Color color, String name, Creature owner, int dmg, boolean isEquipable, String desc, int cost, int durability)
    {
        super(glyph, color, name, owner, isEquipable, desc, cost);
        this.durability = durability;
        this.dmg = dmg;
        if (modifier != null) {
            this.dmg += (Integer) modifier[2];
            if (name == "Battleaxe" && modifier[0] == "Cursed")
            {
                this.desc = "BLOOD FOR THE BLOOD GOD";
            }
        }
    }
}
