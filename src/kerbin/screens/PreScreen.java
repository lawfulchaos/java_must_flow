package kerbin.screens;
//Стартовый экран, отображает сюжетку

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

import javax.swing.*;

public class PreScreen implements Screen {
    private JFrame frame;
    PreScreen(JFrame frame)
    {
        this.frame = frame;
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {

        terminal.writeCenter("A long time ago when our team lead was a child",4);
        terminal.writeCenter("The Great and Powerful MGUPI was pretty decent",5);
        terminal.writeCenter("Smart",6);

        terminal.writeCenter("And the first in the whole world state university",7);

        terminal.writeCenter("Time is going",9);
        terminal.writeCenter("Child grew up from a little man",10);
        terminal.writeCenter("to the important member of our society",11);

        terminal.writeCenter("Member who never beats his friends",12);
        terminal.writeCenter("*his comrades",13);
        terminal.writeCenter("**his subordinates",14);
        terminal.writeCenter("Well, time never to stop too and continues in all aspects",16);
        terminal.writeCenter("He became a student of MGUPI and he has reached many successes",17);
        terminal.writeCenter("But this university is not what it used to be. It is...another",18);
        terminal.writeCenter("Time has come",21);
        terminal.writeCenter("Only you can help him",22);
        terminal.writeCenter("No one ain't know what happened and it's your mission:",23);
        terminal.writeCenter("To know",24);
        terminal.writeCenter("To solve",25);
        terminal.writeCenter("You must",30);
        terminal.writeCenter("Begin",35);
        terminal.writeCenter("--press [enter] to start--",39);

    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen(frame) : this;
    }
}