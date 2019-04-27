package kerbin.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class JournalScreen implements Screen {

    @Override
    public void displayOutput(AsciiPanel terminal) {

    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? null : this;
    }
}