package kerbin.projectiles;

import kerbin.creatures.Creature;
import kerbin.world.World;

import java.awt.*;
import java.io.Serializable;

public class Projectile implements Serializable {
    public Creature owner;
    public int x;
    public int y;
    public int dmg;
    public String name;
    public ProjectileAi ai;
    int mx;
    int my;
    int vectorx;
    int vectory;
    private World world;
    private char glyph;
    private Color color;

    Projectile(World world, Creature owner, char glyph, Color color, String name, int dmg, int x, int y, int mx, int my, int vectorx, int vectory) {
        this.owner = owner;
        this.world = world;
        this.glyph = glyph;
        this.color = color;
        this.name = name;
        this.dmg = dmg;
        this.x = x;
        this.y = y;
        this.mx = mx;
        this.my = my;
        this.vectorx = vectorx;
        this.vectory = vectory;
        this.world.projectiles.add(this);
    }

    public World getWorld() {
        return world;
    }

    public char glyph() {
        return glyph;
    }

    public Color color() {
        return color;
    }
}
