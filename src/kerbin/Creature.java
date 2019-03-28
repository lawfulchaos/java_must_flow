package kerbin;
/* Физические характеристики существа, поведение обрабатывается в CreatureAi, создается CreatureFactory*/
import kerbin.items.Item;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Creature {
    private World world;

    public int x;
    public int y;
    public CreatureAi ai;
    public List<Item> inv;
    public void setCreatureAi(CreatureAi ai) { this.ai = ai; }
// Символ существа
    private char glyph;
    public char glyph() { return glyph; }

    private Color color;
    public Color color() { return color; }

    public Creature(World world, char glyph, Color color){
        this.world = world;
        this.glyph = glyph;
        this.color = color;
        this.inv = new ArrayList<Item>();
    }
//Движение, реакция на смещение обрабатывается AI
    public void moveBy(int mx, int my){
        ai.onEnter(x+mx, y+my, world.tile(x+mx, y+my));
    }

}
