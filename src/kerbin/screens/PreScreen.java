package kerbin.screens;
//Стартовый экран, отображает сюжетку

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class PreScreen implements Screen {

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.writeCenter("So long time ago, when our team lead n",4);

    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
    }
}