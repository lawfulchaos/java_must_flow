package kerbin.screens;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;
import kerbin.Creature;
import kerbin.CreatureFactory;
import kerbin.World;
import kerbin.WorldBuilder;

public class PlayScreen implements Screen {
	private World world;
	Creature player;
	private int screenWidth;
	private int screenHeight;
	
	public PlayScreen(){
		screenWidth = 80;
		screenHeight = 21;
		createWorld();
		CreatureFactory creatureFactory = new CreatureFactory(world);
		player = creatureFactory.newPlayer();
		world.creatures.add(player);
		for (int i = 0; i < 8; i++) {
			Creature mouse = creatureFactory.newMouse();
			world.creatures.add(mouse);
		}
	}
	
	private void createWorld(){
		world = new WorldBuilder(90, 32).build();
	}

	public int getScrollX() { return Math.max(0, Math.min(player.x - screenWidth / 2, world.width() - screenWidth)); }

	public int getScrollY() { return Math.max(0, Math.min(player.y - screenHeight / 2, world.height() - screenHeight)); }
	@Override
	public void displayOutput(AsciiPanel terminal) {
		
		int left = getScrollX();
		int top = getScrollY(); 
		
		displayTiles(terminal, left, top);

		terminal.writeCenter("-- press [escape] to lose or [enter] to win --", 22);
	}

	private void displayTiles(AsciiPanel terminal, int left, int top) {
		for(int x = 0; x < screenWidth ; x++) {
			for(int y = 0; y < screenHeight; y++) {
				int wx = x + left;
				int wy = y + top;
				terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
			}
		}
		for(Creature c : world.creatures) {
			if((c.x >= left && c.x < left + screenWidth) && (c.y >= top && c.y < top + screenHeight)) {
				terminal.write(c.glyph(), c.x - left, c.y - top, c.color());
			}
		}
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		for (Creature creature: world.creatures) {
			creature.ai.onTurn();
		}
		switch (key.getKeyCode()){
		case KeyEvent.VK_ESCAPE: return new LoseScreen();
		case KeyEvent.VK_ENTER: return new WinScreen();
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_H: player.moveBy(-1, 0); break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_L: player.moveBy( 1, 0); break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_K: player.moveBy( 0,-1); break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_J: player.moveBy( 0, 1); break;
		case KeyEvent.VK_Y: player.moveBy(-1,-1); break;
		case KeyEvent.VK_U: player.moveBy( 1,-1); break;
		case KeyEvent.VK_B: player.moveBy(-1, 1); break;
		case KeyEvent.VK_N: player.moveBy( 1, 1); break;
		}
		return this;
	}
}
