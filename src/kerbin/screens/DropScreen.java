package kerbin.screens;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;
//Экран выбрасывания предметов
import asciiPanel.AsciiPanel;
import kerbin.Creature;
import kerbin.items.Item;

public class DropScreen extends InventoryScreen implements Screen{
    private String msg;
    private String c_string;
    private List<String> alph_short;
    public DropScreen(Creature player) {
        super(player);
        msg = "";
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        showHeader(terminal);
        terminal.write("Choose item to drop: ", 1, 2);
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
        terminal.clear(' ', 0, 38, 80, 1);
        terminal.write(msg, 0, 39, AsciiPanel.brightGreen);
        terminal.repaint();
    }
    public void dropItem()
    {
        Item drop=null;
        int j=0;
        for (int z = 0; z < player.inv.size(); z++) {
                if (alph_short.indexOf(c_string) == j)
                {
                    drop=player.inv.get(z);
                    break;
                }
                j++;
        }
        if (player.getWorld().tile(player.x, player.y).item == null) {
            player.inv.remove(drop);
            player.getWorld().tile(player.x, player.y).item = drop;
            if (drop.modifier != null)
                msg = String.format("You throw %s %s away", drop.modifier[0], drop.name());
            else msg = String.format("You throw %s away", drop.name());
        }
        else msg = String.format("You can't throw %s away, something is in the way", drop.name());

    }


    @Override
    public Screen respondToUserInput(KeyEvent key) {
        char c = key.getKeyChar();
        //Срез алфавита, равный количеству предметов в инвентаре
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
                if (player.inv.size() > 0)
                {
                    c_string = alph_short.get(chosen);
                    if (chosen > 0) chosen-=1;
                }
                break;
        }
        if (alph_short.contains(c_string))
        {
            this.dropItem();
        }
        return this;
    }
}