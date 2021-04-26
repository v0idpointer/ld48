package me.v0idpointer.ld48.input;

import me.v0idpointer.ld48.Game;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class MouseInput extends MouseAdapter {

    private Game gameComponent;

    private LinkedList<Point> dbPoints;
    private int dbTimer;
    private int clickedTimer = 0;

    public MouseInput(Game gameComponent) {
        this.gameComponent = gameComponent;
        this.dbPoints = new LinkedList<>();
    }

    public void mouseClicked(MouseEvent e) {
        this.dbPoints.add(new Point(e.getX(), e.getY()));
        this.clickedTimer = 4;
    }

    public int[] getMousePosition() {
        PointerInfo pi = MouseInfo.getPointerInfo();
        int mx = (int)pi.getLocation().getX() - this.gameComponent.getWindow().getFrame().getX();
        int my = (int)pi.getLocation().getY() - this.gameComponent.getWindow().getFrame().getY();
        return new int[] {mx, my, (this.clickedTimer > 0) ? 1 : 0 };
    }

    public void renderDebugInformation(Graphics g) {
        PointerInfo pi = MouseInfo.getPointerInfo();
        int mx = (int)pi.getLocation().getX() - this.gameComponent.getWindow().getFrame().getX();
        int my = (int)pi.getLocation().getY() - this.gameComponent.getWindow().getFrame().getY();

        if((mx < 16 || mx > (this.gameComponent.getWindow().getWidth() - 16)) || my < 32 || my > (this.gameComponent.getWindow().getHeight() - 16)) {
            mx = -1;
            my = -1;
        }

        if(mx >= 0 && my >= 0) {
            g.setColor(Color.blue);
            g.drawLine(mx - 8, 0, mx - 8, 600);
            g.drawLine(0, my - 32, 800, my - 32);
        }

        g.setColor(Color.white);
        g.drawString("X: " + ( (mx >= 0) ? mx : "Out of bounds!" ), 16, 48);
        g.drawString("Y: " + ( (my >= 0) ? my : "Out of bounds!" ), 16, 64);

        for(Point point : dbPoints) {
            g.setColor(Color.red);
            g.drawRect((int)(point.getX() - 8), (int)(point.getY() - 8), 16, 16);
            g.drawLine((int)(point.getX()), 0, (int)(point.getX()), 600);
            g.drawLine(0, (int)(point.getY()), 800, (int)(point.getY()));
            g.setColor(Color.white);
            g.drawString("" + ( (int)point.getX() + ", " + (int)point.getY() ), (int)point.getX(), (int)point.getY());
        }
    }

    public void renderPopDebugPoints() {
        this.dbTimer ++;
        if(this.dbTimer > 96) {
            this.dbTimer = 0;
            if(this.dbPoints.size() > 0) this.dbPoints.removeFirst();
        }
    }

    public Game getGameComponent() {
        return this.gameComponent;
    }

    public void time() {
        if(this.clickedTimer > 0) this.clickedTimer--;
    }

}
