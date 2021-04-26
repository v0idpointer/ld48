package me.v0idpointer.ld48.entity;

import me.v0idpointer.ld48.Game;
import me.v0idpointer.ld48.gfx.SpriteSheet;
import me.v0idpointer.ld48.partice.SmokeParticle;
import me.v0idpointer.ld48.partice.SplatterParticle;
import me.v0idpointer.ld48.sfx.Sound;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class LightBeam extends Entity {

    private LinkedList<Entity> tagged;
    private boolean isRainbow = false;

    public LightBeam(int x, int y, int dirX, int dirY) {
        super(55, x, y, "lightBeam");
        this.setVelX(5 * dirX);
        this.setVelY(5 * dirY);
        this.setNoClipPrivilege(true);
        this.tagged = new LinkedList<>();
    }

    @Override
    public void update() {
        this.setX(this.getX() + this.getVelX());
        this.setY(this.getY() + this.getVelY());

        if(this.getX() < 0 || this.getX() > 770 || this.getY() < 96 || this.getY() > 520) killLight();

        for(Entity entity : Game.instance.getDisplayComponent().world.getEntities()) {
            if(this.getHitbox().intersects(entity.getHitbox()) && entity.hasAi() && !(this.tagged.contains(entity))) {
                int d = 10 - ( this.tagged.size() > 5 ? 5 : this.tagged.size() );
                entity.setHealth(entity.getHealth() - d);
                Game.instance.getHUD().awardPlayerDamage(d);
                this.tagged.add(entity);
                Sound.mob_damage.play();

                if(entity.getClass().getName().toLowerCase().indexOf("dark") >= 0 || entity.getClass().getName().toLowerCase().indexOf("phantom") >= 0) {
                    entity.setHealth(entity.getHealth() - 12);
                    Game.instance.getHUD().awardPlayerDamage(12);
                }

                if(entity instanceof FrostbiteSlime) {
                    this.setVelX(this.getVelX() * -1);
                    this.setVelY(this.getVelY() * -1);

                    int v = new Random().nextInt(3) - 1;
                    if(this.getVelX() == 0) this.setVelX(v * 5);
                    else this.setVelY(v * 5);

                    this.isRainbow = true;
                }
            }
        }

        if(!this.isRainbow) Game.instance.getDisplayComponent().world.getParticles().add(new SmokeParticle(this.getX(), this.getY(), 8, 0xFFFFD871, 1, 16, true, false));
        else {
            Game.instance.getDisplayComponent().world.getParticles().add(new SmokeParticle(this.getX(), this.getY(), 8, 0xFFCC0000, 1, 16, true, false));
            Game.instance.getDisplayComponent().world.getParticles().add(new SmokeParticle(this.getX(), this.getY(), 8, 0xFFFF6A00, 1, 16, true, false));
            Game.instance.getDisplayComponent().world.getParticles().add(new SmokeParticle(this.getX(), this.getY(), 8, 0xFF00CC00, 1, 16, true, false));
            Game.instance.getDisplayComponent().world.getParticles().add(new SmokeParticle(this.getX(), this.getY(), 8, 0xFF0055CC, 1, 16, true, false));
        }
    }

    @Override
    public void render(Graphics g) { }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(this.getX() - 16, this.getY() - 16, 32, 32);
    }

    public void killLight() {
        Game.instance.getDisplayComponent().world.getEntities().remove(this);
    }
}
