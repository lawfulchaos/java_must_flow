package kerbin;
//ИИ скелета
import asciiPanel.AsciiPanel;

import java.util.concurrent.ThreadLocalRandom;

public class SkeletonAi extends CreatureAi {

    public SkeletonAi(Creature creature) {
        super(creature);
    }
    /*public void onTurn(Creature player) {
        int mx = ThreadLocalRandom.current().nextInt(-1, 2);
        int my = ThreadLocalRandom.current().nextInt(-1, 2);
        this.creature.moveBy(mx, my);
    }*/
    public void onTurn(Creature player){
        if (Math.pow(Math.pow(creature.x - player.x, 2) + Math.pow(creature.y - player.y, 2), 0.5) > creature.radius || Math.pow(Math.pow(creature.x - player.x, 2) + Math.pow(creature.y - player.y, 2), 0.5) < 3){
            int mx = ThreadLocalRandom.current().nextInt(-1, 2);
            int my = ThreadLocalRandom.current().nextInt(-1, 2);
            this.creature.moveBy(mx, my);
        }
        else{
            int mx, my;
            boolean isMoved = false;
            boolean isVisible = false;//используется для проверки, находится ли игрок в прямой видимости скелета
            if (creature.x - player.x > 0 && creature.y - player.y > 0) { //мышь справа сверху от игрока
                mx = -1;
                my = -1;
                if (creature.getWorld().tile(creature.x + mx, creature.y + my).isGround()) {
                    this.creature.moveBy(mx, my);
                    isMoved = true;
                    for (int i =1; i<=5; i++){
                        if(creature.getWorld().tile(creature.x + i*mx, creature.y + i*my).isGround()&&!isVisible){
                            continue;
                        }
                        else{
                            //ProjectileFactory.newBullet(creature.x, creature.y); не понимаю, как загенерить новый буллет
                            //Projectile bullet = ProjectileFactory.newBullet(creature.x, creature.y); ToDo как блятб загенерить новый буллет? я даже протетсить не могу, помогите дебилу
                            //bullet.vectorx=mx;
                            //bullet.vectory=my;
                            isVisible=true;
                        }
                    }
                }
            }

            if (creature.x - player.x < 0 && creature.y - player.y < 0 && !isMoved) { //мышь слева снизу от игрока
                mx = 1;
                my = 1;
                if (creature.getWorld().tile(creature.x + mx, creature.y + my).isGround()) {
                    this.creature.moveBy(mx, my);
                    isMoved = true;
                }
            }

            if (creature.x - player.x > 0 && creature.y - player.y < 0 && !isMoved) { //мышь справа снизу от игрока
                mx = -1;
                my = 1;
                if (creature.getWorld().tile(creature.x + mx, creature.y + my).isGround()) {
                    this.creature.moveBy(mx, my);
                    isMoved = true;
                }
            }

            if (creature.x - player.x < 0 && creature.y - player.y > 0 && !isMoved) { //мышь слева сверху от игрока
                mx = 1;
                my = -1;
                if (creature.getWorld().tile(creature.x + mx, creature.y + my).isGround()) {
                    this.creature.moveBy(mx, my);
                    isMoved = true;
                }
            }
            if (creature.x - player.x == 0 && creature.y - player.y > 0 && !isMoved) { //мышь ровно сверху от игрока
                mx = 0;
                my = -1;
                if (creature.getWorld().tile(creature.x + mx, creature.y + my).isGround()) {
                    this.creature.moveBy(mx, my);
                    isMoved = true;
                }
            }
            if (creature.x - player.x == 0 && creature.y - player.y < 0 && !isMoved) { //мышь ровно снизу от игрока
                mx = 0;
                my = 1;
                if (creature.getWorld().tile(creature.x + mx, creature.y + my).isGround()) {
                    this.creature.moveBy(mx, my);
                    isMoved = true;
                }
            }
            if (creature.x - player.x > 0 && creature.y - player.y == 0 && !isMoved) { //мышь ровно справа от игрока
                mx = -1;
                my = 0;
                if (creature.getWorld().tile(creature.x + mx, creature.y + my).isGround()) {
                    this.creature.moveBy(mx, my);
                    isMoved = true;
                }
            }
            if (creature.x - player.x < 0 && creature.y - player.y == 0 && !isMoved) { //мышь ровно слева от игрока
                mx = 1;
                my = 0;
                if (creature.getWorld().tile(creature.x + mx, creature.y + my).isGround()) {
                    this.creature.moveBy(mx, my);
                    isMoved = true;
                }
            }
            if (!isMoved) {
                mx = ThreadLocalRandom.current().nextInt(-1, 2);
                my = ThreadLocalRandom.current().nextInt(-1, 2);
                while (!creature.getWorld().tile(creature.x + mx, creature.y + my).isGround()) {
                    mx = ThreadLocalRandom.current().nextInt(-1, 2);
                    my = ThreadLocalRandom.current().nextInt(-1, 2);
                }
                this.creature.moveBy(mx, my);
            }
        }
        }
    public void onEnter(int x, int y, Tile tile) {
        if (tile.isGround()) {
            Creature c = creature.getWorld().creature(x, y);
            if (c != null && c.name != "skeleton" && c.name != "mouse") {
                int priority = 2;
                if (priority >= Event.getInstance().getPriority()) {
                    Event.getInstance()
                            .init(String.format("%s: You`ve been attacked by skeleton", c.name), priority, 3, AsciiPanel.brightWhite);
                }
                super.battle(c);
                super.teleport(tile);
            } else {
                creature.x = x;
                creature.y = y;
            }
        }
    }
}