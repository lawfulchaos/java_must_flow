package kerbin.screens;
/* Основной игровой экран. содержит игровую логику, отображение и обработку клавиатуры */
import java.awt.*;
import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;
import kerbin.*;
import kerbin.Event;
public class PlayScreen implements Screen {
	private World world;
	public Creature player;
	private int screenWidth;
	private int screenHeight;
	private Screen subscreen;
	/* Генерация нового экрана и добавление игрока в CreateWorld*/
	public PlayScreen(){
		screenWidth = 90;
		screenHeight = 30;
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

		//Шмат хпшки
		terminal.write("HP: ",0,30, Color.green);
		for (int k = 0;k<10;k++)
		{
			if (k>=(player.hp/10))
			terminal.write((char)254,5+k,30, Color.red);
			else terminal.write((char)254,5+k,30, Color.green);
		}
		// DEF out
		if (player.def>1) {
			terminal.write("DEF: ", 0, 31, Color.green);
			for (int k=0;k<player.armor.startdef;k++)
			{
				if (k >= (player.def))
					terminal.write((char) 150, 5 + k, 31, Color.red);
				else terminal.write((char) 150, 5 + k, 31, Color.blue);
			}
		}
		
		//damage out
		if (player.def>1) {
			terminal.write("DMG: ", 0, 32, Color.green);
			terminal.write(Integer.toString(player.dmg), 5, 32, Color.green);
		}
		else {
			terminal.write("DMG: ", 0, 31, Color.green);
			terminal.write(Integer.toString(player.dmg), 5, 31, Color.green);
		}

		terminal.write("Map", 70, 30, Color.green);
		terminal.write(Integer.toString(player.level), 83, 30, Color.green);

		terminal.write("Player level", 70, 31, Color.green);
		terminal.write(Integer.toString(player.player_level ), 83, 31, Color.green);

		terminal.write("Honor", 70, 32, Color.green);
		terminal.write(Integer.toString(player.honor ), 81, 32, Color.green);
		terminal.write("/", 83, 32, Color.green);
		terminal.write(Integer.toString(player.max_honor ), 85, 32, Color.green);

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
		else {
			//Проверяем, если нажата клавиша не из списка управления - игноририруем.
			boolean isAction = true;
			switch (key.getKeyCode()) {
				case KeyEvent.VK_ESCAPE:
					return new LoseScreen();
				case KeyEvent.VK_ENTER:
					if(player.getWorld().tile(player.x, player.y).glyph() == '#' && player.getWorld().creatures.size() ==0){
						PlayScreen nextLvl = new PlayScreen();
						nextLvl.setPlayer(player);
						player.level++;
						return nextLvl;
						}
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
				case KeyEvent.VK_I:
					subscreen = new InventoryScreen(player);
					break;
				case KeyEvent.VK_5:
					player.moveBy(0, 0);
					break;
				default:
					isAction = false;
					break;
			}
				if (isAction) {
					for (Creature creature : world.creatures) {
						creature.ai.onTurn(player);

					}
				}

			if (Event.getInstance().getLifetime() == 0) {
				Event.getInstance().init("Welcome to MGUPI Roguelike", 0, -1, AsciiPanel.brightWhite);
			} else Event.getInstance().decreaseLifetime();
			return this;
		}
	}
}
