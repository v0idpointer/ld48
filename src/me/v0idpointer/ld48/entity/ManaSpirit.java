package me.v0idpointer.ld48.entity;

import me.v0idpointer.ld48.Game;
import me.v0idpointer.ld48.partice.SmokeParticle;
import me.v0idpointer.ld48.sfx.Sound;

import java.awt.*;

public class ManaSpirit extends Entity {

    public ManaSpirit(int x, int y) {
        super(101, x, y, "mana_spirit");
    }

    @Override
    public void update() {
        Game.instance.getDisplayComponent().world.getParticles().add(new SmokeParticle(this.getX(), this.getY(), 8, 0xFF1A30AF, 1, 16, true, true));

        for(Entity entity : Game.instance.getDisplayComponent().world.getEntities()) {
            if(entity.getId() == 1 && entity.getHitbox().intersects(this.getHitbox())) {
                entity.setPower(entity.getPower() + 2);
                if(entity.getPower() > 200) entity.setPower(200);
                Game.instance.getDisplayComponent().world.getEntities().remove(this);
                Sound.consume_mana.play();
            }
        }

    }

    @Override
    public void render(Graphics g) { }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(this.getX() - 8, this.getY() - 8, 16, 16);
    }
}
