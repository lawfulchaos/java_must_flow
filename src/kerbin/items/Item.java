package kerbin.items;
//Итемы, генерируются в ItemFactory
import kerbin.Creature;
import java.awt.Color;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Item  implements Serializable {
    //Словарь модификаторов, {Эффект, Название, Цвет}
    Object[][] modifiers = {
            {"Cursed", Color.RED, -10},
            {"Relic", Color.WHITE, 3},
            {"Blessed", Color.BLUE, 5},
            {"Holy", Color.GREEN, 10}
    };

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
    public String desc;
    public int cost; //Стоимость объекта
    //Модификатор обьекта, {Эффект, Название, Цвет}
    public Object[] modifier;
    public Color color() {
        return color;
    }

    private String name;

    public String name() {
        return name;
    }

    public Item(char glyph, Color color, String name, Creature owner, boolean isEquipable, String desc, int cost) {
        this.glyph = glyph;
        this.color = color;
        this.name = name;
        this.owner = owner;
        this.isEquipable = isEquipable;
        this.desc = desc;
        this.cost = cost;
        //Добавление случайного модификатора в 1/2 случаев
        if (Math.random() > 0.5) modifier = modifiers[(int)(Math.random()*modifiers.length)];
        else modifier = null;
        //Изменение цены из-за модификатора
        if (modifier != null){
        switch ((String)modifier[0]) {
            case "Cursed":
                this.cost /= 10;
                break;
            case "Relic":
                this.cost *= 1.3;
                break;
            case "Blessed":
                this.cost *= 1.6;
                break;
            case "Holy":
                this.cost *= 2;
                break;
        }
        }
    }
}
