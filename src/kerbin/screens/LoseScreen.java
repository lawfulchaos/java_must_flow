package kerbin.screens;

import asciiPanel.AsciiPanel;
import kerbin.ApplicationMain;
import javax.swing.Timer;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class LoseScreen implements Screen {

    private JFrame frame;
    private int iteration;
    LoseScreen(JFrame frame) {
        this.frame = frame;
        this.iteration = 0;
        ApplicationMain.playSound("fulldead.wav");
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        if (iteration < 3) {
            terminal.write("Congrats.", 1, 1);
            ActionListener taskPerformer = evt -> {
                if (iteration == 0) {
                    terminal.write("You lose.", 1, 2);
                } else if (iteration == 1) {
                    terminal.write("FFFFFFFFFF", 1, 6);
                    terminal.write("FFFFFFFFFF", 1, 7);
                    terminal.write("FFF", 1, 8);
                    terminal.write("FFF", 1, 9);
                    terminal.write("FFFFFF", 1, 10);
                    terminal.write("FFFFFF", 1, 11);
                    terminal.write("FFF", 1, 12);
                    terminal.write("FFF", 1, 13);
                    terminal.write("FFF", 1, 14);
                } else {
                    terminal.writeCenter("-- press [enter] to restart --", 30);
                    ((Timer) evt.getSource()).stop();
                }
                iteration++;
                terminal.repaint();
            };
            Timer timer = new Timer(400, taskPerformer);
            timer.start();
        }
        else
        {
            terminal.write("Congrats.", 1, 1);
            terminal.write("You lose.", 1, 2);
            terminal.write("FFFFFFFFFF", 1, 6);
            terminal.write("FFFFFFFFFF", 1, 7);
            terminal.write("FFF", 1, 8);
            terminal.write("FFF", 1, 9);
            terminal.write("FFFFFF", 1, 10);
            terminal.write("FFFFFF", 1, 11);
            terminal.write("FFF", 1, 12);
            terminal.write("FFF", 1, 13);
            terminal.write("FFF", 1, 14);
            terminal.writeCenter("-- press [enter] to restart --", 30);
        }
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        if (key.getKeyCode() == KeyEvent.VK_ENTER) {
            ApplicationMain.playSound();
            return new PlayScreen(frame);
        }
        return this;
    }
}
