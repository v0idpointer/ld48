package me.v0idpointer.ld48.entity;

import me.v0idpointer.ld48.Game;
import me.v0idpointer.ld48.gfx.SpriteSheet;
import me.v0idpointer.ld48.sfx.Sound;

import java.awt.*;
import java.util.Random;

public class MagicSlime extends Entity {

    private int animationTimer = 0;
    private int jumpTime = 0;
    private int jumpStrength = 14;
    private int damage = 6;
    private int times = 0;

    public MagicSlime(int x, int y, String name) {
        super(12, x, y, name);
        this.setHasAi(true);

        int ho = new Random().nextInt(15) + 1;
        this.setHealth(20 + ho);
        this.setMaxHealth(20 + ho);
    }

    @Override
    public void update() {
        this.setX(this.getX() + this.getVelX());
        this.setY(this.getY() + this.getVelY());

        Random random = new Random();
        int dirX = 0, dirY = 0;

        if(this.jumpTime <= -16) {
            dirX = random.nextInt(3) - 1; // Value will be -1, 0 or 1
            dirY = random.nextInt(3) - 1;

            if(Game.instance.getDisplayComponent().world.getEntityByName("player") != null) {
                int xd = Game.instance.getDisplayComponent().world.getEntityByName("player").getX() - this.getX();
                int yd = Game.instance.getDisplayComponent().world.getEntityByName("player").getY() - this.getY();

                if (xd < 0) dirX = -1;
                if (xd > 0) dirX = +1;
                if (yd < 0) dirY = -1;
                if (yd > 0) dirY = +1;
            }

            this.jumpTime = 16;
        }

        this.jumpTime --;
        if(this.jumpTime == 0) {
            dirX = 0;
            dirY = 0;
        }

        this.setVelX(this.jumpStrength * dirX);
        this.setVelY(this.jumpStrength * dirY);

        this.animationTimer++;
        if(this.animationTimer > 32) this.animationTimer = 0;

        if(this.jumpTime > 14) {
            for(Entity entity : Game.instance.getDisplayComponent().world.getEntities()) {
                if(entity.getHitbox().intersects(this.getHitbox()) && entity.getId() == 1) {
                    entity.setHealth(entity.getHealth() - this.damage);
                    Sound.player_damage.play();
                }
                if(entity.getHitbox().intersects(this.getHitbox()) && entity.getId() == 12 && entity != this) {
                    if(new Random().nextInt(12) == 2) {
                        int hp = entity.getHealth();
                        int max = entity.getMaxHealth();
                        int t = ((MagicSlime)entity).getTimes();
                        this.setMaxHealth(this.getMaxHealth() + max);
                        this.setHealth(this.getHealth() + hp);
                        Game.instance.getDisplayComponent().world.getEntities().remove(entity);
                        Game.instance.getDisplayComponent().world.setEnemyCounter(Game.instance.getDisplayComponent().world.getEnemyCounter() - 1);
                        this.times = this.times + 1 + t;
                        this.damage = 6 + (times * 2);
                    }
                }
            }
        }

        if(this.getHealth() <= 0) {
            this.summonManaSpiritReward();
            Game.instance.getDisplayComponent().world.getEntities().remove(this);
            Game.instance.getDisplayComponent().decrementEnemyCounter();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(SpriteSheet.TERRAIN.getSprite( ( (this.getVelX() > 0 || this.getVelY() > 0) ? 5 : (( (this.animationTimer > 16) ? 4 : 6 )) ) , 4), this.getX(), this.getY() , 48 + (times * 16), 48 + (times * 16), null);
    }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(this.getX(), this.getY(), 48 + (times * 16), 48 + (times * 16));
    }

    public int getTimes() {
        return times;
    }
}
