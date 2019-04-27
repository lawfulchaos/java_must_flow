package kerbin.items;
//Класс брони, наследуется от итема, создается в ItemFactory TODO
import kerbin.Creature;

import java.awt.*;
import java.io.Serializable;

public class Armor extends Item implements Serializable {
    public int def;
    public int startdef;

    public Armor(char glyph, Color color, String name, Creature owner, int def, boolean isEquipable, String desc, int cost)
    {
        super(glyph, color, name, owner, isEquipable, desc, cost);
        this.def = def;
        if (modifier != null) this.def += (Integer) modifier[2]*2;
        this.startdef=this.def;
    }
}
