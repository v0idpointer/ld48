package me.v0idpointer.ld48.partice;

import me.v0idpointer.ld48.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Particle {

    private int x, y;
    private BufferedImage image;
    private int iw, ih;
    private int life;

    public Particle(int x, int y, BufferedImage image, int iw, int ih, int life) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.iw = iw;
        this.ih = ih;
        this.life = life;
    }

    public void update() {
        this.life --;
        if(this.life <= 0) {
            Game.instance.getDisplayComponent().world.getParticles().remove(this);
        }
    }

    public void render(Graphics g) {
        g.drawImage(this.image, this.x - this.iw/2, this.y - this.ih/2, this.iw, this.ih, null);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getIw() {
        return iw;
    }

    public void setIw(int iw) {
        this.iw = iw;
    }

    public int getIh() {
        return ih;
    }

    public void setIh(int ih) {
        this.ih = ih;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
}
