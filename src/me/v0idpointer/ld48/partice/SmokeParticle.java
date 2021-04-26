package me.v0idpointer.ld48.partice;

import java.awt.*;
import java.util.Random;

public class SmokeParticle extends Particle {

    public static final int SMOKE = 0xFF454545;
    public static final int STEAM = 0xFFD3D3D3;
    public static final int FLAME_TRAIL = 0xFFCC0000;

    private int fadeInTimer;
    private int color;
    private int size;
    private boolean fadeOut;
    private final boolean auto;

    public SmokeParticle(int x, int y, int life, int color, int fadeInTimer, int size, boolean fadeOut, boolean auto) {
        super(x, y, null, 0, 0, life);
        this.fadeInTimer = fadeInTimer;
        this.color = color;
        this.size = size;
        this.fadeOut = fadeOut;
        this.auto = auto;
    }

    @Override
    public void update() {
        super.update();

        if(this.auto) this.setY(this.getY() - 4);

        if(this.fadeInTimer > 0) this.fadeInTimer--;
        if(this.fadeOut) {
            if(new Random().nextInt(4) == 1) this.size -= 8;
        }
    }

    @Override
    public void render(Graphics g) {
        if(this.fadeInTimer > 0) return;

        Graphics2D g2d = (Graphics2D)g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        g.setColor(new Color(this.color));
        g.fillRect(this.getX() - this.size/2, this.getY() - this.size/2, this.size, this.size);

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.00f));
    }
}
