package kerbin.items;
//Класс брони, наследуется от итема, создается в ItemFactory TODO
import kerbin.Creature;

import java.awt.*;

public class Armor extends Item{
    public int def;

    public Armor(char glyph, Color color, String name, Creature owner, int def, boolean isEquipable)
    {
        super(glyph, color, name, owner, isEquipable);
        this.def = def;
    }
}
