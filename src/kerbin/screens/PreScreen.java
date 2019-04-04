package kerbin.screens;
//Стартовый экран, отображает сюжетку

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class PreScreen implements Screen {

    @Override
    public void displayOutput(AsciiPanel terminal) {

        terminal.writeCenter("Long, long time ago, when our team lead was a child",4);
        terminal.writeCenter("ancient dungeons of MGUPI were pretty decent and simple.",5);
        terminal.writeCenter("But time goes on",10);
        terminal.writeCenter("Once he came.... And he didn't like what he saw",37);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
    }
}