package me.v0idpointer.ld48;

import me.v0idpointer.ld48.window.Window;
import me.v0idpointer.ld48.world.World;
import me.v0idpointer.ld48.world.WorldGeneration;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class DisplayComponent extends Canvas {

    private final Game gameComponent;
    private final Window window;

    private boolean isRunning = false;
    private Thread thread;

    private int frames = 0;
    private int frameCounter = 0;
    private int focusTimer = 0;
    private int flashTimer = 0;

    public World world;

    public DisplayComponent(Game gameComponent, Window window) {
        this.gameComponent = gameComponent;
        this.window = window;

        this.window.getFrame().add(this);

        this.world = WorldGeneration.generate(0, null);
    }

    public synchronized void start() {
        if(this.isRunning) return;

        this.isRunning = true;
        this.thread = new Thread(this::run, "renderThread");
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
        while(this.isRunning) this.render();
    }

    public void update() {
        if(this.gameComponent.state == GameState.GAME) {
            this.world.update();
            this.gameComponent.getHUD().update();
            if(this.flashTimer > 0) this.flashTimer--;
        } else if(this.gameComponent.state == GameState.MAIN_MENU) {
            this.gameComponent.getMenu().update();
        } else if(this.gameComponent.state == GameState.GAME_CUTSCENE) {
            this.gameComponent.getCutscene().update();
        }
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();

        if(bs == null) {
            this.createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

        if(this.gameComponent.state == GameState.GAME) {
            this.world.render(g);
            this.gameComponent.getHUD().render(g);

            if(((this.gameComponent.flags >> 3) & 1) == 1) {
                g.setColor( (this.frameCounter > 60) ? Color.white : Color.red);
                g.drawString("LD48: " + this.frameCounter + " FPS, 64 ticks.", 16,16);

                if(((this.gameComponent.flags >> 4) & 1) == 1) {
                    this.gameComponent.getMouseInput().renderDebugInformation(g);
                }
            }

            if((((this.gameComponent.flags >> 1) & 1) == 1)) {
                this.gameComponent.getFont().RenderString(g, "AI Disabled.", 32, 128, 2, 0xffffffff);
            }

            if(this.flashTimer > 0) {
                g.setColor(Color.white);
                g.fillRect(0, 0, 800, 600);
            }

            if(!this.hasFocus()) {
                Graphics2D g2d = (Graphics2D)g;
                if(((Game.instance.flags >> 6) & 1) != 1) {
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.65f));
                    g2d.setColor(Color.black);
                    g2d.fillRect(0, 0, 800, 600);
                }
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

                String msg = "Click to focus!";
                this.gameComponent.getFrame().renderWindow(g, 400 - msg.length() * 10 - 32, 256 - 36, 328, 96);
                this.gameComponent.getFont().RenderString(g, msg, 400 - msg.length() * 10, 256, 3, ( (this.focusTimer > 32) ? 0xff000000 : 0xffffffff ));
            }

        } else if(this.gameComponent.state == GameState.MAIN_MENU) {
            this.gameComponent.getMenu().render(g);
        } else if(this.gameComponent.state == GameState.GAME_CUTSCENE) {
            this.gameComponent.getCutscene().render(g);
        }

        g.dispose();
        bs.show();

        this.frames++;
    }

    public void updateFrameCounter() {
        this.frameCounter = this.frames;
        this.frames = 0;
    }

    public void unfocusedUpdate() {
        this.focusTimer++;
        if(this.focusTimer > 64) this.focusTimer = 0;
    }

    public void decrementEnemyCounter() {
        if(this.world != null) this.world.setEnemyCounter(this.world.getEnemyCounter() - 1);
    }

    public void setFlashTimer(int flashTimer) {
        this.flashTimer = flashTimer;
    }
}
