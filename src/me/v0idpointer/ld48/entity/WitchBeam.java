package me.v0idpointer.ld48.entity;

import me.v0idpointer.ld48.Game;
import me.v0idpointer.ld48.partice.SmokeParticle;
import me.v0idpointer.ld48.partice.SplatterParticle;
import me.v0idpointer.ld48.sfx.Sound;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class WitchBeam extends Entity {

    private LinkedList<Entity> tagged;
    private int life = 1;
    private int clearTimer = 64;

    public WitchBeam(int x, int y, int dirX, int dirY) {
        super(61, x, y, "witchBeam");
        this.setVelX(5 * dirX);
        this.setVelY(5 * dirY);
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
            if(this.getHitbox().intersects(entity.getHitbox()) && (entity.getId() == 1 || entity.getId() == 51) && !(this.tagged.contains(entity))) {
                entity.setHealth(entity.getHealth() - 15);
                entity.setPower(entity.getPower() + 20);
                if(entity.getPower() > 200) entity.setPower(200);
                this.life--;
                this.tagged.add(entity);
                Sound.player_damage.play();
            }
        }

        this.clearTimer--;
        if(this.clearTimer <= 0) {
            this.clearTimer = 64;
            this.tagged.clear();
        }

        Game.instance.getDisplayComponent().world.getParticles().add(new SmokeParticle(this.getX(), this.getY(), 8, 0xFF6F4099, 1, 16, true, false));
        if(life <= 0) {
            Game.instance.getDisplayComponent().world.getParticles().add(new SplatterParticle(this.getX() + getRandomXOffset(), this.getY(), 0xFF6F4099, 24, -32));
            Game.instance.getDisplayComponent().world.getParticles().add(new SplatterParticle(this.getX() + getRandomXOffset(), this.getY(), 0xFF6F4099, 24, 32));
            Game.instance.getDisplayComponent().world.getParticles().add(new SplatterParticle(this.getX() + getRandomXOffset(), this.getY() - 16, 0xFF6F4099, 24, -32));
            Game.instance.getDisplayComponent().world.getParticles().add(new SplatterParticle(this.getX() + getRandomXOffset(), this.getY() - 16, 0xFF6F4099, 24, 32));

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
