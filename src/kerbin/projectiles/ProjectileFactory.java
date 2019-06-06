// Factory для снарядов, создает Projectile, и помещает в свободный тайл
package kerbin.projectiles;

import asciiPanel.AsciiPanel;
import kerbin.creatures.Creature;
import kerbin.world.World;

import java.io.Serializable;

public class ProjectileFactory implements Serializable {
    private World world;

    public ProjectileFactory(World world) {
        this.world = world;
    }

    public void newBullet(Creature owner, int x, int y, int mx, int my, int vectorx, int vectory, int dmg) {
        Projectile bullet = new Projectile(world, owner, '*', AsciiPanel.red, "bullet", dmg, x, y, mx, my, vectorx, vectory);
        bullet.ai = new ProjectileAi(bullet);

    }

}