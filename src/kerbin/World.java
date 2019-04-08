package kerbin;
//Общий класс мира
import kerbin.items.Item;
import kerbin.items.ItemFactory;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class World {
	public Tile[][] tiles;
	private int width;
	public int width() { return width; }
	public Creature player;
	private int height;
	public int height() { return height; }
	public TileFactory tileFactory;
	public ProjectileFactory projectileFactory;
	public List<Creature> creatures;
	public List<Projectile> projectiles;

	public World(Tile[][] tiles){
		this.tiles = tiles;
//Размеры поля генерируются исходя из размера переданного массива
		this.width = tiles.length;
		this.height = tiles[0].length;
		this.creatures = new ArrayList();
		this.projectiles = new ArrayList();
		this.tileFactory = new TileFactory();
		this.projectileFactory = new ProjectileFactory(this);
		populateWorld();
	}
	//Создает мобов и предметы
	public void populateWorld()
	{
		CreatureFactory creatureFactory = new CreatureFactory(this);
		ItemFactory itemFactory = new ItemFactory(this);
		this.player = creatureFactory.newPlayer();
		for (int i = 0; i < 4; i++) {
			Creature mouse = creatureFactory.newMouse();
			creatures.add(mouse);
			Creature skeleton = creatureFactory.newSkeleton();
			creatures.add(skeleton);
			Creature mob = creatureFactory.newMob();
			creatures.add(mob);
		}
		for (int j = 0; j < 3; j++) {
			if (Math.random() > 0.5) itemFactory.newBattleaxe(null);
			else itemFactory.newSword(null);
		}
		for (int j = 0; j < 3; j++) {
			itemFactory.newHeal(null);
		}
		for (int j = 0; j < 1; j++) {
			itemFactory.newTeleport(null);
		}
		for (int j = 0; j < 2; j++) {
			if (Math.random() > 0.5) itemFactory.newPlate(null);
			else itemFactory.newMail(null);
		}
	}
//Возращает тайл по заданным коордам. Semi-deprecated
	public Tile tile(int x, int y){
		if (x < 0 || x >= width || y < 0 || y >= height)
			return tileFactory.newBound();
		else
			return tiles[x][y];
	}
//Возвращает символ по коордам
	public char glyph(int x, int y){
		return tile(x, y).glyph();
	}
	
	public Color color(int x, int y){
		return tile(x, y).color();
	}
//Генерирует случайное незанятое местоположение на проходимой клетке. Определено для Creature/Item.
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
		while (!tile(x,y).isGround() || item(x,y)!=null && !tile(x,y).isUtil);

		tiles[x][y].item = i;
	}
//Возвращает существо по коордам, при отсутствии возвращает null
	public Creature creature(int x, int y){
		if (player != null && player.x == x && player.y == y) return player;
		for (Creature c : creatures){
			if (c.x == x && c.y == y)
				return c;
		}
		return null;
	}
//Возвращает итем по коордам, при отсутствии возвращает null
	public Item item(int x, int y){ return tiles[x][y].item; }

}
