package kerbin.screens;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;
//Класс экрана экипирования предметов, выводит предметы, которые можно надеть
import asciiPanel.AsciiPanel;
import kerbin.Creature;
import kerbin.items.Armor;
import kerbin.items.Item;
import kerbin.items.Weapon;

public class EquipScreen extends InventoryScreen implements Screen{
    private int i;
    private String msg;
    public EquipScreen(Creature player) {
        super(player);
        int i = 0;
        msg = "";
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        int y = 4;
        int x = 3;
        i = 0;
        super.showHeader(terminal);
        terminal.write("Choose weapon or armor: ", 1, 2);

        for (Item it : player.inv) {
            if (it.isEquipable) {
                terminal.write(alphabet[i] + ":  ", x - 2, y);
                terminal.write(it.name(), x, y++);
                i++;
            }
        }

        terminal.clear(' ', 0, 23, 80, 1);
        terminal.write(msg, 0, 22, AsciiPanel.brightGreen);
        terminal.repaint();
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        char c = key.getKeyChar();
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                return new InventoryScreen(player);
        }
        //Срез алфавита, равный количеству одеваемых предметов в инвентаре
        List<String> alph_short = Arrays.asList("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".substring(0, i).split(""));
        String c_string = Character.toString(c);
        if (alph_short.contains(c_string))
        {
            Item Equipable=null;
            int j=0;
            for (int z = 0; z < player.inv.size(); z++) {
                if (player.inv.get(z).isEquipable)
                {
                    if (alph_short.indexOf(c_string) == j)
                    {
                        Equipable=player.inv.get(z);
                        break;
                    }
                    j++;
                }
            }
            if (Equipable instanceof Weapon)
            {
                player.setWeapon((Weapon)Equipable);
                player.inv.remove(Equipable);
                msg = String.format("You took a %s in hands", Equipable.name());
            }
            else if (Equipable instanceof Armor)
            {
                player.setArmor((Armor)Equipable);
                player.inv.remove(Equipable);
                msg = String.format("You now wear %s", Equipable.name());
            }
        }
        return this;
    }
}