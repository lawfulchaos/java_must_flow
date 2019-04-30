package kerbin.screens;
/* Основной игровой экран. содержит игровую логику, отображение и обработку клавиатуры */
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;

import asciiPanel.AsciiPanel;
import kerbin.*;
import kerbin.Event;
import kerbin.items.Armor;
import kerbin.items.Ranged;

public class PlayScreen implements Screen {
	private World world;
	public Creature player;
	private int screenWidth;
	private int screenHeight;
	private Screen subscreen;
	private boolean isShooting;
	/* Генерация нового экрана и добавление игрока в CreateWorld*/
	public PlayScreen(){
		screenWidth = 90;
		screenHeight = 30;
		isShooting = false;
		createWorld();
		Event.getInstance().init("You are playing WORK now", 0, -1, AsciiPanel.brightWhite);
	}

	public void setPlayer(Creature player)
    {
        this.player = player;
        world.player = player;
        player.setWorld(world);
        world.addAtEmptyLocation(player);
    }
	//Создает мир, внутри мира генерируются мобы и игрок
	private void createWorld(){
		world = new WorldBuilder(90, 32).build();
		player = world.player;
	}
// Сдвиг экрана при движении игрока
	public int getScrollX() { return Math.max(0, Math.min(player.x - screenWidth / 2, world.width() - screenWidth)); }

