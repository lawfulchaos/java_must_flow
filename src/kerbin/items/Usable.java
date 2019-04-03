package kerbin.items;
//Класс юзабельных вещей (хилка и т.д.), наследуется от итема, создается в ItemFactory TODO
import kerbin.Creature;

import java.awt.*;

public class Usable extends Item{
    public int effect;

    public Usable(char glyph, Color color, String name, Creature owner, int effect, boolean isEquipable, String desc)
    {
        super(glyph, color, name, owner, isEquipable, desc);
        this.effect = effect;
    }
    public void use()
    {
        this.owner().hp += effect;
        this.owner().inv.remove(this);
    }
}
