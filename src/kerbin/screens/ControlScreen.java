package kerbin.screens;
//Стартовый экран, отображает приветствие и создает новый игровой экран. TODO: Обучение
import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

import javax.swing.*;

public class ControlScreen implements Screen {
    private JFrame frame;
    public ControlScreen(JFrame frame) {
        this.frame = frame;
    }
    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.writeCenter("Controls:", 3);
        terminal.write("WASD or arrows to move", 1,5);
        terminal.write("ENTER on # to load next level", 1,6);

        terminal.write("I to open inventory", 1,7);
        terminal.write("U in inventory to use item, W to equip item, D to drop", 1,8);
        terminal.write("F5/F9 to save/load", 1,9);
        terminal.write("F to fire", 1,10);

        terminal.writeCenter("-- press [enter] to return --", 37);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new StartScreen(frame): this;
    }
}