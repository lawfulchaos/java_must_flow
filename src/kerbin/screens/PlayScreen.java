package kerbin.screens;
/* Основной игровой экран. содержит игровую логику, отображение и обработку клавиатуры */

import asciiPanel.AsciiPanel;
import kerbin.AI.MouseAi;
import kerbin.Event;
import kerbin.creatures.Creature;
import kerbin.creatures.CreatureFactory;
import kerbin.items.*;
import kerbin.projectiles.Projectile;
import kerbin.world.World;
import kerbin.world.WorldBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.List;

public class PlayScreen implements Screen {
    public Creature player;
    private World world;
    private int screenWidth;
    private int screenHeight;
    private Screen subscreen;
    private JFrame frame;
    private boolean isShooting;

    /* Генерация нового экрана и добавление игрока*/
    PlayScreen(JFrame frame) {
        screenWidth = 90;
        this.frame = frame;
        screenHeight = 30;
        isShooting = false;
        createWorld();
        this.player = new CreatureFactory(world).newPlayer();
        setPlayer(this.player);
        Event.getInstance().init("You are playing WORK now", 0, -1, AsciiPanel.brightWhite);

    }

    private PlayScreen(Creature player) {
        screenWidth = 90;
        screenHeight = 30;
        isShooting = false;
        this.player = player;
        if (player.level % 3 == 0) createBossWorld();
        else createWorld();
        setPlayer(this.player);
        Event.getInstance().init("You are playing WORK now", 0, -1, AsciiPanel.brightWhite);
    }

    public void setPlayer(Creature player) {
        this.player = player;
        world.player = player;
        player.setWorld(world);
        if (player.level % 3 != 0) {
            world.addAtEmptyLocation(player);
            world.populateWorld();
        } else {
            world.addAtBossLevel(player);
            world.populateBossWorld();
        }
    }

    //Создает мир, внутри мира генерируются мобы и игрок
    private void createWorld() {
        world = new WorldBuilder(90, 32).build();
    }

    private void createBossWorld() {
        world = new WorldBuilder(90, 32).buildBossLevel();
    }

    // Сдвиг экрана при движении игрока
    private int getScrollX() {
        return Math.max(0, Math.min(player.x - screenWidth / 2, world.width() - screenWidth));
    }

    private int getScrollY() {
        return Math.max(0, Math.min(player.y - screenHeight / 2, world.height() - screenHeight));
    }

    //Вывод игрового поля со сдвигом
    @Override
    public void displayOutput(AsciiPanel terminal) {


        int left = getScrollX();
        int top = getScrollY();

        displayTiles(terminal, left, top);

        //Рамочка
        for (int dy = 30; dy < 39; dy++)
            terminal.write((char) 186, 89, dy, Color.cyan);

        for (int dx = 0; dx < 90; dx++) {
            terminal.write((char) 196, dx, 30, Color.cyan);
            if ((dx % 21 == 0) && (dx / 21 != 4)) {
                for (int dy = 30; dy < 39; dy++)
                    terminal.write((char) 186, dx, dy, Color.cyan);
                terminal.write((char) 203, dx, 30, Color.cyan);
                terminal.write((char) 202, dx, 38, Color.cyan);
            } else
                terminal.write((char) 196, dx, 38, Color.cyan);
        }


        terminal.write((char) 187, 89, 30, Color.cyan);
        terminal.write((char) 202, 89, 38, Color.cyan);
        terminal.write((char) 201, 0, 30, Color.cyan);


        //Шмат хпшки
        terminal.write((char) 157 + " YOUR HEALTHPOINT " + (char) 157, 1, 31, Color.green);
        for (int k = 0; k < 20; k++) {
            double dobhp = (float) player.hp / player.max_hp * 20;
            if (k > ((float) dobhp - 0.5))
                terminal.write((char) 254, k + 1, 32, Color.red);
            else terminal.write((char) 254, k + 1, 32, Color.green);
        }
        terminal.write(Integer.toString(player.hp), 9, 33, Color.green);


        // DEF out
        terminal.write((char) 177 + "YOUR  DEFENCE" + (char) 177, 25, 31, Color.green);
        if (player.def > 1) {
            for (int k = 0; k < 20; k++) {
                double dobdef = (float) player.def / player.startdef * 20;
                if (k > ((float) dobdef)) // Показывает процент целой брони от общей.
                    terminal.write((char) 176, k + 22, 32, Color.red);
                else terminal.write((char) 178, k + 22, 32, Color.blue);
            }
            terminal.write(Integer.toString(player.def), 30, 33, Color.green);

        } else {
            terminal.write("0", 31, 33, Color.green);
            terminal.write("Nope", 30, 32, Color.red);
        }


        //damage out

        terminal.write("-|YOUR WEAPON|-", 46, 31, Color.green);
        if (player.weapon != null)
            terminal.write(player.weapon.name(), 53 - player.weapon.name().length() / 2, 32, Color.magenta);
        else terminal.write("Fists", 51, 32, Color.magenta);
        terminal.write(Integer.toString(player.dmg), 53, 33, Color.green);


        terminal.write("Map:", 65, 31, Color.green);
        terminal.write(Integer.toString(player.level), 82, 31, Color.green);

        terminal.write("Player level:", 65, 32, Color.green);
        terminal.write(Integer.toString(player.player_level), 82, 32, Color.green);


        String honors = String.format("/ %d", player.max_honor);
        terminal.write("Honor:", 65, 33, Color.green);
        if (player.honor > 0)
            terminal.write(Integer.toString(player.honor), 80 - ((int) (Math.log10(player.honor))), 33, Color.green);
        else terminal.write(Integer.toString(player.honor), 80, 33, Color.green);
        terminal.write(honors, 82, 33, Color.green);


        terminal.write("Gold:", 65, 34, Color.green);
        if (player.gold > 0)
            terminal.write(Integer.toString(player.gold), 82 - ((int) (Math.log10(player.gold))) / 2, 34, Color.green);
        else terminal.write(Integer.toString(player.gold), 82, 34, Color.green);

        terminal.write("Ammo:", 65, 35, Color.green);
        if (player.ammo > 10)
            terminal.write(Integer.toString(player.ammo), 81 - ((int) (Math.log10(player.ammo))) / 2, 35, Color.green);
        else terminal.write(Integer.toString(player.ammo), 82, 35, Color.green);

        terminal.write(Event.getInstance().getMsg(), 0, 39, Event.getInstance().getColor());
        if (subscreen != null)
            subscreen.displayOutput(terminal);
    }

