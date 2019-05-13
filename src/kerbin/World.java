package kerbin;
//Общий класс мира
import asciiPanel.AsciiPanel;
import kerbin.items.*;

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
	public String [][] story = {{"Toilet paper","Toilet paper #2","Toilet paper #3","My notes","Page of study book","Table-napkin","My notes #2","My notes #3"},{"Khm, i've been here for several days and i am still trying to understand what went wrong. Since this is a zombie apocalypse type, i will accordingly write on all sorts of rubbish and i hope my records will find. I don't know who will read it, but know: you beat a lot of bricks with your head if you really here","So, how it all started. It was a normal day, the usual study as always. But then the light abruptly disappeared. At first we thought it was just a typical outage, but not. This was not the case. Immediately it became dark on the street, immediately became somehow...Spooky","I can't stay here longer. Need to go fast","Some strange thing. I see that all mouses were humans, i see their eyes, which see only meat in me. Probably they was hungry very long and now they only can running on their hands and legs and trying to eat who isn't like they. Skeletons were hungry too, but they is not like mouses. They throw study books in me. i think they tried to make exams, but the luck was not lovely for them. Yeah, riot, brothers. ","I'm move out from the toilet and now my location is the stair near buffet. To be clearly, it's is between two buffet. Now i don't know what i must do. Ways out cuted out, can't to run into the second floor, can't go at third. Maybe first...","It wasn't good idea. One mouse bit me, but i kill it. Now i'll become a mouse. I have a few minutes, so you should know. I saw 4 guys, who can be guilty. They look like monsters, but not the same. They are ... is...wnat et...maet....fud","I understood. 4 guys. I must find them","So, looks like it is it. And..oh damn..My slaves ? Really ? Okay. I have to push and punch them",}};

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
		this.itemFactory = new ItemFactory(this);


	}
	//Создает мобов и предметы
	public void populateWorld()
	{
		for (int i = 0; i < 4; i++) {
			Creature mouse = creatureFactory.newMouse();
			creatures.add(mouse);
			Creature skeleton = creatureFactory.newSkeleton();
			creatures.add(skeleton);
			Creature mob = creatureFactory.newMob();
			creatures.add(mob);
		}
		ItemFactory itemFactory = new ItemFactory(this);
		itemFactory.newStory(null, story[0][player.level - 1], story[1][player.level - 1]);
		itemFactory.newLootbox(null);
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
		for (int j = 0; j < 3; j++) {
			if (Math.random() > 0.5) items.add(itemFactory.newPlate(null));
			else items.add(itemFactory.newMail(null));
		}
	}

//Спавнит предметы и босса
	public void populateBossWorld() {
		itemFactory.newArrows(null);
		itemFactory.newHeal(null);
		itemFactory.newRandom(null);
		Creature boss;
		switch (player.level / 3) {
			case 3:
				boss = new Creature(this, 'M', AsciiPanel.red, "Lord Mousarium", 35, 5, 20, 999, 10, 500, 6);
				boss.setWeapon(itemFactory.newTeeth(boss));
				boss.setArmor(itemFactory.newHide(boss));
				boss.inv.add(new Weapon('%', Color.yellow, "Guitar", null, 35, true,
						"A strange instrument from foreign lands, disturbingly glowing with radiation, deeply beloved by Mouse Lord. You find no use for it, other of macing enemies on your path. It feels like part of something greater", 450, 12));
				break;
			case 2:
				boss = new Creature(this, 'R', AsciiPanel.yellow, "Reality Hacker", 15, 3, 10, 15 * player.player_level, 10, 500, 6);
				boss.setWeapon(itemFactory.newTeeth(boss));
				boss.setArmor(itemFactory.newHide(boss));
				boss.inv.add(new Usable('+', Color.yellow, "CTF Guide", null, 10, true,
						"Unknown words of ancient origin, spoken only by Hacker, they fill you with renowned berserk rage, empowering you with unprecedented strength. It feels like part of something greater", 450));
				break;
			case 1:
				boss = new Creature(this, 'S', AsciiPanel.red, "Father of skeletons", 20, 4, 10, 20 * player.player_level, 10, 500, 15);
				boss.setWeapon(itemFactory.newTeeth(boss));
				boss.setArmor(itemFactory.newHide(boss));
				boss.inv.add(new Armor((char) 127, Color.yellow, "Meizu Backpack", null, 90, true,
						"An exotic armor piece from Far East of the world, used by the First of the Skeletons, for some reason you are sure that you wearing it wrong. It feels like part of something greater", 450));
				break;
			case 0:
				boss = new Creature(this, 'H', AsciiPanel.red, "Hikkihino", 20, 4, 20, 20 * player.player_level, 10, 500, 15);
				boss.setWeapon(itemFactory.newTeeth(boss));
				boss.setArmor(itemFactory.newHide(boss));
				boss.inv.add(new Ranged('}', Color.yellow, "Notebook", null, 35, true,
						"Shiny notebook, owned by Hikkihino, bearing part of its owners wild flow of destructive ideas, allowing you to devastate minds of your enemies. It feels like part of something greater", 450, 30));
						break;
			default:
				boss = new Creature(this, 'S', AsciiPanel.red, "Lord Mousarium", 35, 5, 20, 999, 10, 500, 6);
				boss.setWeapon(itemFactory.newTeeth(boss));
				boss.setArmor(itemFactory.newHide(boss));
				boss.inv.add(new Weapon('%', Color.yellow, "Guitar", null, 30, true,
						"A strange instrument from foreign lands, disturbingly glowing with radiation, deeply beloved by Mouse Lord. You find no use for it, other of macing enemies on your path", 450, 12));
				break;
		}
		if (boss != null) {
			boss.x = 45;
			boss.y = 25;
			new BossAi(boss);
			creatures.add(boss);
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
