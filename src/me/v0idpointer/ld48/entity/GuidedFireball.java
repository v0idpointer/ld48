package me.v0idpointer.ld48.entity;

import me.v0idpointer.ld48.Game;
import me.v0idpointer.ld48.gfx.SpriteSheet;
import me.v0idpointer.ld48.partice.SmokeParticle;
import me.v0idpointer.ld48.partice.SplatterParticle;
import me.v0idpointer.ld48.sfx.Sound;

import java.awt.*;
import java.util.Random;

public class GuidedFireball extends Entity {

    private final int ty;

    public GuidedFireball(int x, int ty, int dy) {
        super(3, x, -64 - dy, "guided_fireball_");
        this.ty = ty;
        this.setVelY(5);
        this.setNoClipPrivilege(true);
    }

    @Override
    public void update() {
        this.setY(this.getY() + this.getVelY());

        if(this.getY() >= this.ty || this.getY() > 520) {
            killFireball();
            Sound.fireball_explode.play();
        }

        Game.instance.getDisplayComponent().world.getParticles().add(new SmokeParticle(this.getX(), this.getY(), 8, SmokeParticle.FLAME_TRAIL, 1, 24, true, true));
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(SpriteSheet.TERRAIN.getSprite(12, 0), this.getX() - 16, this.getY() - 16, 32, 32, null);
    }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(this.getX() - 16, this.getY() - 16, 32, 32);
    }

    public void killFireball() {
        for(Entity entity : Game.instance.getDisplayComponent().world.getEntities()) {
            if(entity.getHitbox().intersects(this.getHitbox()) && entity.hasAi()) {
                entity.setHealth(entity.getHealth() - 24);
                Sound.mob_damage.play();
                Game.instance.getHUD().awardPlayerDamage(24);
            }
        }

        Game.instance.getDisplayComponent().world.getParticles().add(new SplatterParticle(this.getX() + getRandomXOffset(), this.getY(), 0xffcc0000, 24, -32));
        Game.instance.getDisplayComponent().world.getParticles().add(new SplatterParticle(this.getX() + getRandomXOffset(), this.getY(), 0xffcc0000, 24, 32));
        Game.instance.getDisplayComponent().world.getParticles().add(new SplatterParticle(this.getX() + getRandomXOffset(), this.getY() - 16, 0xffcc0000, 24, -32));
        Game.instance.getDisplayComponent().world.getParticles().add(new SplatterParticle(this.getX() + getRandomXOffset(), this.getY() - 16, 0xffcc0000, 24, 32));

        Game.instance.getDisplayComponent().world.getEntities().remove(this);
    }

    private int getRandomXOffset() {
        Random random = new Random();
        int k = random.nextInt(3) - 1;
        int n = random.nextInt(9);
        int y = (int)( n * Math.signum(k) );
        return y;
    }
}
