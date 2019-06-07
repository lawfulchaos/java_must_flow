package kerbin.screens;

import asciiPanel.AsciiPanel;
import kerbin.ApplicationMain;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class LoseScreen implements Screen {

    private JFrame frame;

    LoseScreen(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        ApplicationMain.playSound("fulldead.wav");
        terminal.write("Congrats.", 1,1);
        terminal.write("You lose.", 1,2);
        terminal.write("FFFFFFFFFF", 1,6);
        terminal.write("FFFFFFFFFF", 1,7);
        terminal.write("FFF", 1,8);
        terminal.write("FFF", 1,9);
        terminal.write("FFFFFF",1 ,10);
        terminal.write("FFFFFF", 1,11);
        terminal.write("FFF", 1,12);
        terminal.write("FFF", 1,13);
        terminal.write("FFF", 1,14);

        terminal.writeCenter("-- press [enter] to restart --", 30);
    }


    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()){
            case KeyEvent.VK_ENTER:
                ApplicationMain.playSound();
                return new PlayScreen(frame);
        }

        return this;
    }
}
