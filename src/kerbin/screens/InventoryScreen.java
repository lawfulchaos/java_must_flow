package kerbin.screens;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Set;
//Базовый класс инвентаря, выводит предметы TODO Крафт(?)
import asciiPanel.AsciiPanel;
import kerbin.Creature;
import kerbin.items.Armor;
import kerbin.items.Item;
import kerbin.items.Usable;
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
        if (player.weapon != null)
        {
            int wx = 65;
            if (player.weapon.modifier != null) {
                String modifier = (String) player.weapon.modifier[0];
                terminal.write(modifier, wx, 3, (Color) player.weapon.modifier[1]);
                wx += modifier.length() + 1;
            }
            terminal.write(player.weapon.name(), wx, 3);
        }
        if (player.armor != null)
        {
            int wx = 65;
            if (player.armor.modifier != null) {
                String modifier = (String) player.armor.modifier[0];
                terminal.write(modifier, wx, 4, (Color) player.armor.modifier[1]);
                wx += modifier.length() + 1;
            }
            terminal.write(player.armor.name(), wx, 4);
        }
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
        if (this.i==chosen) {
            terminal.write("> ", ix - 4, iy, Color.WHITE);
            if (it instanceof Weapon) {
                terminal.write("DMG:", 55, 10, Color.WHITE);
                if (it.modifier != null && it.modifier[0] == "Cursed" && it.name() == "Battleaxe")
                {
                    terminal.write("HP: ", 75, 10, Color.WHITE);
                    terminal.write("-50", 79, 10, Color.RED);
                }
                terminal.write(((Weapon) it).dmg + "", 60, 10, Color.GREEN);
            } else if (it instanceof Armor) {
                if (it.modifier!= null && it.modifier[0] == "Cursed") {
                    terminal.write("HP:", 55, 10, Color.WHITE);
                    terminal.write(((Armor) it).def + "", 60, 10, Color.RED);
                }
                    else {
                        terminal.write("DEF:", 55, 10, Color.WHITE);
                    terminal.write(((Armor) it).def + "", 60, 10, Color.BLUE);
                }
            } else if (it instanceof Usable) {
                if (it.name().equals("Java class")) terminal.write("DMG: ", 55, 10, Color.WHITE);
                else terminal.write("HP: ", 55, 10, Color.WHITE);
                terminal.write(((Usable) it).effect + "", 60, 10, Color.RED);
            }
            terminal.write("GOLD: ", 65, 10, Color.WHITE);
            terminal.write(it.cost + "", 71, 10, Color.ORANGE);
            //разбирваем на слова и проверяем длину описания, если больше экрана - переносим
            String[] description = it.desc.split(" ");
            int y = 11;
            int x = 55;
            for (String word : description) {
                if (x + word.length() >= 89) {
                    y++;
                    x = 55;
                }
                terminal.write(word, x, y);
                x += word.length()+1;
            }
        }
        terminal.write(alphabet[this.i] + ":  ", ix - 2, iy);
        if (it.modifier != null) {
            String modifier = (String) it.modifier[0];
            terminal.write(modifier, ix, iy, (Color) it.modifier[1]);
            ix+=modifier.length()+1;
        }
        terminal.write(it.name(), ix, iy);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                return null;
            case KeyEvent.VK_W:
                return new EquipScreen(player);
            case KeyEvent.VK_U:
                return new UseScreen(player);
            case KeyEvent.VK_D:
                return new DropScreen(player);
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