	public int getScrollY() { return Math.max(0, Math.min(player.y - screenHeight / 2, world.height() - screenHeight)); }
//Вывод игрового поля со сдвигом
	@Override
	public void displayOutput(AsciiPanel terminal) {


		int left = getScrollX();
		int top = getScrollY();

		displayTiles(terminal, left, top);

		//Рамочка
		for (int dx = 0; dx < 90; dx++) {
			terminal.write((char) 196, dx, 30, Color.cyan);
			if ((dx % 21 == 0) && (dx / 21 != 4)) {
				for (int dy = 30; dy < 40; dy++)
					terminal.write((char) 179, dx, dy, Color.cyan);
				terminal.write((char) 194, dx, 30, Color.cyan);
			}
		}
		terminal.write((char) 169, 0, 30, Color.cyan);


		//Шмат хпшки
		terminal.write((char) 157 + " YOUR HEALTHPOINT " + (char) 157, 1, 31, Color.green);
		for (int k = 0; k < 20; k++) {
			if (k >= (player.hp / 5)) //знаменатель - число очков здоровья, которое отображает символ
				terminal.write((char) 254, k + 1, 32, Color.red);
			else terminal.write((char) 254, k + 1, 32, Color.green);
		}
		terminal.write(Integer.toString(player.hp), 9, 33, Color.green);


		// DEF out
		terminal.write((char) 177 + "YOUR  DEFENCE" + (char) 177, 25, 31, Color.green);
		if (player.def > 1) {
			for (int k = 0; k < 20; k++) {
				double dobdef = (float) player.def / player.startdef * 20;
				if (k > ((float) dobdef)) // Показывает процент целой брони от общей.
					terminal.write((char) 176, k + 22, 32, Color.red);
				else terminal.write((char) 178, k + 22, 32, Color.blue);
			}
			terminal.write(Integer.toString(player.def), 30, 33, Color.green);

		} else {
			terminal.write("0", 31, 33, Color.green);
			terminal.write("Nope", 30, 32, Color.red);
		}


		//damage out

		terminal.write("-|YOUR WEAPON|-", 46, 31, Color.green);
		if (player.weapon != null)
			if (player.weapon.name().length()<6)
				terminal.write(player.weapon.name(), 45 + player.weapon.name().length()+4, 32, Color.magenta);
			else
			terminal.write(player.weapon.name(), 45 + player.weapon.name().length()/2, 32, Color.magenta);
		else terminal.write("Fists", 51, 32, Color.magenta);
		terminal.write(Integer.toString(player.dmg), 53, 33, Color.green);


		terminal.write("Map", 65, 31, Color.green);
		terminal.write(Integer.toString(player.level), 82, 31, Color.green);

		terminal.write("Player level", 65, 32, Color.green);
		terminal.write(Integer.toString(player.player_level), 82, 32, Color.green);

		terminal.write("Honor", 65, 33, Color.green);
		terminal.write(Integer.toString(player.honor), 82, 33, Color.green);
		terminal.write("/", 84, 33, Color.green);
		terminal.write(Integer.toString(player.max_honor), 86, 33, Color.green);

		terminal.write("Gold", 65, 34, Color.green);
		terminal.write(Integer.toString(player.gold), 82, 34, Color.green);
		terminal.write("Ammo", 65, 35, Color.green);
		terminal.write(Integer.toString(player.ammo), 82, 35, Color.green);
		terminal.write(Event.getInstance().getMsg(), 0, 39, Event.getInstance().getColor());
		if (subscreen != null)
			subscreen.displayOutput(terminal);
	}
	private void displayTiles(AsciiPanel terminal, int left, int top) {
		for(int x = 0; x < screenWidth ; x++) {
			for(int y = 0; y < screenHeight; y++) {
				int wx = x + left;
				int wy = y + top;
				terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
				if (world.tiles[wx][wy].item!=null)
				{terminal.write(world.tiles[wx][wy].item.glyph(), x, y, world.tiles[wx][wy].item.color());}
			}
		}
		for(Creature c : world.creatures) {
			if((c.x >= left && c.x < left + screenWidth) && (c.y >= top && c.y < top + screenHeight)) {
				terminal.write(c.glyph(), c.x - left, c.y - top, c.color());
			}
		}
		for(Creature c : world.npcs) {
			if((c.x >= left && c.x < left + screenWidth) && (c.y >= top && c.y < top + screenHeight)) {
				terminal.write(c.glyph(), c.x - left, c.y - top, c.color());
			}
		}
		for(Projectile p : world.projectiles) {
			if((p.x >= left && p.x < left + screenWidth) && (p.y >= top && p.y < top + screenHeight)) {
				terminal.write(p.glyph(), p.x - left, p.y - top, p.color());
			}
		}
		if (isShooting)
		{
			if(world.tile(player.x, player.y - 1).isGround())
				terminal.write('^', player.x - left, player.y - top -1, AsciiPanel.brightWhite);
			if(world.tile(player.x, player.y + 1).isGround())
				terminal.write('v', player.x - left, player.y - top +1, AsciiPanel.brightWhite);
			if(world.tile(player.x + 1, player.y).isGround())
				terminal.write('>', player.x - left+1, player.y - top, AsciiPanel.brightWhite);
			if(world.tile(player.x-1, player.y).isGround())
				terminal.write('<', player.x - left-1, player.y - top, AsciiPanel.brightWhite);
		}
		terminal.write(player.glyph(), player.x - left, player.y - top, player.color());
	}
//Реакция на нажатие клавиши: ход нпс, после движение игрока
	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if(player.hp<=0){
			return new LoseScreen();
		}
		if (subscreen != null) {
			subscreen = subscreen.respondToUserInput(key);
			return this;
		}
		//стрельба
		else if (isShooting)
		{
			switch (key.getKeyCode()) {
				case KeyEvent.VK_LEFT:
				case KeyEvent.VK_A:
					if(world.tile(player.x-1, player.y).isGround()) player.getWorld().projectileFactory.newBullet(player, player.x-1, player.y, -1, 0, -1, 0);
					player.ammo -= 1;
					isShooting = false;
					break;
				case KeyEvent.VK_RIGHT:
				case KeyEvent.VK_D:
					if(world.tile(player.x+1, player.y).isGround()) player.getWorld().projectileFactory.newBullet(player,player.x+1, player.y, +1, 0, +1, 0);
					player.ammo -= 1;
					isShooting = false;
					break;
				case KeyEvent.VK_UP:
				case KeyEvent.VK_W:
					if(world.tile(player.x, player.y-1).isGround()) player.getWorld().projectileFactory.newBullet(player, player.x, player.y-1, 0, -1, 0, -1);
					player.ammo -= 1;
					isShooting = false;
					break;
				case KeyEvent.VK_DOWN:
				case KeyEvent.VK_S:
					if(world.tile(player.x, player.y+1).isGround()) player.getWorld().projectileFactory.newBullet(player, player.x, player.y+1, 0, 1, 0, 1);
					player.ammo -= 1;
					isShooting = false;
					break;
				case KeyEvent.VK_ESCAPE:
					isShooting = false;
					break;
			}
			if (player.ammo < 0) player.ammo = 0;
			return this;
		}

