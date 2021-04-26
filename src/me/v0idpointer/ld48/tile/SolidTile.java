package me.v0idpointer.ld48.tile;

import me.v0idpointer.ld48.gfx.SpriteSheet;

import java.awt.*;

public class SolidTile extends Tile {

    public SolidTile(int id, int tx, int ty, String tileName) {
        super(id, tx, ty, tileName);
    }

    @Override
    public void render(Graphics graphics, int x, int y) {
        graphics.drawImage(SpriteSheet.TERRAIN.getSprite(this.getTextureX(), this.getTextureY()), x, y, 64, 64, null);
    }
}
