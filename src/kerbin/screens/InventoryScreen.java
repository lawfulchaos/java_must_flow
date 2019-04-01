package kerbin.screens;

import java.awt.event.KeyEvent;
//Базовый класс инвентаря, выводит предметы TODO Взаимодействие с предметами
import asciiPanel.AsciiPanel;
import kerbin.Creature;
import kerbin.items.Item;

public class InventoryScreen implements Screen {
    private Creature player;
    private char[] alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    public InventoryScreen(Creature player)
    { this.player = player; }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        int y = 4;
        int x = 3;
        int i = 0;
        terminal.clear();
        terminal.write("Inventory: ", 1, 1);
        terminal.write("Equipped: ", 40, 1);
        if (player.weapon!=null) terminal.write(player.weapon.name(), 40, 3);
        if (player.armor!=null) terminal.write(player.armor.name(), 40, 4);
        for (Item item : player.inv){
            terminal.write(alphabet[i]+": ", x-2, y);
            terminal.write(item.name(), x, y++);
            i++;
        }

        terminal.clear(' ', 0, 23, 80, 1);
        terminal.repaint();
    }



    @Override
    public Screen respondToUserInput(KeyEvent key) {
        char c = key.getKeyChar();
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                return null;
        //    case KeyEvent.VK_:
        //        return new WinScreen();
    }

}
