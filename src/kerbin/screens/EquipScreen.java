package kerbin.screens;

import asciiPanel.AsciiPanel;
import kerbin.creatures.Creature;
import kerbin.items.Armor;
import kerbin.items.Item;
import kerbin.items.Ranged;
import kerbin.items.Weapon;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

//Класс экрана экипирования предметов, выводит предметы, которые можно надеть

public class EquipScreen extends InventoryScreen implements Screen {
    private String msg;
    private String c_string;
    private List<String> alph_short;

    public EquipScreen(Creature player) {
        super(player);
        msg = "";
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        int y = 4;
        int x = 4;
        i = 0;
        super.showHeader(terminal);
        terminal.write("Choose weapon or armor: ", 1, 2);

        for (Item it : player.inv) {
            if (it.isEquipable) {
                this.showItem(terminal, it, x, y);
                y++;
                i++;
                if (i != 0 && i % 36 == 0) {
                    y = 4;
                    x += 25;
                }
            }
        }

        terminal.clear(' ', 0, 38, 80, 1);
        terminal.write(msg, 0, 39, AsciiPanel.brightGreen);
        terminal.repaint();
    }

    public void wearItem() {
        Item Equipable = null;
        int j = 0;
        for (int z = 0; z < player.inv.size(); z++) {
            if (player.inv.get(z).isEquipable) {
                if (alph_short.indexOf(c_string) == j) {
                    Equipable = player.inv.get(z);
                    break;
                }
                j++;
            }
        }
        if (Equipable instanceof Weapon) {
            player.setWeapon((Weapon) Equipable);
            player.inv.remove(Equipable);
            if (Equipable.modifier != null)
                msg = String.format("You took a %s %s in hands", Equipable.modifier[0], Equipable.name());
            else msg = String.format("You took a %s in hands", Equipable.name());

        } else if (Equipable instanceof Ranged) {
            player.setWeapon((Ranged) Equipable);
            player.inv.remove(Equipable);
            if (Equipable.modifier != null)
                msg = String.format("You took a %s %s in hands", Equipable.modifier[0], Equipable.name());
            else msg = String.format("You took a %s in hands", Equipable.name());

        } else if (Equipable instanceof Armor) {
            player.setArmor((Armor) Equipable);
            player.inv.remove(Equipable);
            if (Equipable.modifier != null)
                msg = String.format("You now wear %s %s", Equipable.modifier[0], Equipable.name());
            else msg = String.format("You now wear %s", Equipable.name());
        }
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        char c = key.getKeyChar();
        //Срез алфавита, равный количеству одеваемых предметов в инвентаре
        alph_short = Arrays.asList("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".substring(0, i).split(""));
        c_string = Character.toString(c);
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                return new InventoryScreen(player);
            case KeyEvent.VK_DOWN:
                if (chosen < i - 1) chosen += 1;
                break;
            case KeyEvent.VK_UP:
                if (chosen > 0) chosen -= 1;
                break;
            case KeyEvent.VK_ENTER:
                if (player.inv.size() > 0) {
                    c_string = alph_short.get(chosen);
                    if (chosen > 0) chosen -= 1;
                }
                break;
        }
        if (alph_short.contains(c_string)) {
            this.wearItem();
        }
        return this;
    }
}