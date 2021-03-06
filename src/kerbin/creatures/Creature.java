package kerbin.creatures;
/* Физические характеристики существа, поведение обрабатывается в CreatureAi, создается CreatureFactory*/

import kerbin.AI.CreatureAi;
import kerbin.items.Armor;
import kerbin.items.Item;
import kerbin.items.Weapon;
import kerbin.world.World;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Creature implements Serializable {
    public int x;
    public int y;
    public int gold; //деньги
    public int ammo;
    public int level = 1; // счетчик уровня
    public int honor = 0; // опыт героя
    public int player_level = 1; // уровень героя
    public int max_honor = 50; // экспа для перехода на следующий лвл
    public String name;
    public CreatureAi ai;
    public List<Item> inv;
    //хп существ
    public int hp;
    public int kd;
    public int max_hp;
    //дамаг существ
    public int dmg;
    public int def;
    public int[] effect; // Действующий эффект на хп существа [Сила, время]
    public int startdef;
    public Armor armor;
    public Weapon weapon;
    public int radius;
    private World world;
    // Символ существа
    private char glyph;
    private Color color;

    public Creature(World world, char glyph, Color color, String name, int hp, int dmg, int def, int max_hp, int radius, int gold, int kd) {
        this.world = world;
        this.glyph = glyph;
        this.color = color;
        this.inv = new ArrayList<>();
        this.name = name;
        this.hp = hp;
        this.dmg = dmg;
        this.def = def;
        this.weapon = null;
        this.armor = null;
        this.max_hp = max_hp;
        this.radius = radius;
        this.gold = gold;
        this.effect = null;
        this.ammo = 5;
        this.kd = kd;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void setCreatureAi(CreatureAi ai) {
        this.ai = ai;
    }

    public char glyph() {
        return glyph;
    }

    public Color color() {
        return color;
    }

    public void setWeapon(Weapon weapon) {
        if (this.weapon != null) {
            this.dmg -= this.weapon.dmg;
            if (this.dmg < 0) this.dmg = 0;
            inv.add(this.weapon);
        }
        this.weapon = weapon;
        if (weapon.modifier != null && weapon.modifier[0] == "Cursed" && weapon.name().equals("Battleaxe"))
            hp -= 50;
        this.dmg += weapon.dmg;
        if (this.dmg < 0) this.dmg = 0;
    }

    public void setArmor(Armor armor) {
        if (this.armor != null) {
            this.def -= this.armor.def;
            if (this.armor.modifier != null && this.armor.modifier[0].equals("Cursed")) {
                this.effect = null;
            }
            if (this.def < 0) {
                this.def = 0;
            }
            inv.add(this.armor);
            this.startdef = this.armor.startdef;

        }
        this.armor = armor;
        this.def += armor.def;
        if (this.def < 0) {
            this.effect = new int[]{this.armor.def, -1};
            this.def = 0;
        }
        this.startdef = this.armor.startdef;
    }

    //Движение, реакция на смещение обрабатывается AI
    public void moveBy(int mx, int my) {
        ai.onEnter(x + mx, y + my, world.tile(x + mx, y + my));
    }

}

