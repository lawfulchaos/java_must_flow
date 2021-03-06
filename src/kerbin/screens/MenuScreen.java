package kerbin.screens;
//Стартовый экран, отображает меню и создает новый игровой экран.

import asciiPanel.AsciiPanel;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class MenuScreen implements Screen {
    protected int i;
    private int chosen;
    private PlayScreen game;
    private String msg;
    private JFrame frame;

    MenuScreen(JFrame frame, PlayScreen game) {
        chosen = 0;
        this.frame = frame;
        this.game = game;
        this.msg = "";
    }

    static void drawHeading(AsciiPanel terminal) {
        terminal.write("MGUPI Studio presents:", 1, 1);
        terminal.writeCenter("A best Pinatel and not a Tyrant game", 3);
        terminal.writeCenter("True Roguelike Beta", 4);
        terminal.writeCenter("WORK: World of Roguelike Kettles", 7);
        terminal.writeCenter("Menu:", 11);
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.clear();
        drawHeading(terminal);
        terminal.write("Resume", 3, 12);
        terminal.write("Save Game", 3, 13);
        terminal.write("Load Game", 3, 14);
        terminal.write("Controls", 3, 15);
        terminal.write("Exit", 3, 16);
        terminal.write(">", 1, chosen + 12, AsciiPanel.brightWhite);
        terminal.clear(' ', 0, 38, 80, 1);
        terminal.write(msg, 0, 39, AsciiPanel.brightGreen);
        terminal.repaint();
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                return game;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                chosen += 1;
                if (chosen > 4) chosen = 0;
                break;
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                chosen -= 1;
                if (chosen < 0) chosen = 4;
                break;
            case KeyEvent.VK_ENTER:
                switch (chosen) {
                    case 0:
                        return game;
                    case 1:
                        game.saveGame();
                        msg = "Game saved";
                        return game;
                    case 2:
                        game.loadGame();
                        return game;
                    case 3:
                        return new ControlScreen(frame, game);
                    case 4:
                        return new StartScreen(frame);

                }
        }
        return this;
    }
}
