package me.v0idpointer.ld48.entity;

import me.v0idpointer.ld48.Game;
import me.v0idpointer.ld48.gfx.SpriteSheet;
import me.v0idpointer.ld48.sfx.Sound;

import java.awt.*;
import java.util.Random;

public class SpellbookPage extends Entity {

    public static final int DEFAULT_PAGE = 0b00000001;
    public static final int ICE_PAGE = 0b00000010;
    public static final int LIGHT_PAGE = 0b00000100;
    public static final int ELECTRICAL_PAGE = 0b00001000;
    public static final int NATURE_PAGE = 0b00010000;
    public static final int TIME_PAGE = 0b00100000;

    private final int value;
    private float step = 0;

    public SpellbookPage(int x, int y, int value) {
        super(50, x, y, "page");

        this.value = value;
    }

    @Override
    public void update() {
        for(Entity entity : Game.instance.getDisplayComponent().world.getEntities()) {
            if(entity.getHitbox().intersects(this.getHitbox()) && entity instanceof Player) {
                Player player = (Player)entity;

                int unlock = player.getUnlockedSpells();
                unlock |= this.value;
                player.setUnlockedSpells(unlock);
                player.setBookOpen(true);

                Game.instance.getDisplayComponent().world.getEntities().remove(this);
                Sound.spell_learn.play();
            }
        }
        this.step += 0.055f;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(SpriteSheet.TERRAIN.getSprite(0, 7), this.getX() - 16, this.getY() - 16 + (int)(Math.sin(this.step) * 5), 32, 32, null);
    }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(this.getX() - 16, this.getY() - 16, 32, 32);
    }

    public int getValue() {
        return value;
    }
}
