package me.v0idpointer.ld48.entity;

import me.v0idpointer.ld48.Game;
import me.v0idpointer.ld48.partice.SmokeParticle;
import me.v0idpointer.ld48.sfx.Sound;

import java.awt.*;
import java.util.Random;

public class Snowstorm extends Entity {

    private int timer = 4*64;
    private Player owner;

    public Snowstorm(int x, int y, Player owner) {
        super(53, x, y, "snowstorm");
        this.owner = owner;
        Sound.storm.play();
    }

    @Override
    public void update() {
        this.timer --;
        if(this.timer <= 0) Game.instance.getDisplayComponent().world.getEntities().remove(this);

        for(Entity entity : Game.instance.getDisplayComponent().world.getEntities()) {
            if(entity.getHitbox().intersects(this.getHitbox()) && entity != this && entity != owner) {
                entity.setHealth(entity.getHealth() - 4);
                this.owner.setHealth(this.owner.getHealth() + 2);
                if(this.owner.getHealth() > this.owner.getMaxHealth()) this.owner.setHealth(this.owner.getMaxHealth());
            }
        }
    }

    @Override
    public void render(Graphics g) {
        Random random = new Random();
        for(int i = 0; i < 50; i++) {
            int dx = random.nextInt(3) - 1;
            int dy = random.nextInt(3) - 1;
            int ox = random.nextInt(64) * dx;
            int oy = random.nextInt(64) * dy;
            Game.instance.getDisplayComponent().world.getParticles().add(new SmokeParticle(this.getX() + ox, this.getY() + oy, 12, 0xFFD5FFFF, 2, 24, true, false));
        }
    }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(this.getX() - 64, this.getY() - 64, 128, 128);
    }
}
