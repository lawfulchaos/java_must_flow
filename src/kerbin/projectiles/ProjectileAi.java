package kerbin.projectiles;

import asciiPanel.AsciiPanel;
import kerbin.ApplicationMain;
import kerbin.Event;
import kerbin.world.World;
import java.io.Serializable;

public class ProjectileAi implements Serializable {
    private Projectile bullet;

    ProjectileAi(Projectile bullet) {
        this.bullet = bullet;
    }

    public void onTurn(World world) {
        boolean isHit = false;
        for (int i = 1; i <= 3; i++) {
            if (!world.tile(bullet.x + bullet.mx, bullet.y + bullet.my).isGround()
                    || ((world.creature(bullet.x + bullet.mx, bullet.y + bullet.my) != null)
                    && !(world.creature(bullet.x + bullet.mx, bullet.y + bullet.my).name.equals(bullet.owner.name)))) {

                isHit = true;
                break;
            }
            bullet.mx = i * bullet.vectorx;
            bullet.my = i * bullet.vectory;
        }
        bullet.x += bullet.mx;
        bullet.y += bullet.my;
        if (isHit) hit(world);
    }

    private void hit(World world) {
        if (world.creature(bullet.x, bullet.y) != null && !(world.creature(bullet.x, bullet.y).name.equals(bullet.owner.name))) {
            world.creature(bullet.x, bullet.y).hp -= bullet.dmg;
            Event.getInstance().init("attacked from a distance", 5, 3, AsciiPanel.brightWhite);
            bullet.getWorld().projectiles.remove(bullet);
            //звук
            ApplicationMain.playSound("getbullet.wav");
        } else {
            bullet.getWorld().projectiles.remove(bullet);
        }
    }
}
