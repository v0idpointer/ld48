package me.v0idpointer.ld48.gfx;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Font {

    private static final int fontWidth = 5;
    private static final int fontHeight = 8;

    private static final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwx";
    private static final String characters2 = "yz.,!?:;-_()[]{}+=@#$/%^'\"~0123456789<>|\\\b\f";
    private static final String characters3 = "";
    private static final int startX = 0;
    private static final int startY = 224;

    private final String fontName;
    private final SpriteSheet spriteSheet;

    public Font(String fontName, SpriteSheet spriteSheet) {
        this.fontName = fontName;
        this.spriteSheet = spriteSheet;
        System.out.println("Created a font called \"" + this.fontName + "\".");
    }

    public void RenderString(Graphics graphics, String text, int x, int y, int s, int col) {
        int l = text.length();
        for(int i = 0; i < l; i++) {
            String character = "" + text.charAt(i);
            int yo = 0;
            int io = characters.indexOf(character);
            if(io == -1) {
                io = characters2.indexOf(character);
                yo += fontHeight;
            }
            if(io == -1) {
                io = characters3.indexOf(character);
                yo += fontHeight;
            }
            if(io == -1) {
                io = 0;
                yo = 0;
            }
            BufferedImage ci = this.spriteSheet.getSpriteR(startX + io*fontWidth, startY + yo, fontWidth, fontHeight);
            Art art = new Art(ci);
            art.recolor(0xff000000, 0x00);
            art.recolor(0xffffffff, col);
            graphics.drawImage(art.getImage(), x + (i * fontWidth * s) + i*s, y, fontWidth * s, fontHeight * s, null);
        }
    }

    public String getFontName() {
        return this.fontName;
    }

    public SpriteSheet getSpriteSheet() {
        return this.spriteSheet;
    }

}
