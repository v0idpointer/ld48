package me.v0idpointer.ld48.entity;

import me.v0idpointer.ld48.Game;
import me.v0idpointer.ld48.partice.SmokeParticle;
import me.v0idpointer.ld48.sfx.Sound;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class Zap extends Entity {

    public Zap(int x, int y) {
        super(56, x, y, "zap");
        this.setNoClipPrivilege(true);
    }

    @Override
    public void update() {
        this.setX(this.getX() + this.getVelX());
        this.setY(this.getY() + this.getVelY());

        for(Entity entity : Game.instance.getDisplayComponent().world.getEntities()) {
            if(this.getHitbox().intersects(entity.getHitbox()) && entity.hasAi() && entity.getId() != 1) {
                entity.setHealth(entity.getHealth() - 2);
                Game.instance.getHUD().awardPlayerDamage(2);
                Sound.mob_damage.play();
            }
        }

        Game.instance.getDisplayComponent().world.getParticles().add(new SmokeParticle(this.getX(), this.getY(), 4, 0xFF0069FF, 1, 16, true, false));
        Game.instance.getDisplayComponent().world.getEntities().remove(this);
    }

    @Override
    public void render(Graphics g) { }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(this.getX() - 16, this.getY() - 16, 32, 32);
    }

}
