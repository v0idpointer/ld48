package me.v0idpointer.ld48;

import me.v0idpointer.ld48.gfx.Font;
import me.v0idpointer.ld48.gfx.SpriteSheet;
import me.v0idpointer.ld48.gfx.Frame;
import me.v0idpointer.ld48.hud.HUD;
import me.v0idpointer.ld48.input.KeyboardInput;
import me.v0idpointer.ld48.input.MouseInput;
import me.v0idpointer.ld48.item.Item;
import me.v0idpointer.ld48.menu.GameCutscene;
import me.v0idpointer.ld48.menu.MainMenu;
import me.v0idpointer.ld48.spell.Spell;
import me.v0idpointer.ld48.tile.Tile;
import me.v0idpointer.ld48.window.Window;

import javax.swing.*;

public class Game {

    public static final int WIDTH = 800, HEIGHT = 600;
    public static final String TITLE = "LD48";

    private final Window window;
    private final DisplayComponent displayComponent;

    private boolean isRunning = false;
    private Thread thread;

    private Font font;
    private Frame frame;
    private KeyboardInput keyboardInput;
    private HUD hud;
    private MouseInput mouseInput;
    private MainMenu menu;
    private GameCutscene cutscene;

    public static Game instance = null;
    public final int flags;

    public GameState state = GameState.MAIN_MENU;

    public Game(int flags) {
        this.flags = flags;
        if(!(System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) && ((flags & 1) == 0)) {
            JOptionPane.showMessageDialog(null, "Sorry, but your operation system is not supported.", "LD48", 0);
            System.exit(2);
        }

        this.window = new Window(WIDTH, HEIGHT, TITLE);
        this.displayComponent = new DisplayComponent(this, window);
        System.out.println("Window and DisplayComponent initialized.");

        this.font = new Font("progressive", SpriteSheet.TERRAIN);
        this.frame = new Frame(SpriteSheet.TERRAIN);
        this.hud = new HUD(this);
        System.out.println("Game components initialized.");

        this.keyboardInput = new KeyboardInput();
        this.displayComponent.addKeyListener(this.keyboardInput);
        System.out.println("Keyboard input initialized.");

        this.mouseInput = new MouseInput(this);
        this.displayComponent.addMouseListener(this.mouseInput);
        System.out.println("Mouse input initialized.");

        this.menu = new MainMenu(this);
        this.displayComponent.addMouseListener(this.menu);
        this.cutscene = new GameCutscene(this);
        System.out.println("Menu components initialized.");

        System.out.println(Tile.tileCounter + "/" + Tile.tiles.length + " tile(s) registered.");
        System.out.println(Item.itemCounter + "/" + Item.items.length + " item(s) registered.");
        System.out.println(Spell.spellCounter + "/" + Spell.spells.length + " spell(s) registered.");

        instance = this;
        this.start();
        this.displayComponent.start();
        System.out.println("Ready!");
    }

    public synchronized void start() {
        if(this.isRunning) return;

        this.isRunning = true;
        this.thread = new Thread(this::run, "gameThread");
        this.thread.start();
    }

    public synchronized void stop() {
        if(!this.isRunning) return;

        try {
            this.isRunning = false;
            this.thread.join();
        }catch(InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double ns = 1000000000 / 64.0;
        double delta = 0;
        long timer = System.currentTimeMillis();

        while(this.isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while(delta >= 1) {
                this.update();
                delta--;
            }

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                this.displayComponent.updateFrameCounter();
            }
        }

        this.stop();
    }

    public void update() {
        if(((this.flags >> 4) & 1) == 1) this.mouseInput.renderPopDebugPoints();
        this.mouseInput.time();
        if(!this.displayComponent.hasFocus() && this.state == GameState.GAME) {
            this.displayComponent.unfocusedUpdate();
            return;
        }

        this.displayComponent.update();
    }

    public Window getWindow() {
        return this.window;
    }

    public DisplayComponent getDisplayComponent() {
        return this.displayComponent;
    }

    public Font getFont() {
        return this.font;
    }

    public Frame getFrame() {
        return this.frame;
    }

    public KeyboardInput getKeyboardInput() {
        return this.keyboardInput;
    }

    public HUD getHUD() {
        return this.hud;
    }

    public MouseInput getMouseInput() {
        return this.mouseInput;
    }

    public MainMenu getMenu() {
        return this.menu;
    }

    public GameCutscene getCutscene() {
        return this.cutscene;
    }

}
