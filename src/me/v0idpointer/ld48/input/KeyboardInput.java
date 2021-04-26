package me.v0idpointer.ld48.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyboardInput extends KeyAdapter {

    public static final int KEY_FORWARD = KeyEvent.VK_W;
    public static final int KEY_BACK = KeyEvent.VK_S;
    public static final int KEY_LEFT = KeyEvent.VK_A;
    public static final int KEY_RIGHT = KeyEvent.VK_D;
    public static final int KEY_USE = KeyEvent.VK_E;

    public static final int KEY_MAGIC_Q = KeyEvent.VK_Q;
    public static final int KEY_MAGIC_E = KeyEvent.VK_E;
    public static final int KEY_MAGIC_RELEASE = KeyEvent.VK_R;
    public static final int KEY_ITEM_Z = KeyEvent.VK_Z;
    public static final int KEY_ITEM_X = KeyEvent.VK_X;

    public static final int KEY_PICKUP = KeyEvent.VK_F;
    public static final int KEY_ESCAPE = KeyEvent.VK_ESCAPE;

    private boolean[] keys;

    public KeyboardInput() {
        this.keys = new boolean[256];
        for(int i = 0; i < 256; i++) this.keys[i] = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.keys[e.getKeyCode()] = false;
    }

    public boolean isDown(int code) {
        return this.keys[code];
    }

}
