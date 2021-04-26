package me.v0idpointer.ld48.entity;

import me.v0idpointer.ld48.Game;
import me.v0idpointer.ld48.partice.SmokeParticle;
import me.v0idpointer.ld48.partice.SplatterParticle;
import me.v0idpointer.ld48.sfx.Sound;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class PowerBeam extends Entity {

    private LinkedList<Entity> tagged;
    private int life = 16;
    private int clearTimer = 64;

    public PowerBeam(int x, int y) {
        super(55, x, y, "powerBeam");
        this.setVelX(7 * ( new Random().nextBoolean() ? 1 : -1 ));
        this.setVelY(5);
        this.setNoClipPrivilege(true);
        this.tagged = new LinkedList<>();
    }

    @Override
    public void update() {
        this.setX(this.getX() + this.getVelX());
        this.setY(this.getY() + this.getVelY());

        if(this.getX() < 0 || this.getX() > 770) {
            this.setVelX(this.getVelX() * -1);
            this.life--;
        }
        if(this.getY() < 96 || this.getY() > 520) {
            this.setVelY(this.getVelY() * -1);
            this.life--;
        }

        for(Entity entity : Game.instance.getDisplayComponent().world.getEntities()) {
            if(this.getHitbox().intersects(entity.getHitbox()) && entity.hasAi() && entity != this && !(this.tagged.contains(entity))) {
                entity.setHealth(entity.getHealth() - 8);
                Game.instance.getHUD().awardPlayerDamage(8);
                this.life--;
                this.tagged.add(entity);
                Sound.mob_damage.play();
            }
        }

        this.clearTimer--;
        if(this.clearTimer <= 0) {
            this.clearTimer = 64;
            this.tagged.clear();
        }

        Game.instance.getDisplayComponent().world.getParticles().add(new SmokeParticle(this.getX(), this.getY(), 8, 0xFF0069FF, 1, 16, true, false));
        if(life <= 0) {
            Game.instance.getDisplayComponent().world.getParticles().add(new SplatterParticle(this.getX() + getRandomXOffset(), this.getY(), 0xFF0069FF, 24, -32));
            Game.instance.getDisplayComponent().world.getParticles().add(new SplatterParticle(this.getX() + getRandomXOffset(), this.getY(), 0xFF0069FF, 24, 32));
            Game.instance.getDisplayComponent().world.getParticles().add(new SplatterParticle(this.getX() + getRandomXOffset(), this.getY() - 16, 0xFF0069FF, 24, -32));
            Game.instance.getDisplayComponent().world.getParticles().add(new SplatterParticle(this.getX() + getRandomXOffset(), this.getY() - 16, 0xFF0069FF, 24, 32));

            Game.instance.getDisplayComponent().world.getEntities().remove(this);
        }
    }

    @Override
    public void render(Graphics g) { }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(this.getX() - 16, this.getY() - 16, 32, 32);
    }

    private int getRandomXOffset() {
        Random random = new Random();
        int k = random.nextInt(3) - 1;
        int n = random.nextInt(9);
        int y = (int)( n * Math.signum(k) );
        return y;
    }
}
