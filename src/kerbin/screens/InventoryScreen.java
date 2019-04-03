package kerbin.screens;

import java.awt.*;
import java.awt.event.KeyEvent;
//Базовый класс инвентаря, выводит предметы TODO Крафт(?), разнообразие
import asciiPanel.AsciiPanel;
import kerbin.Creature;
import kerbin.items.Item;
import kerbin.items.Weapon;

public class InventoryScreen implements Screen {
    protected Creature player;
    protected char[] alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    protected int i;
    protected int chosen;
    public InventoryScreen(Creature player) {
        this.player = player;
        chosen = 0;
    }
    //Показывает заголовки инвентаря и надетую экипировку, используется в наследниках
    protected void showHeader(AsciiPanel terminal)
    {
        int y = 4;
        int x = 3;
        i = 0;
        terminal.clear();
        terminal.write("Inventory: ", 1, 1, Color.WHITE);
        terminal.write("Equipped: ", 55, 1, Color.WHITE);
        terminal.write("Weapon: ", 55, 3);
        terminal.write("Armor: ", 55, 4);
        if (player.weapon != null) terminal.write(player.weapon.name(), 65, 3);
        if (player.armor != null) terminal.write(player.armor.name(), 65, 4);
        terminal.write("Description: ", 55, 8, Color.WHITE);
    }
    @Override
    public void displayOutput(AsciiPanel terminal) {
        showHeader(terminal);
        int y = 4;
        int x = 4;
        i = 0;
        for (Item item : player.inv) {
            showItem(terminal, item, x, y);
            y++;
            i++;
            if (i != 0 && i % 36 == 0)
            {
                y = 4;
                x += 25;
            }
        }

        terminal.clear(' ', 0, 23, 80, 1);
        terminal.repaint();
    }
    //Отображение предмета, используется в наследниках
    public void showItem(AsciiPanel terminal, Item it, int ix, int iy)
    {
        if (this.i==chosen)
        {
            terminal.write("> ", ix - 4, iy, Color.WHITE);
            switch (it.glyph()) {
                case '!':
                    terminal.write("dmg = 10", 55, 10);
                    break;
                case (127):
                    terminal.write("def = 10", 55, 10);
                    break;
                case '+':

                    terminal.write("heal = ", 55, 10);
                    break;
            }
            terminal.write(it.desc, 55, 12);
        }
        terminal.write(alphabet[this.i] + ":  ", ix - 2, iy);
        terminal.write(it.name(), ix, iy);
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
            case KeyEvent.VK_DOWN:
                if (chosen < i-1) chosen+=1;
                break;
            case KeyEvent.VK_UP:
                if (chosen > 0) chosen-=1;
                break;
        }
            return this;
    }
}