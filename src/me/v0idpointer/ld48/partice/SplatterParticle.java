package me.v0idpointer.ld48.partice;

import java.awt.*;

public class SplatterParticle extends Particle {

    private final int xo;
    private final int color;

    private int mx = 0;

    public SplatterParticle(int x, int y, int color, int life, int xo) {
        super(x, y, null, 0, 0, life);
        this.xo = xo;
        this.color = color;
    }

    @Override
    public void update() {
        super.update();

        if(mx != Math.abs(this.xo)) {
            if(this.xo != 0) {
                this.setX(this.getX() + (int)( 2 * Math.signum(xo) ));
                this.mx ++;
                if((Math.abs(this.xo)) / 2 > this.mx) {
                    this.setY(this.getY() - 2);
                }else{
                    this.setY(this.getY() + 2);
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(color));
        g.fillRect(this.getX() - 4, this.getY() - 4, 8, 8);
    }
}
