package kerbin;

public class ProjectileAi {
    public ProjectileAi(){}
    void onTurn(World world, Projectile bullet){
        boolean isHit=false;
        for (int i =0; i<=5; i++){
            if (!world.tile(bullet.x+i*bullet.vectorx, bullet.y+i*bullet.vectory).isGround()){
                isHit=true;
                bullet.mx+=i*bullet.vectorx;
                bullet.my+=i*bullet.vectory;
                break;
            }
        }
        bullet.x += bullet.mx;
        bullet.y += bullet.my;
        if(isHit) hit(world, bullet);
    }
    void hit(World world, Projectile bullet) {
            if (world.creature(bullet.x, bullet.y).name == "player") {
                world.creature(bullet.x, bullet.y).hp -= bullet.dmg;
                bullet.getWorld().projectiles.remove(bullet);
            }
            else{
                bullet.getWorld().projectiles.remove(bullet);
            }
        }
    }
