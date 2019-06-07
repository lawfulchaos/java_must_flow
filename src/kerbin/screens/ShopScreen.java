package kerbin.screens;

import asciiPanel.AsciiPanel;
import kerbin.ApplicationMain;

import kerbin.creatures.Creature;
import kerbin.items.Armor;
import kerbin.items.Item;
import kerbin.items.Usable;
import kerbin.items.Weapon;
import kerbin.projectiles.ProjectileAi;

import java.awt.*;
import java.awt.event.KeyEvent;

//Базовый класс инвентаря, выводит предметы

public class ShopScreen implements Screen {
    protected Creature player;
    protected int i;
    private Creature merchant;
    private char[] alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private String msg;
    private int chosen;

    ShopScreen(Creature player, Creature merchant) {
        this.player = player;
        this.merchant = merchant;
        chosen = 0;
        msg = "";
        if (Math.random()>0.375) ApplicationMain.playSound("Chtopritashil.wav");
        else ApplicationMain.playSound("habar.wav");
    }

    //Показывает заголовки инвентаря и надетую экипировку, используется в наследниках
    private void showHeader(AsciiPanel terminal) {
        i = 0;
        terminal.clear();
        terminal.write("Shop gold: ", 1, 1, Color.WHITE);
        terminal.write(merchant.gold + "", 12, 1, Color.ORANGE);
        terminal.write("Your gold: ", 28, 1, Color.WHITE);
        terminal.write(player.gold + "", 40, 1, Color.ORANGE);
        terminal.write("Shop: ", 1, 2, Color.WHITE);
        terminal.write("Inventory: ", 28, 2, Color.WHITE);
        terminal.write("Equipped: ", 55, 2, Color.WHITE);
        InventoryScreen.showEquiped(terminal, player);
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.clear();

        showHeader(terminal);
        int y = 4;
        int x = 32;
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
            if (i != 0 && i % 36 == 0) {
                y = 4;
                x += 25;
            }
        }
        terminal.write(msg, 0, 39, AsciiPanel.brightGreen);
        terminal.repaint();
    }

    //Отображение предмета, используется в наследниках
    public void showItem(AsciiPanel terminal, Item it, int ix, int iy) {
        if (this.i == chosen) {
            terminal.write("> ", ix - 4, iy, Color.WHITE);
            if (it instanceof Weapon) {
                terminal.write("DMG:", 55, 10, Color.WHITE);
                if (it.modifier != null && it.modifier[0] == "Cursed" && it.name() == "Battleaxe") {
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
                x += word.length() + 1;
            }
        }
        terminal.write(alphabet[this.i] + ":  ", ix - 2, iy);
        if (it.modifier != null) {
            String modifier = (String) it.modifier[0];
            terminal.write(modifier, ix, iy, (Color) it.modifier[1]);
            ix += modifier.length() + 1;
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
            case KeyEvent.VK_S:
                if (chosen < i - 1) chosen += 1;
                break;
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                if (chosen > 0) chosen -= 1;
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                if (chosen < merchant.inv.size() && chosen < player.inv.size())
                    chosen += merchant.inv.size();
                else if (chosen >= merchant.inv.size() && chosen - merchant.inv.size() < merchant.inv.size())
                    chosen -= merchant.inv.size();
                else if (chosen < merchant.inv.size())
                    chosen = merchant.inv.size() + player.inv.size() - 1;
                else
                    chosen = merchant.inv.size() - 1;
                break;
            case KeyEvent.VK_ENTER:
                if (chosen < merchant.inv.size()) {
                    if (player.gold >= merchant.inv.get(chosen).cost) {
                        Item sold = merchant.inv.get(chosen);
                        if (sold.modifier != null)
                            msg = String.format("You bought a %s %s for  %s gold", sold.modifier[0], sold.name(), sold.cost);
                        else
                            msg = String.format("You bought a %s for  %s gold", sold.name(), sold.cost);
                        makePurchase(sold, player, merchant, 0.83);
                    } else {
                        msg = "You have not enough gold";
                        ApplicationMain.playSound("gold.wav");
                    }
                } else {
                    if (merchant.gold >= player.inv.get(chosen - merchant.inv.size()).cost) {
                        Item sold = player.inv.get(chosen - merchant.inv.size());
                        if (sold.modifier != null)
                            msg = String.format("You sold a %s %s for  %s gold", sold.modifier[0], sold.name(), sold.cost);
                        else
                            msg = String.format("You sold a %s for  %s gold", sold.name(), sold.cost);
                        makePurchase(sold, merchant, player, 1.2);
                    } else {
                        msg = "Merchant doesn't have enough gold";
                        ApplicationMain.playSound("gold.wav");
                    }
                }
                break;
        }
        return this;
    }

    private void makePurchase(Item sold, Creature merchant, Creature player, double v) {
        merchant.gold -= sold.cost;
        player.gold += sold.cost;
        sold.cost *= v;
        merchant.inv.add(sold);
        player.inv.remove(sold);
        sold.owner = merchant;
        if (chosen > 0) chosen -= 1;
    }
}