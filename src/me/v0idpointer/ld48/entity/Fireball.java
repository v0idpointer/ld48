package me.v0idpointer.ld48.entity;

import me.v0idpointer.ld48.Game;
import me.v0idpointer.ld48.gfx.SpriteSheet;
import me.v0idpointer.ld48.partice.SmokeParticle;
import me.v0idpointer.ld48.partice.SplatterParticle;
import me.v0idpointer.ld48.sfx.Sound;

import java.awt.*;
import java.util.Random;

public class Fireball extends Entity {

    public Fireball(int x, int y, int dirX, int dirY) {
        super(3, x, y, "fireball_");
        this.setVelX(5 * dirX);
        this.setVelY(5 * dirY);
        this.setNoClipPrivilege(true);
    }

    @Override
    public void update() {
        this.setX(this.getX() + this.getVelX());
        this.setY(this.getY() + this.getVelY());

        if(this.getX() < 0 || this.getX() + 32 > 800 || this.getY() < 96 || this.getY() > 600) killFireball();

        for(Entity entity : Game.instance.getDisplayComponent().world.getEntities()) {
            if(this.getHitbox().intersects(entity.getHitbox()) && entity.hasAi()) {
                entity.setHealth(entity.getHealth() - 12);
                Game.instance.getHUD().awardPlayerDamage(12);
                this.killFireball();
                Sound.mob_damage.play();

                if(entity.getClass().getName().toLowerCase().indexOf("frostbite") >= 0) {
                    if(entity instanceof FrostbiteSlime) {
                        if(new Random().nextBoolean()) {
                            entity.setHealth(entity.getHealth() - 22);
                            Game.instance.getHUD().awardPlayerDamage(22);
                        }else {
                            int ch = entity.getHealth();
                            int cx = entity.getX();
                            int cy = entity.getY();
                            Game.instance.getDisplayComponent().world.getEntities().remove(entity);

                            FrostbiteSlime clone1 = new FrostbiteSlime(cx - 16, cy - 16, "frostbite_slime_clone_1");
                            FrostbiteSlime clone2 = new FrostbiteSlime(cx + 16, cy + 16, "frostbite_slime_clone_2");
                            clone1.setHealth(ch / 2);
                            clone2.setHealth(ch / 2);

                            Game.instance.getDisplayComponent().world.getEntities().add(clone1);
                            Game.instance.getDisplayComponent().world.getEntities().add(clone2);
                            Game.instance.getDisplayComponent().world.setEnemyCounter(Game.instance.getDisplayComponent().world.getEnemyCounter() + 1);
                        }
                    }else{
                        entity.setHealth(entity.getHealth() - 24);
                        Game.instance.getHUD().awardPlayerDamage(24);
                    }
                }
                else if(entity.getClass().getName().toLowerCase().indexOf("fire") >= 0) {
                    entity.setHealth(entity.getHealth() + 6);
                    Game.instance.getHUD().awardPlayerDamage(-6);
                }
            }
        }

        Game.instance.getDisplayComponent().world.getParticles().add(new SmokeParticle(this.getX(), this.getY(), 8, SmokeParticle.FLAME_TRAIL, 1, 24, true, false));
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
        Game.instance.getDisplayComponent().world.getParticles().add(new SplatterParticle(this.getX() + getRandomXOffset(), this.getY(), 0xffcc0000, 24, -32));
        Game.instance.getDisplayComponent().world.getParticles().add(new SplatterParticle(this.getX() + getRandomXOffset(), this.getY(), 0xffcc0000, 24, 32));
        Game.instance.getDisplayComponent().world.getParticles().add(new SplatterParticle(this.getX() + getRandomXOffset(), this.getY() - 16, 0xffcc0000, 24, -32));
        Game.instance.getDisplayComponent().world.getParticles().add(new SplatterParticle(this.getX() + getRandomXOffset(), this.getY() - 16, 0xffcc0000, 24, 32));

        Game.instance.getDisplayComponent().world.getEntities().remove(this);
        Sound.fireball_explode.play();
    }

    private int getRandomXOffset() {
        Random random = new Random();
        int k = random.nextInt(3) - 1;
        int n = random.nextInt(9);
        int y = (int)( n * Math.signum(k) );
        return y;
    }
}
