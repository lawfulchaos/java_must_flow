package kerbin.world;
//Тайл,  создается в TileFactory

import kerbin.items.Item;

import java.awt.*;
import java.io.Serializable;

public class Tile implements Serializable {
    //Предмет на клетке, при отсутствии == null
    public Item item;
    // Проверка на то, является ли телепортом/лестницей
    boolean isUtil;
    private char glyph;
    private Color color;
    //Проверка на проходимость
    private boolean isGround;

    Tile(char glyph, Color color, boolean isGround, boolean isUtil) {
        this.glyph = glyph;
        this.color = color;
        this.item = null;
        this.isGround = isGround;
        this.isUtil = isUtil;
    }

    public char glyph() {
        return glyph;
    }

    public Color color() {
        return color;
    }

    public boolean isGround() {
        return isGround;
    }
}
