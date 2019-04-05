// Factory для снарядов, создает Projectile, и помещает в свободный тайл
package kerbin;

import asciiPanel.AsciiPanel;

public class ProjectileFactory {
    private World world;

    public ProjectileFactory(World world) {
        this.world = world;
    }

    /*public Projectile newBullet(int x, int y) {
        Projectile bullet = new Projectile(world, '*', AsciiPanel.red, "bullet", (int)(1+Math.random()*10), x, y, 3,3);

        return bullet;
        if (world.tile(bullet.x, bullet.y).isGround()) {
            if (world.creature(bullet.x, bullet.y).name=="player") {
                world.creature(bullet.x, bullet.y).hp -= bullet.dmg;
                projectile.getWorld().creatures.remove(c);
            }
            //else
        }
    }*/

}