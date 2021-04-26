package me.v0idpointer.ld48.entity;

import me.v0idpointer.ld48.Game;
import me.v0idpointer.ld48.gfx.SpriteSheet;
import me.v0idpointer.ld48.sfx.Sound;

import java.awt.*;
import java.util.Random;

public class Gem extends Entity {

    private final int[][] gemType = { {10, 0, 8}, {25, 1, 8}, {50, 2, 8}, {-1, 0, 9}, {-2, 1, 9} };

    private final int value;
    private final int tx, ty;

    private float step = 0;

    public Gem(int x, int y) {
        super(50, x, y, "gem");

        int[] gem = gemType[new Random().nextInt(3)];

        if(new Random().nextInt(6) < 2) {
            gem = this.gemType[ ( new Random().nextBoolean() ? 3 : 4 ) ];
        }

        this.value = gem[0];
        this.tx = gem[1];
        this.ty = gem[2];

    }

    @Override
    public void update() {
        for(Entity entity : Game.instance.getDisplayComponent().world.getEntities()) {
            if(entity.getHitbox().intersects(this.getHitbox()) && entity instanceof Player) {
                Player player = (Player)entity;

                if(this.value < 0) {
                    if(this.value == -1) player.setHealth(player.getHealth() + 10);
                    if(this.value == -2) player.setPower(player.getPower() + 20);
                    if(player.getHealth() > player.getMaxHealth()) player.setHealth(player.getMaxHealth());
                    if(player.getPower() > 200) player.setPower(200);
                }else{
                    player.setGems(player.getGems() + this.value);
                }

                Game.instance.getDisplayComponent().world.getEntities().remove(this);
                Sound.coin.play();
            }
        }
        this.step += 0.055f;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(SpriteSheet.TERRAIN.getSprite(this.tx, this.ty), this.getX() - 12, this.getY() - 12 + (int)(Math.sin(this.step) * 5), 24, 24, null);
    }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(this.getX() - 12, this.getY() - 12, 24, 24);
    }

    public int getValue() {
        return value;
    }

    public int getTx() {
        return tx;
    }

    public int getTy() {
        return ty;
    }
}
