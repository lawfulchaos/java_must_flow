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
        if (modifier != null)
        {
            this.effect += (Integer) modifier[2] * 3;
            if (modifier[0] == "Cursed") this.desc = "Glows viciously" + " " + desc.split(" ")[1];
        }
    }
    //Использование предмета. Возвращает сообщение
    public String use() {
        if (name() == "Heal potion") {
            this.owner().hp += effect;
            if (owner().hp > 100) owner().hp = 100;
            this.owner().inv.remove(this);
            return String.format("You used a %s, now you have %d HP", name(), owner.hp);
        }
        else if (name() == "Teleport potion")
        {
            owner.hp += effect;
            if (owner().hp > 100) owner().hp = 100;
            owner.getWorld().addAtEmptyLocation(owner);
            this.owner().inv.remove(this);
            return String.format("You used a %s, teleporting away, now you have %d HP", name(), owner.hp);
        }
        return "You use unknown magic";
    }
}
