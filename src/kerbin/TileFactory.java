package kerbin;
//Factory для тайлов, возвращает новый Tile
import asciiPanel.AsciiPanel;


public class TileFactory {

    public TileFactory(){
    }

    public Tile newWall(){
        return new Tile((char)177, AsciiPanel.yellow, false);
    }
    public Tile newFloor(){
        return new Tile((char)250, AsciiPanel.yellow, true);
    }
// Границы поля
    public Tile newBound(){
        return new Tile('x', AsciiPanel.brightBlack, false);
    }
}