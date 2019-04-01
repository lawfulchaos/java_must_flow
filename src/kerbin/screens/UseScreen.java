package kerbin.screens;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;
//Экран использования предметов, TODO Почистить код
import asciiPanel.AsciiPanel;
import kerbin.Creature;
import kerbin.items.Item;
import kerbin.items.Usable;

public class UseScreen extends InventoryScreen implements Screen{
    private int i;
    private String msg;
    public UseScreen(Creature player) {
        super(player);
        i = 0;
        msg = "";
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        int y = 4;
        int x = 3;
        i = 0;
        super.showHeader(terminal);
        terminal.write("Choose usable item: ", 1, 2);

        for (Item it : player.inv) {
            if (it instanceof Usable) {
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
            Usable usable;
            int j=0;
            for (int z = 0; z < player.inv.size(); z++) {
                if (player.inv.get(z) instanceof Usable)
                {
                    if (alph_short.indexOf(c_string) == j)
                    {
                        usable = (Usable)(player.inv.get(z));
                        usable.use();
                        msg = String.format("You used a %s, now you have %d HP", usable.name(), player.hp);
                        break;
                    }
                    j++;
                }
            }
        }
        return this;
    }
}