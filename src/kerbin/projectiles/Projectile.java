package kerbin.projectiles;

import kerbin.creatures.Creature;
import kerbin.world.World;

import java.awt.*;
import java.io.Serializable;

public class Projectile implements Serializable {
        public World getWorld() {
            return world;
        }

        private World world;
        public Creature owner;
        public int x;
        public int mx;
        public int y;
        public int my;



        public int dmg;
        public int vectorx;
        public int vectory;
        private char glyph;
        public String name;

        public char glyph() {
            return glyph;
        }

        private Color color;
        public Color color() {
            return color;
        }

        public ProjectileAi ai;

    public Projectile(World world, Creature owner, char glyph, Color color, String name, int dmg, int x, int y, int mx, int my, int vectorx, int vectory){
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
        this.vectorx=vectorx;
        this.vectory=vectory;
        this.world.projectiles.add(this);
    }
}
