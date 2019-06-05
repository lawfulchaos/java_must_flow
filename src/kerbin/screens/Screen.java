package kerbin.screens;
// Общий интерфейс для всех экранов
import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public interface Screen {
	void displayOutput(AsciiPanel terminal);
	
	Screen respondToUserInput(KeyEvent key);
}
