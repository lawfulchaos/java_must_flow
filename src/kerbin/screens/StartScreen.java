package kerbin.screens;
//Стартовый экран, отображает меню и создает новый игровой экран.

import asciiPanel.AsciiPanel;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

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
        MenuScreen.drawHeading(terminal);
        terminal.write("New Game", 3, 12);
        terminal.write("Load Game", 3, 13);
        terminal.write("Controls", 3, 14);
        terminal.write("Exit", 3, 15);
        terminal.write(">", 1, chosen + 12, AsciiPanel.brightWhite);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                chosen += 1;
                if (chosen > 3) chosen = 0;
                break;
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                chosen -= 1;
                if (chosen < 0) chosen = 3;
                break;
            case KeyEvent.VK_ENTER:
                switch (chosen) {
                    case 0:
                        return new PreScreen(frame);
                    case 1:
                        PlayScreen loadLvl = new PlayScreen(frame);
                        loadLvl.loadGame();
                        return loadLvl;
                    case 2:
                        return new ControlScreen(frame, null);
                    case 3:
                        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                        break;
                }
        }
        return this;
    }
}
