package kerbin;
// Factory для существ, создает Creature, Ai, и помещает в свободный тайл
import asciiPanel.AsciiPanel;

public class CreatureFactory {
    private World world;

    public CreatureFactory(World world){
        this.world = world;
    }
    public Creature newPlayer(){
        Creature player = new Creature(world, '@', AsciiPanel.brightWhite);
        world.addAtEmptyLocation(player);
        new PlayerAi(player);
        return player;
    }
    public Creature newMouse(){
        Creature mouse = new Creature(world, 'm', AsciiPanel.red);
        world.addAtEmptyLocation(mouse);
        new CreatureAi(mouse);
        return mouse;
    }
}