package me.v0idpointer.ld48.entity;

import me.v0idpointer.ld48.Game;
import me.v0idpointer.ld48.gfx.SpriteSheet;
import me.v0idpointer.ld48.partice.SplatterParticle;

import java.awt.*;
import java.util.Random;

public class StorageChest extends Entity {

    private final int itemId;
    private boolean locked = true;
    private final int amount;

    public StorageChest(int x, int y, int itemId, int amount) {
        super(76, x, y, "chest");
        this.itemId = itemId;
        this.amount = amount;
        this.setMaxHealth(1);
        this.setHealth(1);
        this.setHasAi(true);
    }

    @Override
    public void update() {
        if(this.locked && this.getHealth() < 0) {
            this.locked = false;

            Game.instance.getDisplayComponent().world.getParticles().add(new SplatterParticle(this.getX() + getRandomXOffset(), this.getY(), 0xFFFFD800, 24, -32));
            Game.instance.getDisplayComponent().world.getParticles().add(new SplatterParticle(this.getX() + getRandomXOffset(), this.getY(), 0xFFFFD800, 24, 32));
            Game.instance.getDisplayComponent().world.getParticles().add(new SplatterParticle(this.getX() + getRandomXOffset(), this.getY() - 16, 0xFFFFD800, 24, -32));
            Game.instance.getDisplayComponent().world.getParticles().add(new SplatterParticle(this.getX() + getRandomXOffset(), this.getY() - 16, 0xFFFFD800, 24, 32));

            Game.instance.getDisplayComponent().world.getEntities().add(new DroppedItem(this.getX(), this.getY(), this.itemId, this.amount));
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(SpriteSheet.TERRAIN.getSprite(4, 8 + (!this.locked ? 1 : 0)), this.getX() - 24, this.getY() - 24, 48, 48, null);
    }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(this.getX() - 24, this.getY() - 24, 48, 48);
    }

    private int getRandomXOffset() {
        return this.getRandomXOffset(9);
    }

    private int getRandomXOffset(int o) {
        Random random = new Random();
        int k = random.nextInt(3) - 1;
        int n = random.nextInt(o);
        int y = (int)( n * Math.signum(k) );
        return y;
    }

}
