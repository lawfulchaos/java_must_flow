package kerbin.screens;
//Стартовый экран, отображает приветствие и создает новый игровой экран. TODO: Обучение
import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class StartScreen implements Screen {

	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.write("MGUPI Studio presents:", 1, 1);
		terminal.writeCenter("True Roguelike Pre-Alpha", 3);
		terminal.writeCenter("-- press [enter] to start --", 22);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
	}
}