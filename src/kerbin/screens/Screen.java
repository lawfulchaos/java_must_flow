package kerbin.screens;
// Общий интерфейс для всех экранов
import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public interface Screen {
	public void displayOutput(AsciiPanel terminal);
	
	public Screen respondToUserInput(KeyEvent key);
}
