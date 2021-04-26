package me.v0idpointer.ld48.gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class SpriteSheet {

    public static final int DEFAULT_SPRITE_WIDTH = 16;
    public static final int DEFAULT_SPRITE_HEIGHT = 16;

    public static final SpriteSheet TERRAIN = new SpriteSheet("terrain", DEFAULT_SPRITE_WIDTH, DEFAULT_SPRITE_HEIGHT);
    public static final SpriteSheet STORYBOARD_0 = new SpriteSheet("storyboard0", DEFAULT_SPRITE_WIDTH, DEFAULT_SPRITE_HEIGHT);
    public static final SpriteSheet STORYBOARD_1 = new SpriteSheet("storyboard1", DEFAULT_SPRITE_WIDTH, DEFAULT_SPRITE_HEIGHT);
    public static final SpriteSheet STORYBOARD_2 = new SpriteSheet("storyboard2", DEFAULT_SPRITE_WIDTH, DEFAULT_SPRITE_HEIGHT);
    public static final SpriteSheet STORYBOARD_3 = new SpriteSheet("storyboard3", DEFAULT_SPRITE_WIDTH, DEFAULT_SPRITE_HEIGHT);

    private final String name;
    private final int sw, sh;

    private BufferedImage spriteSheet;

    public SpriteSheet(String name, int sw, int sh) {
        this.name = name;
        this.sw = sw;
        this.sh = sh;

        try {
            this.spriteSheet = ImageIO.read(this.getClass().getResourceAsStream("/textures/" + name + ".png"));
        }catch(Exception ex) {
            this.spriteSheet = null;
            ex.printStackTrace();
        }
    }

    public BufferedImage getSpriteSheet() {
        return this.spriteSheet;
    }

    public BufferedImage getSprite(int x, int y) {
        return this.getSprite(x, y, this.sw, this.sh);
    }

    public BufferedImage getSprite(int x, int y, int w, int h) {
        return this.spriteSheet.getSubimage(x * this.sw, y * this.sh, w, h);
    }

    public BufferedImage getSpriteR(int x, int y, int w, int h) {
        return this.spriteSheet.getSubimage(x, y, w, h);
    }

}
