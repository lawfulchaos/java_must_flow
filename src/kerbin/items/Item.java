package kerbin.items;
//Итемы, генерируются в ItemFactory
import kerbin.Creature;
import java.awt.Color;
public class Item {

    private char glyph;
    public Creature owner;
    public Creature owner() {
        return owner;
    }
    public char glyph() {
        return glyph;
    }
    private Color color;
    public boolean isEquipable;
    public Color color() {
        return color;
    }

    private String name;

    public String name() {
        return name;
    }

    public Item(char glyph, Color color, String name, Creature owner, boolean isEquipable) {
        this.glyph = glyph;
        this.color = color;
        this.name = name;
        this.owner = owner;
        this.isEquipable = isEquipable;
    }
}
