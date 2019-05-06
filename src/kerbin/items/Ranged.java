package kerbin.items;
//Класс дальнобойного оружия, наследуется от оружия, создается в ItemFactory
import kerbin.Creature;

import java.awt.*;
import java.io.Serializable;
public class Ranged extends Weapon implements Serializable {
    public Ranged(char glyph, Color color, String name, Creature owner, int dmg, boolean isEquipable, String desc, int cost, int durability)
    {
        super(glyph, color, name, owner, dmg, isEquipable, desc, cost, durability);
        if (modifier != null) {
            this.dmg -= (Integer) modifier[2] * 0.5;
        }
    }
}
