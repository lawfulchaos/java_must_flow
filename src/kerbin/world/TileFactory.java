package kerbin.world;
//Factory для тайлов, возвращает новый Tile

import asciiPanel.AsciiPanel;

import java.io.Serializable;


class TileFactory implements Serializable {

    TileFactory() {
    }

    Tile newWall() {
        return new Tile((char) 177, AsciiPanel.yellow, false, false);
    }

    Tile newStairs() {
        return new Tile('#', AsciiPanel.yellow, true, true);
    }

    Tile newTeleport() {
        return new Tile('O', AsciiPanel.yellow, true, true);
    }

    Tile newFloor() {
        return new Tile((char) 250, AsciiPanel.yellow, true, false);
    }

    // Границы поля
    Tile newBound() {
        return new Tile('x', AsciiPanel.brightBlack, false, false);
    }
}