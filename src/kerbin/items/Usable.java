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
            if (modifier[0].equals("Cursed"))
            {
                this.desc = "Glows viciously" + " " + desc.split(" ")[1];
                this.effect -= (Integer) modifier[2] * 3;
            }
            else
            {
                this.effect += (Integer) modifier[2] * 3;
            }
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
        else if (name().equals("Lootbox"))
        {
            ItemFactory itemFactory = new ItemFactory(this.owner.getWorld());
            this.owner().inv.remove(this);
            double chance = Math.random();
            if (this.modifier != null) chance *= ((double)((int)modifier[2] / 2));
            if (this.modifier != null && this.modifier[0].equals("Cursed"))
            {
                this.owner.gold-=100;
                if (this.owner.gold < 0) this.owner.gold = 0;
                return "You open empty lootbox, buying key for 100 gold. You feel robbed";
            }
            else if (chance > 0.75)
            {
                Item item = itemFactory.newRandom(this.owner);
                this.owner.inv.add(item);
                return String.format("You open lootbox, getting brand new %s", item.name());
            }
            return "You open lootbox, getting nothing";
        }
        return "You use unknown magic";
    }
}