		else {
			//Проверяем, если нажата клавиша не из списка управления - игноририруем.
			boolean isAction = true;
			switch (key.getKeyCode()) {
				//case KeyEvent.VK_ESCAPE:
				//	return new LoseScreen();
				case KeyEvent.VK_ENTER:
					if(player.getWorld().tile(player.x, player.y).glyph() == '#' && player.getWorld().creatures.size() ==0){
						PlayScreen nextLvl = new PlayScreen();
						nextLvl.setPlayer(player);
						player.level++;
						return nextLvl;
						}
					else if (player.getWorld().checkMerchant(player.x, player.y)!=null)
						subscreen = new ShopScreen(player, player.getWorld().checkMerchant(player.x, player.y));
					break;

				case KeyEvent.VK_LEFT:
				case KeyEvent.VK_A:
					player.moveBy(-1, 0);
					break;
				case KeyEvent.VK_RIGHT:
				case KeyEvent.VK_D:
					player.moveBy(1, 0);
					break;
				case KeyEvent.VK_UP:
				case KeyEvent.VK_W:
					player.moveBy(0, -1);
					break;
				case KeyEvent.VK_DOWN:
				case KeyEvent.VK_S:
					player.moveBy(0, 1);
					break;
				case KeyEvent.VK_I: //инвентарь
					isAction = false;
					subscreen = new InventoryScreen(player);
					break;
				case KeyEvent.VK_F: //Стрельба
					if (player.weapon instanceof Ranged)
					{
						if (player.ammo < 1)
						{
							Event.getInstance().init("Not enough ammo", 2, 2, AsciiPanel.red);
							isAction = false;
						}
						else
						{
							Event.getInstance().init("Set direction", 3, 1, AsciiPanel.white);
							isShooting = true;
						}
					}
					break;
				case KeyEvent.VK_5:
					player.moveBy(0, 0);
					break;
					// Сейвлоад
				case KeyEvent.VK_F5:
					isAction = false;
					try {
						File f = new File("\\saves\\save.txt");

						f.getParentFile().mkdirs();
						f.createNewFile();
						FileOutputStream fo = new FileOutputStream(new File("\\saves\\save.dat"));
						ObjectOutputStream o = new ObjectOutputStream(fo);

						o.writeObject(world);
						o.close();
						fo.close();
						Event.getInstance().init("Game saved", 2, 3, AsciiPanel.brightGreen);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					break;
				case KeyEvent.VK_F9:
					isAction = false;
					try {
						FileInputStream fi = new FileInputStream(new File("\\saves\\save.dat"));
						ObjectInputStream oi = new ObjectInputStream(fi);

						// Read objects
						world = (World) oi.readObject();
						player = world.player;
						for (int i = 0; i < world.creatures.size(); i++) {
							if (world.creatures.get(i).ai instanceof MouseAi)
							System.out.println(world.creatures.get(i));
						}
						oi.close();
						fi.close();
						Event.getInstance().init("Game loaded", 2, 3, AsciiPanel.brightGreen);

					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					break;
				default:
					isAction = false;
					break;
			}
				if (isAction) {
					for (int j = 0; j < world.creatures.size();j++){
						world.creatures.get(j).ai.onTurn(player);
					}
					for (int j = 0; j < world.npcs.size();j++){
						world.npcs.get(j).ai.onTurn(player);
					}
					for (int j = 0; j < world.projectiles.size();j++){
						world.projectiles.get(j).ai.onTurn(world);
					}
					player.ai.onTurn(player);
				}

			if (Event.getInstance().getLifetime() == 0) {
				Event.getInstance().init("Welcome to MGUPI Roguelike", 0, -1, AsciiPanel.brightWhite);
			} else Event.getInstance().decreaseLifetime();
			return this;
		}
	}
}
