package kerbin.screens;

import java.awt.event.KeyEvent;
//Базовый класс инвентаря, выводит предметы TODO Отображение в PlayScreen
import asciiPanel.AsciiPanel;
import kerbin.Creature;
import kerbin.items.Item;

public class InventoryScreen implements Screen {
    private Creature player;

    public InventoryScreen(Creature player)
    { this.player = player; }

    @Override
    public void displayOutput(AsciiPanel terminal) {

        int y = 23 - player.inv.size();
        int x = 4;

        if (player.inv.size() > 0)
            terminal.clear(' ', x, y, 20, player.inv.size());

        for (Item item : player.inv){
            terminal.write(item.name(), x, y++);
        }

        terminal.clear(' ', 0, 23, 80, 1);
        terminal.repaint();
    }



    @Override
    public Screen respondToUserInput(KeyEvent key) {
        char c = key.getKeyChar();
        if (key.getKeyCode() == KeyEvent.VK_ESCAPE)
            return null;
        else
            return this;
    }

}
