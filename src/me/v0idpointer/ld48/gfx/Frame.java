package me.v0idpointer.ld48.gfx;

import java.awt.*;

public class Frame {

    private SpriteSheet spriteSheet;

    private Art corner;
    private Art vertical;
    private Art horizontal;

    public Frame(SpriteSheet spriteSheet) {
        this.spriteSheet = spriteSheet;

        this.corner = new Art(spriteSheet.getSprite(0, 12));
        this.horizontal = new Art(spriteSheet.getSprite(1, 12));
        this.vertical = new Art(spriteSheet.getSprite(0, 13));
    }

    public void renderWindow(Graphics graphics, int x, int y, int width, int height) {

        Art c = new Art(this.corner.getImage());
        Art v = new Art(this.vertical.getImage());
        Art h = new Art(this.horizontal.getImage());

        graphics.drawImage(c.getImage(), x, y, 64, 64, null);
        graphics.drawImage(c.getFlippedHorizontally(), x + width - 64, y, 64, 64, null);
        graphics.drawImage(h.getImage(), x + 64, y, width - 64*2, 64, null);

        graphics.drawImage(c.getFlippedVertically(), x, y + height - 64, 64,64, null);
        graphics.drawImage(c.getMultiFlipped(), x + width - 64, y + height - 64, 64,64, null);
        graphics.drawImage(h.getFlippedVertically(), x + 64, y + height - 64, width - 64*2, 64, null);

        graphics.drawImage(v.getImage(), x, y + 64, 64, height - 64*2, null);
        graphics.drawImage(v.getFlippedHorizontally(), x + width - 64, y + 64, 64, height - 64*2, null);

        graphics.setColor(new Color(0xff808080));
        graphics.fillRect(x + 64, y + 64, width - 64*2, height - 64*2);

    }

}
