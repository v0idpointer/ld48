package me.v0idpointer.ld48.menu;

import me.v0idpointer.ld48.Game;
import me.v0idpointer.ld48.GameState;
import me.v0idpointer.ld48.entity.Entity;
import me.v0idpointer.ld48.gfx.SpriteSheet;
import me.v0idpointer.ld48.input.KeyboardInput;
import me.v0idpointer.ld48.sfx.Sound;
import me.v0idpointer.ld48.world.World;
import me.v0idpointer.ld48.world.WorldGeneration;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenu extends MouseAdapter {

    private Game gameComponent;
    private World panorama;

    private int state = 0;
    private int lastFloor = 0;
    private int lastGems = 0;
    private int escTimer = 0;

    public MainMenu(Game gameComponent) {
        this.gameComponent = gameComponent;
        this.panorama = WorldGeneration.generateWorldPanorama();
    }

    public void update() {
        if(this.escTimer > 0) this.escTimer--;
        if(state == 3 && Game.instance.getKeyboardInput().isDown(KeyboardInput.KEY_ESCAPE) && this.escTimer == 0) {
            Game.instance.state = GameState.GAME;
            this.state = 0;
        }
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        this.panorama.render(g);
        g2d.setColor(new Color(0xff202020));
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
        g2d.fillRect(0, 0, 800, 600);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        g.setColor(Color.white);
        if(state == 1 || state == 0) Game.instance.getFont().RenderString(g, "Deep Below Ground", 80, 32, 6, 0xffffffff);

       if(this.state == 0) {
           g.drawRect(256, 256 - 32, 256, 64);
           Game.instance.getFont().RenderString(g, "PLAY", 256+64+16+4, 256-16, 4, 0xffffffff);

           g.drawRect(256, 256 + 64, 256, 64);
           Game.instance.getFont().RenderString(g, "Help", 256+64+16+4, 256+64+16, 4, 0xffffffff);

           g.drawRect(256, 256 + 160, 256, 64);
           Game.instance.getFont().RenderString(g, "Quit", 256+64+16+4, 256+160+16, 4, 0xffffffff);

           Game.instance.getFont().RenderString(g, "1.0", 16, 530, 2, 0xffffffff);
           Game.instance.getFont().RenderString(g, "Created for LD48, 2021.", 500, 530, 2, 0xffffffff);
       }else if(state == 1) {

           Game.instance.getFont().RenderString(g, "<<Deep Below Ground>> was created for Ludum Dare 48, a 48", 32, 128, 2, 0xffffffff);
           Game.instance.getFont().RenderString(g, "hour game jam. Created by Aljosa A. \"v0idpointer\"", 32, 128+32, 2, 0xffffffff);

           Game.instance.getFont().RenderString(g, "You play as a wizard, who explores an infinite dungeon while", 32, 128+96, 2, 0xffffffff);
           Game.instance.getFont().RenderString(g, "searching for rare gems and spells.", 32, 256, 2, 0xffffffff);

           Game.instance.getFont().RenderString(g, "Open chests, collect loot, learn spells, kill enemies and", 32, 256+64, 2, 0xffffffff);
           Game.instance.getFont().RenderString(g, "of course, have fun ;)", 32, 256+96, 2, 0xffffffff);

           g.drawImage(SpriteSheet.TERRAIN.getSprite(6, 10), 16, 512, 32, 32, null);
           Game.instance.getFont().RenderString(g, "V0idPointer", 64, 512+8, 2, 0xffffffff);
           g.drawImage(SpriteSheet.TERRAIN.getSprite(5, 10), 16, 512 - 48, 32, 32, null);
           Game.instance.getFont().RenderString(g, "v0idpointer", 64, 512-40, 2, 0xffffffff);
           g.drawImage(SpriteSheet.TERRAIN.getSprite(4, 10), 16, 512 - 96, 32, 32, null);
           Game.instance.getFont().RenderString(g, "@V0idPointer", 64, 512-88, 2, 0xffffffff);

           g.drawRect(256, 256 + 224, 256, 64);
           Game.instance.getFont().RenderString(g, "Back", 256+64+16+4, 256+224+16, 4, 0xffffffff);
       }else if(state == 2) {
           Game.instance.getFont().RenderString(g, "You Died :(", 128+64, 32, 6, 0xffffffff);

           Game.instance.getFont().RenderString(g, "You died on the " + Math.abs(this.lastFloor) + this.getFloorSuffix(this.lastFloor) + " floor.", 32, 128, 2, 0xffffffff);
           Game.instance.getFont().RenderString(g, "That's " + ( Math.abs(this.lastFloor) * 7.5 ) + " meters underground... Impressive!", 32, 128+32, 2, 0xffffffff);
           Game.instance.getFont().RenderString(g, "You collected " + Math.abs(this.lastGems) + " gems on you journey.", 32, 128+64, 2, 0xffffffff);

           g.drawRect(256, 256 + 224, 256, 64);
           Game.instance.getFont().RenderString(g, "Return", 256+64, 256+224+16, 4, 0xffffffff);
       }
       else if(state == 3) {
           Game.instance.getFont().RenderString(g, "Paused", 128+128+16, 32, 6, 0xffffffff);

           g.drawRect(256, 256 - 32, 256, 64);
           Game.instance.getFont().RenderString(g, "Continue", 256+32, 256-16, 4, 0xffffffff);

           g.drawRect(256, 256 + 64, 256, 64);
           Game.instance.getFont().RenderString(g, "Exit", 256+64+16+4, 256+64+16, 4, 0xffffffff);
       }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(this.gameComponent.state != GameState.MAIN_MENU) return;

        int mx[] = { e.getX(), e.getY() };

        if(state == 0) {
            if(new Rectangle(mx[0], mx[1], 1, 1).intersects(256, 256 - 32, 256, 64)) {
                this.gameComponent.getCutscene().reset();
                this.gameComponent.state = GameState.GAME_CUTSCENE;
                Sound.select.play();
            }
            if(new Rectangle(mx[0], mx[1], 1, 1).intersects(256, 256 + 64, 256, 64)) {
                this.state = 1;
                Sound.select.play();
            }
            if(new Rectangle(mx[0], mx[1],1 ,1).intersects(256, 256 + 160, 256, 64)) {
                System.exit(0);
                Sound.select.play();
            }
        }else if(state == 1 || state == 2) {
            if(new Rectangle(mx[0], mx[1], 1, 1).intersects(256, 256 + 224, 256, 64)) {
                this.state = 0;
                Sound.select.play();
            }
        }
        else if(state == 3) {
            if(new Rectangle(mx[0], mx[1], 1, 1).intersects(256, 256 - 32, 256, 64)) {
                Game.instance.state = GameState.GAME;
                this.state = 0;
                Sound.select.play();
            }
            if(new Rectangle(mx[0], mx[1], 1, 1).intersects(256, 256 + 64, 256, 64)) {
                this.state = 0;
                Game.instance.state = GameState.MAIN_MENU;
                this.gameComponent.state = GameState.MAIN_MENU;
                Game.instance.getDisplayComponent().world = WorldGeneration.generate(0, null);
                for(Entity entity : Game.instance.getDisplayComponent().world.getEntities()) {
                    if(entity.getId() == 1) Game.instance.getHUD().attachTarget(entity);
                }
                Sound.select.play();
            }
        }
    }

    public void displayDeathPage(int finalFloor, int gems) {
        this.state = 2;
        this.lastGems = gems;
        this.lastFloor = finalFloor;
        this.gameComponent.state = GameState.MAIN_MENU;
        Game.instance.getDisplayComponent().world = WorldGeneration.generate(0, null);
        for(Entity entity : Game.instance.getDisplayComponent().world.getEntities()) {
            if(entity.getId() == 1) Game.instance.getHUD().attachTarget(entity);
        }
    }

    private String getFloorSuffix(int floor) {
        String text = "" + floor;
        char lastChar = text.charAt(text.length() - 1);
        if(lastChar == '1') return "st";
        else if(lastChar == '2') return "nd";
        else if(lastChar == '3') return "rd";
        else return "th";
    }

    public void sendPausedMessage() {
        this.state = 3;
        this.escTimer = 32;
    }

}
