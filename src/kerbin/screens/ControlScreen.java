package kerbin.screens;
//Экран, отображающий управление

import asciiPanel.AsciiPanel;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class ControlScreen implements Screen {
    private JFrame frame;
    private PlayScreen game;

    public ControlScreen(JFrame frame,PlayScreen game) {
        this.frame = frame;
        this.game = game;
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.writeCenter("Controls:", 3);
        terminal.write("WASD or arrows to move", 1, 5);
        terminal.write("ENTER on # to load next level", 1, 6);

        terminal.write("I to open inventory", 1, 7);
        terminal.write("U in inventory to use item, W to equip item, D to drop", 1, 8);
        terminal.write("F5/F9 to save/load", 1, 9);
        terminal.write("F to fire", 1, 10);

        terminal.writeCenter("-- press [enter] to return --", 37);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                if (game!=null)
                    return game;
                else return new StartScreen(frame);

        }
        return this;
    }
}
