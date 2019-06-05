package kerbin.creatures;
// Factory для существ, создает Creature, Ai, и помещает в свободный тайл
import asciiPanel.AsciiPanel;
import kerbin.AI.*;
import kerbin.items.Item;
import kerbin.items.ItemFactory;
import kerbin.world.World;

import java.awt.*;
import java.io.Serializable;

public class CreatureFactory implements Serializable {
    private World world;
    private ItemFactory itemFactory;
    public CreatureFactory(World world){
        this.world = world;
        this.itemFactory = new ItemFactory(world);
    }
    public Creature newPlayer(){
        Creature player = new Creature(world, '@', AsciiPanel.brightWhite, "Player", 100,3,0,100,0, 0,1);
        world.addAtEmptyLocation(player);
        new PlayerAi(player);
        return player;
    }
    public Creature newMerchant(){
        Creature merchant = new Creature(world, '@', Color.GREEN, "Merchant", 100,3,0,100,0, (int)(250+Math.random()*320),1);
        int goodsNum = (int)(Math.random()*5)+5;
        for (int i = 0; i < goodsNum; i++)
        {
            Item it = itemFactory.newRandom(merchant);
            it.cost *= 1.2;
            merchant.inv.add(it);
        }
        world.addAtEmptyLocation(merchant);
        new MerchantAi(merchant);
        return merchant;
    }

    public Creature newMouse(){
        Creature mouse = new Creature(world, 'm', AsciiPanel.red, "mouse",(int)(1+Math.random()*7), (int)(1+Math.random()*7),(int)(Math.random()*5),11,10, 10,1);
        mouse.setWeapon(itemFactory.newTeeth(mouse));
        mouse.setArmor(itemFactory.newHide(mouse));
        world.addAtEmptyLocation(mouse);
        new MouseAi(mouse);
        return mouse;
    }
    public Creature newSkeleton(){
        Creature skeleton = new Creature(world, 's', AsciiPanel.yellow, "skeleton",(int)(1+Math.random()*7),(int)(1+Math.random()*7),(int)(Math.random()*5),11,10,20, 3);
        skeleton.setWeapon(itemFactory.newTeeth(skeleton));
        skeleton.setArmor(itemFactory.newHide(skeleton));
        world.addAtEmptyLocation(skeleton);
        new SkeletonAi(skeleton);
        return skeleton;
    }
    public Creature newMob(){
        Creature mob = new Creature(world, '&', AsciiPanel.brightCyan, "mob",(int)(1+Math.random()*7),(int)(1+Math.random()*7),(int)(Math.random()*5),11,10, 15,1);
        mob.setWeapon(itemFactory.newTeeth(mob));
        mob.setArmor(itemFactory.newHide(mob));
        world.addAtEmptyLocation(mob);
        new MobAi(mob);
        return mob;
    }


}