package kerbin.screens;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;
//Экран использования предметов
import asciiPanel.AsciiPanel;
import kerbin.Creature;
import kerbin.items.Item;
import kerbin.items.Usable;

public class UseScreen extends InventoryScreen implements Screen{
    private String msg;
    private List<String> alph_short;
    public String c_string;
    public UseScreen(Creature player) {
        super(player);
        msg = "";
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        int y = 4;
        int x = 4;
        i = 0;
        super.showHeader(terminal);
        terminal.write("Choose usable item: ", 1, 2);

        for (Item it : player.inv) {
            if (it instanceof Usable) {
                showItem(terminal, it, x, y);
                y++;
                i++;
                if (i != 0 && i % 36 == 0)
                {
                    y = 4;
                    x += 25;
                }
            }
        }

        terminal.clear(' ', 0, 38, 80, 1);
        terminal.write(msg, 0, 39, AsciiPanel.brightGreen);
        terminal.repaint();
    }

    public void useItem() {
        Usable usable;
        int j = 0;
        for (int z = 0; z < player.inv.size(); z++) {
            if (player.inv.get(z) instanceof Usable) {
                if (alph_short.indexOf(c_string) == j) {
                    usable = (Usable) (player.inv.get(z));
                    //Использует предмет и возвращает сообщение
                    msg = usable.use();
                }
                j++;
            }
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
                if (chosen < i-1) chosen+=1;
                break;
            case KeyEvent.VK_UP:
                if (chosen > 0) chosen-=1;
                break;
            case KeyEvent.VK_ENTER:
                if (player.inv.size() > 0) {
                    c_string = alph_short.get(chosen);
                    if (chosen > 0) chosen-=1;
                    break;
                }

        }
        if (alph_short.contains(c_string))
        {
            useItem();
        }
        return this;
    }
}