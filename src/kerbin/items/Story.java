package kerbin.items;

import kerbin.creatures.Creature;

import java.awt.*;
import java.io.Serializable;

class Story extends Item implements Serializable {

    Story(char glyph, Color color, String name, Creature owner, boolean isEquipable, String desc, int cost) {
        super(glyph, color, name, owner, isEquipable, desc, cost);
        modifier = null;
    }
}
