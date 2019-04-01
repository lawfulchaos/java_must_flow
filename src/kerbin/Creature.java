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
    //дамаг существ
    public int dmg;
    public int def;
    public Item armor;
    public Item weapon;
    public char glyph() { return glyph; }

    private Color color;
    public Color color() { return color; }

    public Creature(World world, char glyph, Color color, String name, int hp, int dmg, int def){
        this.world = world;
        this.glyph = glyph;
        this.color = color;
        this.inv = new ArrayList<Item>();
        this.name = name;
        this.hp=hp;
        this.dmg=dmg;
        this.def=def;
        this.weapon = null;
        this.armor = null;
    }
    public void setWeapon(Weapon weapon)
    {
        this.weapon = weapon;
        this.dmg += weapon.dmg;
    }
    public void setArmor(Armor armor)
    {
        this.armor = armor;
        this.def += armor.def;
    }
//Движение, реакция на смещение обрабатывается AI
    public void moveBy(int mx, int my){
        ai.onEnter(x+mx, y+my, world.tile(x+mx, y+my));
    }

}

