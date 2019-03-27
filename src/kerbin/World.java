package kerbin;

import kerbin.items.Item;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class World {
	public Tile[][] tiles;
	private int width;
	public int width() { return width; }

	private int height;
	public int height() { return height; }
	public TileFactory tileFactory;
	public List<Creature> creatures;

	public World(Tile[][] tiles){
		this.tiles = tiles;
		this.width = tiles.length;
		this.height = tiles[0].length;
		this.creatures = new ArrayList();
		this.tileFactory = new TileFactory();
	}
	
	public Tile tile(int x, int y){
		if (x < 0 || x >= width || y < 0 || y >= height)
			return tileFactory.newBound();
		else
			return tiles[x][y];
	}
	
	public char glyph(int x, int y){
		return tile(x, y).glyph();
	}
	
	public Color color(int x, int y){
		return tile(x, y).color();
	}

	public void addAtEmptyLocation(Creature creature){
		int x;
		int y;

		do {
			x = (int)(Math.random() * width);
			y = (int)(Math.random() * height);
		}
		while (!tile(x,y).isGround() || creature(x,y)!=null);

		creature.x = x;
		creature.y = y;
	}
	public void addAtEmptyLocation(Item i){
		int x;
		int y;

		do {
			x = (int)(Math.random() * width);
			y = (int)(Math.random() * height);
		}
		while (!tile(x,y).isGround() || item(x,y)!=null);

		tiles[x][y].item = i;
	}

	public Creature creature(int x, int y){
		for (Creature c : creatures){
			if (c.x == x && c.y == y)
				return c;
		}
		return null;
	}
	public Item item(int x, int y){ return tiles[x][y].item; }

}
