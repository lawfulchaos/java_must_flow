package kerbin.screens;
/* Основной игровой экран. содержит игровую логику,
генерацию предметов, отображение и обработку клавиатуры */ 
import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;
import kerbin.*;
import kerbin.items.Item;
import kerbin.items.ItemFactory;

public class PlayScreen implements Screen {
	private World world;
	public Creature player;
	private int screenWidth;
	private int screenHeight;
	private Screen subscreen;
	/* Генерация нового экрана и добавление предметов/мобов */
	public PlayScreen(){
		screenWidth = 80;
		screenHeight = 21;
		createWorld();
		Event.getInstance().init("Welcome to MGUPI Roguelike", 0, -1, AsciiPanel.brightWhite);
		CreatureFactory creatureFactory = new CreatureFactory(world);
		ItemFactory itemFactory = new ItemFactory(world);
		player = creatureFactory.newPlayer();
		world.player = player;
		for (int i = 0; i < 4; i++) {
			Creature mouse = creatureFactory.newMouse();
			world.creatures.add(mouse);
			Creature skeleton = creatureFactory.newSkeleton();
			world.creatures.add(skeleton);
		}
		for (int j = 0; j < 4; j++) {
			itemFactory.newWeapon(null);
		}
		for (int j = 0; j < 4; j++) {
			itemFactory.newHeal(null);
		}
		for (int j = 0; j < 4; j++) {
			itemFactory.newArmor(null);
		}
	}

	private void createWorld(){
		world = new WorldBuilder(90, 32).build();
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

		terminal.write(Event.getInstance().getMsg(), 0, 22, Event.getInstance().getColor());
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
					return new WinScreen();
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
