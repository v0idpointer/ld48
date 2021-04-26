package me.v0idpointer.ld48.window;

import javax.swing.*;
import java.awt.*;

public class Window {

    private JFrame frame;

    public Window(int width, int height, String title) {
        this.frame = new JFrame();
        this.frame.setTitle(title);

        Dimension dimension = new Dimension(width, height);
        this.frame.setPreferredSize(dimension);
        this.frame.setMaximumSize(dimension);
        this.frame.setMinimumSize(dimension);

        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
        this.frame.setAlwaysOnTop(true);
        this.frame.setVisible(true);
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public int getWidth() {
        return this.frame.getWidth();
    }

    public int getHeight() {
        return this.frame.getHeight();
    }

}