    private void displayTiles(AsciiPanel terminal, int left, int top) {
        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {
                int wx = x + left;
                int wy = y + top;
                terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
                if (world.tiles[wx][wy].item != null) {
                    terminal.write(world.tiles[wx][wy].item.glyph(), x, y, world.tiles[wx][wy].item.color());
                }
            }
        }
        for (Creature c : world.creatures) {
            drawChar(terminal, left, top, c.x, c.y, c.glyph(), c.color());
        }
        for (Creature c : world.npcs) {
            drawChar(terminal, left, top, c.x, c.y, c.glyph(), c.color());
        }
        for (Projectile p : world.projectiles) {
            drawChar(terminal, left, top, p.x, p.y, p.glyph(), p.color());
        }
        if (isShooting) {
            if (world.tile(player.x, player.y - 1).isGround())
                terminal.write('^', player.x - left, player.y - top - 1, AsciiPanel.brightWhite);
            if (world.tile(player.x, player.y + 1).isGround())
                terminal.write('v', player.x - left, player.y - top + 1, AsciiPanel.brightWhite);
            if (world.tile(player.x + 1, player.y).isGround())
                terminal.write('>', player.x - left + 1, player.y - top, AsciiPanel.brightWhite);
            if (world.tile(player.x - 1, player.y).isGround())
                terminal.write('<', player.x - left - 1, player.y - top, AsciiPanel.brightWhite);
        }
        terminal.write(player.glyph(), player.x - left, player.y - top, player.color());
    }

    private void drawChar(AsciiPanel terminal, int left, int top, int x, int y, char glyph, Color color) {
        if ((x >= left && x < left + screenWidth) && (y >= top && y < top + screenHeight)) {
            terminal.write(glyph, x - left, y - top, color);
        }
    }

    void saveGame() {
        try {
            File f = new File("\\saves\\save.txt");

            f.getParentFile().mkdirs();
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(new File("\\saves\\save.dat"));
            ObjectOutputStream o = new ObjectOutputStream(fo);

            o.writeObject(world);
            o.close();
            fo.close();
            Event.getInstance().init("Game saved", 2, 3, AsciiPanel.brightGreen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void loadGame() {
        try {
            FileInputStream fi = new FileInputStream(new File("\\saves\\save.dat"));
            ObjectInputStream oi = new ObjectInputStream(fi);

            // Read objects
            world = (World) oi.readObject();
            player = world.player;
            for (int i = 0; i < world.creatures.size(); i++) {
                if (world.creatures.get(i).ai instanceof MouseAi)
                    System.out.println(world.creatures.get(i));
            }
            oi.close();
            fi.close();
            Event.getInstance().init("Game loaded", 2, 3, AsciiPanel.brightGreen);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Item> updateItems(List<Item> items) {
        for (Item item : items) {
            if (item instanceof Weapon) {
                ((Weapon) item).dmg *= (player.level * 0.8);
                ((Weapon) item).cost *= (player.level * 0.8);
            } else if (item instanceof Armor) {
                ((Armor) item).def *= (player.level * 0.8);
                ((Armor) item).cost *= (player.level * 0.8);
            } else if (item instanceof Usable) {
                ((Usable) item).effect *= (player.level * 0.8);
                ((Usable) item).cost *= (player.level * 0.8);
            }
        }
        return items;
    }

    //Реакция на нажатие клавиши: ход нпс, после движение игрока
    @Override
    public Screen respondToUserInput(KeyEvent key) {
        if (player.hp <= 0) {
            return new LoseScreen(frame);
        }
        if (subscreen != null) {
            subscreen = subscreen.respondToUserInput(key);
            return this;
        }
        //стрельба
        else if (isShooting) {
            if (player.weapon == null) {
                isShooting = false;
                return this;
            }
            switch (key.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    if (world.tile(player.x - 1, player.y).isGround())
                        player.getWorld().projectileFactory.newBullet(player, player.x - 1, player.y, -1, 0, -1, 0, player.weapon.dmg * 3);
                    player.ammo -= 1;
                    player.weapon.durability -= 1;
                    isShooting = false;
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    if (world.tile(player.x + 1, player.y).isGround())
                        player.getWorld().projectileFactory.newBullet(player, player.x + 1, player.y, +1, 0, +1, 0, player.weapon.dmg * 2);
                    player.ammo -= 1;
                    player.weapon.durability -= 1;
                    isShooting = false;
                    break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    if (world.tile(player.x, player.y - 1).isGround())
                        player.getWorld().projectileFactory.newBullet(player, player.x, player.y - 1, 0, -1, 0, -1, player.weapon.dmg * 2);
                    player.ammo -= 1;
                    player.weapon.durability -= 1;
                    isShooting = false;
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    if (world.tile(player.x, player.y + 1).isGround())
                        player.getWorld().projectileFactory.newBullet(player, player.x, player.y + 1, 0, 1, 0, 1, player.weapon.dmg * 2);
                    player.ammo -= 1;
                    player.weapon.durability -= 1;
                    isShooting = false;
                    break;
                case KeyEvent.VK_ESCAPE:
                    isShooting = false;
                    break;
            }
            if (player.weapon.durability < 0) {
                int priority = 4;
                if (priority >= Event.getInstance().getPriority()) {
                    if (player.weapon.modifier == null) Event.getInstance()
                            .init(String.format("Your %s is broken", player.weapon.name()), priority, 3, new Color(255, 88, 0));
                    else {
                        Event.getInstance()
                                .init(String.format("Your %s %s is broken", player.weapon.modifier[0], player.weapon.name()), priority, 3, new Color(255, 88, 0));
                    }
                }
                player.dmg -= player.weapon.dmg;
                player.weapon = null;
            }
            if (player.ammo < 0) player.ammo = 0;
            return this;
        } else {
            //Проверяем, если нажата клавиша не из списка управления - игноририруем.
            boolean isAction = true;
            switch (key.getKeyCode()) {
                case KeyEvent.VK_ESCAPE:
                    isAction = false;
                    return new MenuScreen(frame, this);
                case KeyEvent.VK_ENTER:
                    if (player.getWorld().tile(player.x, player.y).glyph() == '#' && player.getWorld().creatures.size() == 0) {
                        player.level++;
                        PlayScreen nextLvl = new PlayScreen(player);
                        for (Creature creature : nextLvl.world.creatures) {
                            creature.hp *= player.level;
                            creature.dmg *= player.level;
                        }
                        nextLvl.world.items = updateItems(nextLvl.world.items);
                        for (Creature npc : nextLvl.world.npcs) {
                            npc.inv = updateItems(npc.inv);
                        }
                        return nextLvl;
                    } else if (player.getWorld().checkMerchant(player.x, player.y) != null)
                        subscreen = new ShopScreen(player, player.getWorld().checkMerchant(player.x, player.y));
                    break;

                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    player.moveBy(-1, 0);
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    player.moveBy(1, 0);
                    break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    player.moveBy(0, -1);
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    player.moveBy(0, 1);
                    break;
                case KeyEvent.VK_I: //инвентарь
                    isAction = false;
                    subscreen = new InventoryScreen(player);
                    break;
                case KeyEvent.VK_F: //Стрельба
                    if (player.weapon instanceof Ranged) {
                        if (player.ammo < 1) {
                            Event.getInstance().init("Not enough ammo", 2, 2, AsciiPanel.red);
                            isAction = false;
                        } else {
                            Event.getInstance().init("Set direction", 3, 1, AsciiPanel.white);
                            isShooting = true;
                        }
                    }
                    break;
                case KeyEvent.VK_5:
                    player.moveBy(0, 0);
                    break;
                // Сейвлоад
                case KeyEvent.VK_F5:
                    isAction = false;
                    saveGame();
                    break;
                case KeyEvent.VK_F9:
                    isAction = false;
                    loadGame();
                    break;
                default:
                    isAction = false;
                    break;
            }
            if (isAction) {
                for (int j = 0; j < world.creatures.size(); j++) {
                    world.creatures.get(j).ai.onTurn(player);
                }
                for (int j = 0; j < world.npcs.size(); j++) {
                    world.npcs.get(j).ai.onTurn(player);
                }
                for (int j = 0; j < world.projectiles.size(); j++) {
                    world.projectiles.get(j).ai.onTurn(world);
                }
                player.ai.onTurn(player);
            }

            if (Event.getInstance().getLifetime() == 0) {
                Event.getInstance().init("Welcome to MGUPI Roguelike", 0, -1, AsciiPanel.brightWhite);
            } else Event.getInstance().decreaseLifetime();
            return this;
        }
    }
}
