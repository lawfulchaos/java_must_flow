package kerbin.screens;
//Стартовый экран, отображает меню и создает новый игровой экран. TODO: Обучение
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import asciiPanel.AsciiPanel;

import javax.swing.*;

public class StartScreen implements Screen {
	protected int i;
	protected int chosen;
	private JFrame frame;
	public StartScreen(JFrame frame) {
		chosen = 0;
		this.frame = frame;
	}
	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.write("MGUPI Studio presents:", 1, 1);
		terminal.writeCenter("A best Pinatel and not a Tyrant game",3);
		terminal.writeCenter("True Roguelike Beta", 4);
		terminal.writeCenter("WORK: World of Roguelike Kettles",7);
		terminal.writeCenter("Menu:", 11);
		terminal.write("New Game", 3,12);
		terminal.write("Load Game", 3,13);
		terminal.write("Controls", 3,14);
		terminal.write("Exit", 3,15);
		terminal.write(">", 1,chosen+12, AsciiPanel.brightWhite);
/*
		terminal.writeCenter("Controls:", 11);
		terminal.write("WASD or arrows to move", 1,12);
		terminal.write("ENTER on # to load next level", 1,13);

		terminal.write("I to open inventory", 1,14);
		terminal.write("U in inventory to use item, W to equip item, D to drop", 1,15);
		terminal.write("F5/F9 to save/load", 1,16);
		terminal.write("F to fire", 1,17);

		terminal.writeCenter("-- press [enter] to return to menu --", 37);
		*/
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		//return key.getKeyCode() == KeyEvent.VK_ENTER ? new PreScreen() : this;
		switch (key.getKeyCode()) {
			case KeyEvent.VK_S:
			case KeyEvent.VK_DOWN:
				chosen+=1;
				if (chosen > 3) chosen = 0;
				break;
			case KeyEvent.VK_W:
			case KeyEvent.VK_UP:
				chosen-=1;
				if (chosen < 0) chosen = 3;
				break;
			case KeyEvent.VK_ENTER:
				switch (chosen){
					case 0:
						return new PreScreen(frame);
					case 1:
						PlayScreen loadLvl = new PlayScreen(frame);
						loadLvl.loadGame();
						return loadLvl;
					case 2:
						return new ControlScreen(frame);
					case 3:
						frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
						break;
				}
		}
		return this;
	}
}
