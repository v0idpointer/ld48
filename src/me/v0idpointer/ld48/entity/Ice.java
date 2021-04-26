package me.v0idpointer.ld48.entity;

import me.v0idpointer.ld48.Game;
import me.v0idpointer.ld48.gfx.SpriteSheet;
import me.v0idpointer.ld48.partice.SmokeParticle;
import me.v0idpointer.ld48.partice.SplatterParticle;
import me.v0idpointer.ld48.sfx.Sound;

import java.awt.*;
import java.util.Random;

public class Ice extends Entity {

    public Ice(int x, int y, int dirX, int dirY) {
        super(3, x, y, "ice");
        this.setVelX(5 * dirX);
        this.setVelY(5 * dirY);
        this.setNoClipPrivilege(true);
    }

    @Override
    public void update() {
        this.setX(this.getX() + this.getVelX());
        this.setY(this.getY() + this.getVelY());

        if(this.getX() < 0 || this.getX() + 32 > 800 || this.getY() < 96 || this.getY() > 600) killIceball();

        for(Entity entity : Game.instance.getDisplayComponent().world.getEntities()) {
            if(this.getHitbox().intersects(entity.getHitbox()) && entity.hasAi()) {
                entity.setHealth(entity.getHealth() - 18);
                Game.instance.getHUD().awardPlayerDamage(18);
                this.killIceball();
                Sound.mob_damage.play();

                if(entity.getClass().getName().toLowerCase().indexOf("fire") >= 0) {
                    entity.setHealth(entity.getHealth() - 22);
                    Game.instance.getHUD().awardPlayerDamage(22);
                }
                else if(entity.getClass().getName().toLowerCase().indexOf("frostbite") >= 0) {
                    entity.setHealth(entity.getHealth() + 8);
                    Game.instance.getHUD().awardPlayerDamage(-8);
                    if(new Random().nextInt(5) == 2) {
                        entity.setHealth(entity.getHealth() + 24);
                        Game.instance.getHUD().awardPlayerDamage(-24);
                        if(entity.getHealth() > entity.getMaxHealth()) entity.setHealth(entity.getMaxHealth());
                    }
                }
            }
        }

        Game.instance.getDisplayComponent().world.getParticles().add(new SmokeParticle(this.getX(), this.getY(), 8, 0xFFD5FFFF, 1, 16, true, false));
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(SpriteSheet.TERRAIN.getSprite(12, 2), this.getX() - 16, this.getY() - 16, 32, 32, null);
    }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(this.getX() - 16, this.getY() - 16, 32, 32);
    }

    public void killIceball() {
        Game.instance.getDisplayComponent().world.getParticles().add(new SplatterParticle(this.getX() + getRandomXOffset(), this.getY(), 0xFFD5FFFF, 24, -32));
        Game.instance.getDisplayComponent().world.getParticles().add(new SplatterParticle(this.getX() + getRandomXOffset(), this.getY(), 0xFFD5FFFF, 24, 32));
        Game.instance.getDisplayComponent().world.getParticles().add(new SplatterParticle(this.getX() + getRandomXOffset(), this.getY() - 16, 0xFFD5FFFF, 24, -32));
        Game.instance.getDisplayComponent().world.getParticles().add(new SplatterParticle(this.getX() + getRandomXOffset(), this.getY() - 16, 0xFFD5FFFF, 24, 32));

        Game.instance.getDisplayComponent().world.getEntities().remove(this);

        Sound.snow_explode.play();
    }

    private int getRandomXOffset() {
        Random random = new Random();
        int k = random.nextInt(3) - 1;
        int n = random.nextInt(9);
        int y = (int)( n * Math.signum(k) );
        return y;
    }
}
