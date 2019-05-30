package kerbin.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

import javax.swing.*;

public class LoseScreen implements Screen {

	private JFrame frame;
	LoseScreen(JFrame frame)
	{
		this.frame = frame;
	}


	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.write("Congrats.", 1, 1);
		terminal.write("You lost.", 1, 3);
		terminal.writeCenter("-- press [enter] to restart --", 30);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen(frame) : this;
	}
}
