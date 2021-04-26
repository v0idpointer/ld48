package me.v0idpointer.ld48.entity;

import me.v0idpointer.ld48.Game;
import me.v0idpointer.ld48.gfx.SpriteSheet;
import me.v0idpointer.ld48.partice.SmokeParticle;
import me.v0idpointer.ld48.sfx.Sound;

import java.awt.*;
import java.util.Random;

public class HoveringInferno extends Entity {

    private int charge = 0, timer = 0;
    private Entity target;

    private final int[] colors = {0xffcc0000, 0xff6a00, 0xfffd800};

    public HoveringInferno(int x, int y) {
        super(3, x, y, "hovering_inferno");
        this.setHasAi(true);
        this.setHealth(60);
        this.setMaxHealth(60);
        this.setBarOffsetY(32);
    }

    @Override
    public void update() {
        if(target == null) {
            this.charge = 0;
            for(Entity entity : Game.instance.getDisplayComponent().world.getEntities()) {
                if(entity.hasAi() && entity != this && entity.getId() != 51) {
                    this.target = entity;
                    this.charge = 1;
                }
            }
        }else{
            this.timer++;
            if(this.timer > 5*64) {
                this.timer = 0;
                this.charge += 1;
                if(this.charge >= 3) this.charge = 3;
            }

            this.target.setHealth(this.target.getHealth() - this.charge * 2);
            Sound.mob_damage.play();
            Game.instance.getDisplayComponent().world.getParticles().add(new SmokeParticle(this.target.getX() + 32, this.target.getY() + 32 + this.target.getBarOffsetY(), 32 * this.charge, colors[3 - charge], 12, 16, true, true));

            if(this.target.getHealth() <= 0) {
                this.target = null;
                this.timer = 0;
            }
        }

        if(new Random().nextInt(25) == 0) this.setHealth(this.getHealth() - 1);
        if(this.getHealth() <= 0) {
            Game.instance.getDisplayComponent().world.getEntities().remove(this);
            Sound.hovering_inferno_decay.play();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(SpriteSheet.TERRAIN.getSprite(12, 1), this.getX() - 32, this.getY() - 32, 64, 64, null);
    }

    @Override
    public Rectangle getHitbox() {
        return this.getDefaultHitbox();
    }
}
