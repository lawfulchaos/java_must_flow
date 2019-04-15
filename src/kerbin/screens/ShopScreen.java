package kerbin.screens;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Set;
import java.util.SplittableRandom;
//Базовый класс инвентаря, выводит предметы TODO Крафт(?)
import asciiPanel.AsciiPanel;
import kerbin.Creature;
import kerbin.items.Armor;
import kerbin.items.Item;
import kerbin.items.Usable;
import kerbin.items.Weapon;

public class ShopScreen implements Screen {
    protected Creature player;
    protected Creature merchant;
    protected char[] alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    protected int i;
    private String msg;
    protected int chosen;
    public ShopScreen(Creature player, Creature merchant) {
        this.player = player;
        this.merchant = merchant;
        chosen = 0;
        msg = "";
    }
    //Показывает заголовки инвентаря и надетую экипировку, используется в наследниках
    protected void showHeader(AsciiPanel terminal)
    {
        int y = 4;
        int x = 3;
        i = 0;
        terminal.clear();
        terminal.write("Shop gold: ", 1, 1, Color.WHITE);
        terminal.write(merchant.gold+"", 12, 1, Color.ORANGE);
        terminal.write("Your gold: ", 28, 1, Color.WHITE);
        terminal.write(player.gold+"", 40, 1, Color.ORANGE);
        terminal.write("Shop: ", 1, 2, Color.WHITE);
        terminal.write("Inventory: ", 28, 2, Color.WHITE);
        terminal.write("Equipped: ", 55, 2, Color.WHITE);
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
        terminal.clear();

        showHeader(terminal);
        int y = 4;
        int x = 28;
        i = 0;
        int my = 4;
        int mx = 4;
        for (Item item : merchant.inv) {
            showItem(terminal, item, mx, my);
            my++;
            i++;
        }
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
        terminal.write(msg, 0, 39, AsciiPanel.brightGreen);
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
                    terminal.write("HP: ", 65, 10, Color.WHITE);
                    terminal.write("-50", 69, 10, Color.RED);
                }
                terminal.write(((Weapon) it).dmg + "", 60, 10, Color.GREEN);
            } else if (it instanceof Armor) {
                terminal.write("DEF:", 55, 10, Color.WHITE);
                terminal.write(((Armor) it).def + "", 60, 10, Color.BLUE);
            } else if (it instanceof Usable) {
                terminal.write("HP: ", 55, 10, Color.WHITE);
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
        char c = key.getKeyChar();
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                return null;
            case KeyEvent.VK_DOWN:
                if (chosen < i-1) chosen+=1;
                break;
            case KeyEvent.VK_UP:
                if (chosen > 0) chosen-=1;
                break;
            case KeyEvent.VK_ENTER:
                if (chosen < merchant.inv.size()) {
                    if (player.gold >= merchant.inv.get(chosen).cost) {
                        Item sold = merchant.inv.get(chosen);
                        if (sold.modifier != null)
                            msg = String.format("You bought a %s %s for  %s gold", sold.modifier[0], sold.name(), sold.cost);
                        else
                            msg = String.format("You bought a %s for  %s gold", sold.name(), sold.cost);
                        player.gold -= sold.cost;
                        merchant.gold += sold.cost;
                        sold.cost *= 0.83;
                        player.inv.add(sold);
                        merchant.inv.remove(sold);
                        if (chosen > 0) chosen -= 1;
                    }
                    else {msg = "You have not enough gold";}
                }
                else {
                    if (merchant.gold >= player.inv.get(chosen - merchant.inv.size()).cost) {
                        Item sold = player.inv.get(chosen - merchant.inv.size());
                        if (sold.modifier != null)
                            msg = String.format("You sold a %s %s for  %s gold", sold.modifier[0], sold.name(), sold.cost);
                        else
                            msg = String.format("You sold a %s for  %s gold", sold.name(), sold.cost);
                        merchant.gold -= sold.cost;
                        player.gold += sold.cost;
                        sold.cost *= 1.2;
                        merchant.inv.add(sold);
                        player.inv.remove(sold);
                        if (chosen > 0) chosen -= 1;
                    }
                    else {msg = "Merchant doesn't have enough gold";}
                }
                break;
        }
        return this;
    }
}