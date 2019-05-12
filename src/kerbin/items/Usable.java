package kerbin.items;
//Класс юзабельных вещей (хилка и т.д.), наследуется от итема, создается в ItemFactory TODO
import kerbin.Creature;

import java.awt.*;
import java.io.Serializable;

public class Usable extends Item implements Serializable {
    public int effect;

    public Usable(char glyph, Color color, String name, Creature owner, int effect, boolean isEquipable, String desc, int cost)
    {
        super(glyph, color, name, owner, isEquipable, desc, cost);
        this.effect = effect;
        if (modifier != null)
        {
            this.effect += (Integer) modifier[2] * 3;
            if (modifier[0].equals("Cursed")) this.desc = "Glows viciously" + " " + desc.split(" ")[1];
        }
    }
    //Использование предмета. Возвращает сообщение
    public String use() {
        if (name().equals("Heal potion")) {
            this.owner().hp += effect;
            if (owner().hp >  owner().max_hp) owner().hp =  owner().max_hp;
            this.owner().inv.remove(this);
            return String.format("You used a %s, now you have %d HP", name(), owner.hp);
        }
        else if (name().equals("Teleport"))
        {
            owner.hp += effect;
            if (owner().hp > owner().max_hp) owner().hp = owner().max_hp;
            owner.getWorld().addAtEmptyLocation(owner);
            this.owner().inv.remove(this);
            return String.format("You used a %s, teleporting away, now you have %d HP", name(), owner.hp);
        }
        else if (name().equals("CTF Guide"))
        {
            owner.dmg += effect;
            this.owner().inv.remove(this);
            return String.format("You used a %s, fuelling your rage, entering frenzied state", name());
        }
        return "You use unknown magic";
    }
}
