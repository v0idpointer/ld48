package me.v0idpointer.ld48.entity;

import me.v0idpointer.ld48.Game;
import me.v0idpointer.ld48.gfx.SpriteSheet;
import me.v0idpointer.ld48.partice.SplatterParticle;
import me.v0idpointer.ld48.sfx.Sound;

import java.awt.*;
import java.util.Random;

public class PhantomSlime extends Entity {

    private int animationTimer = 0;
    private int jumpTime = 0;
    private int jumpStrength = 15;
    private int damage = 7;

    private boolean phantomState = false;
    private int oldHealth = 0;
    private int oldMaxHealth = 0;

    public PhantomSlime(int x, int y, String name) {
        super(14, x, y, name);
        this.setHasAi(true);

        int ho = new Random().nextInt(10) + 1;
        this.setHealth(25 + ho);
        this.setMaxHealth(25 + ho);
    }

    @Override
    public void update() {
        this.setX(this.getX() + this.getVelX());
        this.setY(this.getY() + this.getVelY());

        Random random = new Random();
        int dirX = 0, dirY = 0;

        if(this.getHealth() < 8 && !this.phantomState) {
            Game.instance.getDisplayComponent().world.getParticles().add(new SplatterParticle(this.getX() + getRandomXOffset(), this.getY(), 0xff404040, 24, -32));
            Game.instance.getDisplayComponent().world.getParticles().add(new SplatterParticle(this.getX() + getRandomXOffset(), this.getY(), 0xff404040, 24, 32));
            Game.instance.getDisplayComponent().world.getParticles().add(new SplatterParticle(this.getX() + getRandomXOffset(), this.getY() - 16, 0xff404040, 24, -32));
            Game.instance.getDisplayComponent().world.getParticles().add(new SplatterParticle(this.getX() + getRandomXOffset(), this.getY() - 16, 0xff404040, 24, 32));

            this.phantomState = true;
            this.oldHealth = this.getHealth();
            this.oldMaxHealth = this.getMaxHealth();
            this.setMaxHealth(8);
            this.setHealth(this.getMaxHealth());
            int ex, ey;
            ex = 64 + random.nextInt(608);
            ey = 128 + random.nextInt(408);
            this.setX(ex);
            this.setY(ey);
        }

        if(!this.phantomState) {
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
        }else{
            if(random.nextInt(75) < 3) this.oldHealth += 1;
            if(this.oldHealth >= this.oldMaxHealth) {
                this.setMaxHealth(this.oldMaxHealth);
                this.setHealth(this.getMaxHealth());
                this.phantomState = false;
                this.oldHealth = 0;
                this.oldMaxHealth = 0;
            }

            for(Entity entity : Game.instance.getDisplayComponent().world.getEntities()) {
                if(entity.getHitbox().intersects(this.getHitbox()) && entity.getId() == 1) {
                    this.setHealth(0);
                    Game.instance.getDisplayComponent().world.getParticles().add(new SplatterParticle(this.getX() + getRandomXOffset(), this.getY(), 0xff404040, 24, -32));
                    Game.instance.getDisplayComponent().world.getParticles().add(new SplatterParticle(this.getX() + getRandomXOffset(), this.getY(), 0xff404040, 24, 32));
                    Game.instance.getDisplayComponent().world.getParticles().add(new SplatterParticle(this.getX() + getRandomXOffset(), this.getY() - 16, 0xff404040, 24, -32));
                    Game.instance.getDisplayComponent().world.getParticles().add(new SplatterParticle(this.getX() + getRandomXOffset(), this.getY() - 16, 0xff404040, 24, 32));
                    entity.setHealth(entity.getHealth() + 5);
                    entity.setPower(entity.getPower() + 12);
                    if(entity.getHealth() > entity.getMaxHealth()) entity.setHealth(entity.getMaxHealth());
                    if(entity.getPower() > 200) entity.setPower(200);
                }
            }
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
            }
        }

        if(this.getHealth() <= 0) {
            Game.instance.getDisplayComponent().world.getEntities().remove(this);
            Game.instance.getDisplayComponent().decrementEnemyCounter();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(SpriteSheet.TERRAIN.getSprite( ( (this.getVelX() > 0 || this.getVelY() > 0) ? 5 : (( (this.animationTimer > 16) ? 4 : 6 )) ) , 5 + ( this.phantomState ? 1 : 0 )), this.getX(), this.getY() , 48, 48, null);
    }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(this.getX(), this.getY(), 48, 48);
    }

    private int getRandomXOffset() {
        Random random = new Random();
        int k = random.nextInt(3) - 1;
        int n = random.nextInt(9);
        int y = (int)( n * Math.signum(k) );
        return y;
    }

}
