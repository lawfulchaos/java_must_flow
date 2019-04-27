package kerbin;
//Factory для тайлов, возвращает новый Tile
import asciiPanel.AsciiPanel;

import java.io.Serializable;


public class TileFactory  implements Serializable {

    public TileFactory(){
    }

    public Tile newWall(){
        return new Tile((char)177, AsciiPanel.yellow, false, false);
    }
    public Tile newStairs(){
        return new Tile('#', AsciiPanel.yellow, true, true);
    }
    public Tile newTeleport(){
        return new Tile('O', AsciiPanel.yellow, true, true);
    }
    public Tile newFloor(){
        return new Tile((char)250, AsciiPanel.yellow, true, false);
    }
// Границы поля
    public Tile newBound(){
        return new Tile('x', AsciiPanel.brightBlack, false, false);
    }
}