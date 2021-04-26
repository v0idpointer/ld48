package me.v0idpointer.ld48.entity;

import me.v0idpointer.ld48.Game;
import me.v0idpointer.ld48.gfx.SpriteSheet;
import me.v0idpointer.ld48.item.Item;
import me.v0idpointer.ld48.sfx.Sound;

import java.awt.*;

public class DroppedItem extends Entity {
    private final int value;
    private final int amount;
    private float step = 0;

    private boolean showHint = false;

    public DroppedItem(int x, int y, int value, int amount) {
        super(95, x, y, "item");

        this.value = value;
        this.amount = amount;
    }

    @Override
    public void update() {
        for(Entity entity : Game.instance.getDisplayComponent().world.getEntities()) {
            if(entity.getHitbox().intersects(this.getHitbox()) && entity instanceof Player) {
                Player player = (Player)entity;

                if(player.getItemX() == null) {
                    player.setItemX(Item.items[this.getValue()]);
                    player.setSelectedItemAmount(player.getSelectedItemAmount() + this.amount);
                    Game.instance.getDisplayComponent().world.getEntities().remove(this);
                    Sound.coin.play();
                }else{
                    if(player.getItemX().getId() == this.value) {
                        player.setSelectedItemAmount(player.getSelectedItemAmount() + this.amount);
                        Game.instance.getDisplayComponent().world.getEntities().remove(this);
                        Sound.coin.play();
                    }else{
                        this.showHint = true;
                    }
                }
            }
        }
        this.step += 0.055f;
    }

    @Override
    public void render(Graphics g) {
        Item item = Item.items[this.value];
        g.drawImage(SpriteSheet.TERRAIN.getSprite(item.getTx(), item.getTy()), this.getX() - 16, this.getY() - 16 + (int)(Math.sin(this.step) * 5), 32, 32, null);
        if(this.showHint) {
            g.drawImage(SpriteSheet.TERRAIN.getSprite(3, 10), this.getX() - 32, this.getY() - 32 + (int) (Math.sin(this.step) * 5), 16, 16, null);
            Game.instance.getFont().RenderString(g, "Press F to pick up", this.getX() - 12, this.getY() - 28 + (int) (Math.sin(this.step) * 5), 1, 0xff000000);
        }
    }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(this.getX() - 16, this.getY() - 16, 32, 32);
    }

    public int getValue() {
        return value;
    }
}
