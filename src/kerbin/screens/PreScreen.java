package kerbin.screens;
//Стартовый экран, отображает сюжетку

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class PreScreen implements Screen {

    @Override
    public void displayOutput(AsciiPanel terminal) {

        terminal.writeCenter("So long time ago, when our team lead was a child",4);
        terminal.writeCenter("ancient dungeons of MGUPI were pretty decent and simply.",5);
        terminal.writeCenter("But time is continued",10);
        terminal.writeCenter("One time he came.... And he doesn't like what he saw",37);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
    }
}