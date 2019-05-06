package kerbin;
//Общий класс мира
import asciiPanel.AsciiPanel;
import kerbin.items.Item;
import kerbin.items.ItemFactory;
import kerbin.items.Usable;
import kerbin.items.Weapon;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class World implements Serializable {
	public Tile[][] tiles;
	private int width;
	public int width() { return width; }
	public Creature player;
	private int height;
	public int height() { return height; }

	public TileFactory tileFactory;
	public ProjectileFactory projectileFactory;
	public CreatureFactory creatureFactory;
	public ItemFactory itemFactory;

	public List<Creature> creatures;
	//Нейтральные/Дружелюбные существа
	public List<Creature> npcs;
	public List<Projectile> projectiles;
	public List<Item> items;

	public World(Tile[][] tiles){
		this.tiles = tiles;
//Размеры поля генерируются исходя из размера переданного массива
		this.width = tiles.length;
		this.height = tiles[0].length;
		this.creatures = new ArrayList();
		this.npcs = new ArrayList();
		this.projectiles = new ArrayList();
		this.items = new ArrayList();
		this.tileFactory = new TileFactory();
		this.projectileFactory = new ProjectileFactory(this);
		this.creatureFactory = new CreatureFactory(this);
		this.player = creatureFactory.newPlayer();
		this.itemFactory = new ItemFactory(this);


	}
	//Создает мобов и предметы
	public void populateWorld()
	{
		ItemFactory itemFactory = new ItemFactory(this);
		for (int i = 0; i < 4; i++) {
			Creature mouse = creatureFactory.newMouse();
			creatures.add(mouse);
			Creature skeleton = creatureFactory.newSkeleton();
			creatures.add(skeleton);
			Creature mob = creatureFactory.newMob();
			creatures.add(mob);
		}
		Creature merch = creatureFactory.newMerchant();
		npcs.add(merch);
		for (int j = 0; j < 3; j++) {
			if (Math.random() > 0.5) items.add(itemFactory.newBattleaxe(null));
			else items.add(itemFactory.newSword(null));
		}
		for (int j = 0; j < 2; j++) {
			items.add(itemFactory.newBow(null));
		}
		for (int j = 0; j < 3; j++) {
			items.add(itemFactory.newHeal(null));
		}
		for (int j = 0; j < 2; j++) {
			items.add(itemFactory.newArrows(null));
		}
		for (int j = 0; j < 1; j++) {
			items.add(itemFactory.newTeleport(null));
		}
		for (int j = 0; j < 30; j++) {
			if (Math.random() > 0.5) items.add(itemFactory.newPlate(null));
			else items.add(itemFactory.newMail(null));
		}
	}
//Спавнит предметы и босса
	public void populateBossWorld()
	{
		itemFactory.newArrows(null);
		itemFactory.newHeal(null);
		itemFactory.newRandom(null);
		Creature boss = new Creature(this, 'M', AsciiPanel.red, "Lord Mousarium", 35,5,20,999,10, 500,6);
		boss.setWeapon(itemFactory.newTeeth(boss));
		boss.setArmor(itemFactory.newHide(boss));
		boss.inv.add(new Weapon('%', Color.yellow, "Guitar", null, 30, true,
				"A strange instrument from foreign lands, disturbingly glowing with radiation, deeply beloved by Mouse Lord. You find no use for it, other of macing enemies on your path", 450, 12));
		boss.x = 45;
		boss.y = 25;
		new BossAi(boss);
		creatures.add(boss);
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
//Спавнит игрока на уровне босса
	public void addAtBossLevel(Creature creature){
		creature.x = 45;
		creature.y = 5;
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
//Возвращает нпс по коордам, при отсутствии возвращает null
	public Creature npc(int x, int y){
		for (Creature c : npcs){
			if (c.x == x && c.y == y)
				return c;
		}
		return null;
	}
//Возвращает итем по коордам, при отсутствии возвращает null
	public Item item(int x, int y){ return tiles[x][y].item; }
	//Проверяет наличие торговца рядом, при отсутствии возвращает null
	public Creature checkMerchant(int x, int y)
	{
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				Creature c = npc(x+i, y+j);
				if (c != null) return c;
			}
		}
		return null;
	}
}
