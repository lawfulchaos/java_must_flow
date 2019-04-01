package kerbin.screens;

import java.awt.event.KeyEvent;
//Базовый класс инвентаря, выводит предметы TODO Взаимодействие с предметами
import asciiPanel.AsciiPanel;
import kerbin.Creature;
import kerbin.items.Item;

public class InventoryScreen implements Screen {
    protected Creature player;
    protected char[] alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    protected int i;
    public InventoryScreen(Creature player) {
        this.player = player;
    }
    //Показывает верхнюю часть инвентаря, используется в наследниках
    protected void showHeader(AsciiPanel terminal)
    {
        int y = 4;
        int x = 3;
        i = 0;
        terminal.clear();
        terminal.write("Inventory: ", 1, 1);
        terminal.write("Equipped: ", 40, 1);
        terminal.write("Weapon: ", 40, 3);
        terminal.write("Armor: ", 40, 4);
        if (player.weapon != null) terminal.write(player.weapon.name(), 50, 3);
        if (player.armor != null) terminal.write(player.armor.name(), 50, 4);
    }
    @Override
    public void displayOutput(AsciiPanel terminal) {
        showHeader(terminal);
        int y = 4;
        int x = 3;
        i = 0;
        for (Item item : player.inv) {
            terminal.write(alphabet[i] + ":  ", x - 2, y);
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
            case KeyEvent.VK_W:
                return new EquipScreen(player);
            case KeyEvent.VK_U:
                return new UseScreen(player);
        }
            return this;
    }
}