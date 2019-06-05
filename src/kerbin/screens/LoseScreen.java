package kerbin.screens;

import asciiPanel.AsciiPanel;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class LoseScreen implements Screen {

    private JFrame frame;

    LoseScreen(JFrame frame) {
        this.frame = frame;
    }


    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("Congrats.", 1, 1);
        terminal.write("You lost.", 1, 3);
        terminal.write("FFFFFFFFFF", 1, 5);
        terminal.write("FFFFFFFFFF", 1, 6);
        terminal.write("FFF", 1, 7);
        terminal.write("FFF", 1, 8);
        terminal.write("FFFFFF", 1, 9);
        terminal.write("FFFFFF", 1, 10);
        terminal.write("FFF", 1, 11);
        terminal.write("FFF", 1, 12);
        terminal.write("FFF", 1, 13);
        terminal.writeCenter("-- press [enter] to restart --", 30);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen(frame) : this;
    }
}
