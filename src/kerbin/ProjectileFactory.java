// Factory для снарядов, создает Projectile, и помещает в свободный тайл
package kerbin;

import asciiPanel.AsciiPanel;

public class ProjectileFactory {
    private World world;

    public ProjectileFactory(World world) {
        this.world = world;
    }

    public Projectile newBullet(int x, int y, int vectorx, int vectory) {
        Projectile bullet = new Projectile(world, '*', AsciiPanel.red, "bullet", (int)(1+Math.random()*10), x, y, 3, 3,vectorx,vectory);
        bullet.ai = new ProjectileAi(bullet);
        return bullet;

    }

}