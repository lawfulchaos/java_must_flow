package kerbin;
/* Физические характеристики существа, поведение обрабатывается в CreatureAi, создается CreatureFactory*/
import kerbin.items.Armor;
import kerbin.items.Item;
import kerbin.items.Weapon;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Creature {
    public World getWorld() { return world; }

    private World world;

    public int x;
    public int y;
    public String name;
    public CreatureAi ai;
    public List<Item> inv;
    public void setCreatureAi(CreatureAi ai) { this.ai = ai; }
// Символ существа
    private char glyph;
    //хп существ
    public int hp;
    public int max_hp;
    //дамаг существ
    public int dmg;
    public int def;
    public Armor armor;
    public Weapon weapon;
    public char glyph() { return glyph; }
    public void setWorld(World world)
    {
        this.world=world;
    }
    private Color color;
    public Color color() { return color; }

    public Creature(World world, char glyph, Color color, String name, int hp, int dmg, int def,int max_hp){
        this.world = world;
        this.glyph = glyph;
        this.color = color;
        this.inv = new ArrayList<>();
        this.name = name;
        this.hp=hp;
        this.dmg=dmg;
        this.def=def;
        this.weapon = null;
        this.armor = null;
        this.max_hp=max_hp;
    }
    public void setWeapon(Weapon weapon)
    {
        if (this.weapon != null)
        {
            this.dmg -= this.weapon.dmg;
            if (this.dmg < 0) this.dmg = 0;
            inv.add(this.weapon);
        }
        this.weapon = weapon;
        this.dmg += weapon.dmg;
    }
    public void setArmor(Armor armor)
    {
        if (this.armor != null)
        {
            this.def -= this.armor.def;
            if (this.def < 0) this.def = 0;
            inv.add(this.armor);
        }
        this.armor = armor;
        this.def += armor.def;
    }
//Движение, реакция на смещение обрабатывается AI
    public void moveBy(int mx, int my){
        ai.onEnter(x+mx, y+my, world.tile(x+mx, y+my));
    }

}

