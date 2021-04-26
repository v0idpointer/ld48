package me.v0idpointer.ld48.menu;

import me.v0idpointer.ld48.Game;
import me.v0idpointer.ld48.GameState;
import me.v0idpointer.ld48.gfx.SpriteSheet;
import me.v0idpointer.ld48.input.KeyboardInput;
import me.v0idpointer.ld48.sfx.Sound;

import java.awt.*;

public class GameCutscene {

    private Game gameComponent;
    private int timer = 0;
    private int stage = 0;

    public GameCutscene(Game gameComponent) {
        this.gameComponent = gameComponent;
    }

    public void update() {
        this.timer++;
        if(this.timer >= 64*5) {
            stage ++;
            timer = 0;
            if(stage > 3) {
                stage = 0;
                this.gameComponent.state = GameState.GAME;
            }
        }

        if(Game.instance.getKeyboardInput().isDown(KeyboardInput.KEY_ITEM_X)) {
            this.gameComponent.state = GameState.GAME;
            Sound.select.play();
        }
    }

    public void render(Graphics g) {

        if(stage == 0) {
            g.drawImage(SpriteSheet.STORYBOARD_0.getSpriteR(0, 0, 200, 150), 0, 0, 800, 600, null);
            Game.instance.getFrame().renderWindow(g, 32, 256+128+64, 700, 96);
            Game.instance.getFont().RenderString(g, "A simple man who works a simple job during the day...", 64, 256+128+96+8, 2, 0xffffffff);
        }else if(stage == 1) {
            g.drawImage(SpriteSheet.STORYBOARD_1.getSpriteR(0, 0, 200, 150), 0, 0, 800, 600, null);
            Game.instance.getFrame().renderWindow(g, 32, 256+128+64, 700, 96);
            Game.instance.getFont().RenderString(g, "... and who fights monsters during the night...", 64+16, 256+128+96+8, 2, 0xffffffff);
        }else if(stage == 2) {
            g.drawImage(SpriteSheet.STORYBOARD_2.getSpriteR(0, 0, 200, 150), 0, 0, 800, 600, null);
            Game.instance.getFrame().renderWindow(g, 32, 256+128+64, 700, 96);
            Game.instance.getFont().RenderString(g, ".. when he heard about a dungeon full of treasure...", 64, 256+128+96+8, 2, 0xffffffff);
        }else if(stage == 3) {
            g.drawImage(SpriteSheet.STORYBOARD_3.getSpriteR(0, 0, 200, 150), 0, 0, 800, 600, null);
            Game.instance.getFrame().renderWindow(g, 32, 32, 480, 96);
            Game.instance.getFont().RenderString(g, "... he knew what he had to do!", 64+16, 64+8, 2, 0xffffffff);
        }

        if(stage < 3) Game.instance.getFont().RenderString(g, "Press 'X' to skip >>", 16, 16, 2, 0xffffffff);
    }

    public void reset() {
        this.timer = 0;
        this.stage = 0;
    }

}
