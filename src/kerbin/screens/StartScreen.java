package kerbin.screens;
//Стартовый экран, отображает приветствие и создает новый игровой экран. TODO: Обучение
import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class StartScreen implements Screen {

	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.write("MGUPI Studio presents:", 1, 1);

		//terminal.writeCenter("A best Pinatel and not a Tyrant game",3);
		//terminal.writeCenter("True Roguelike Alpha", 4);
		//terminal.writeCenter("MOUSES RODEO AT DUNGEONS OF MGUPI",5);
		//terminal.write("ver: 0.01",6,6);
		terminal.writeCenter("Controls:", 7);
		terminal.write("WASD or arrows to move", 1,9);

		terminal.writeCenter("True Roguelike Pre-Alpha", 3);
		terminal.writeCenter("Controls:", 5);
		terminal.write("WASD or arrows to move", 1,6);
		terminal.write("ENTER on # to load next level", 1,9);



		terminal.write("I to open inventory", 1,10);
		terminal.write("U in inventory to use item, W to equip item", 1,11);

		terminal.writeCenter("-- press [enter] to next --", 30);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
	}
}
