package me.v0idpointer.ld48.tile;

import java.awt.*;

public class Tile {

    public static Tile[] tiles = new Tile[128];
    public static int tileCounter = 0;

    public static final Tile stone = new SolidTile(1, 0, 0, "stone");
    public static final Tile frostbiteStone = new SolidTile(2, 1, 0, "frostbite_stone");
    public static final Tile magmaStone = new SolidTile(3, 2, 0, "magma_stone");
    public static final Tile magicStone = new SolidTile(4, 3, 0, "magic_stone");
    public static final Tile darkStone = new SolidTile(5, 4, 0, "obsidian");
    public static final Tile stoneBrick = new SolidTile(11, 5, 0, "stone_bricks");
    public static final Tile frostbiteBrick = new SolidTile(12, 6, 0, "frostbite_bricks");
    public static final Tile magmaBrick = new SolidTile(13, 7, 0, "magma_bricks");
    public static final Tile magicBrick = new SolidTile(14, 8, 0, "magic_bricks");
    public static final Tile darkBrick = new SolidTile(15, 9, 0, "dark_bricks");

    private int id;
    private int tx, ty;
    private String tileName;

    public Tile(int id, int tx, int ty, String tileName) {
        this.id = id;
        this.tx = tx;
        this.ty = ty;
        this.tileName = tileName;

        if (tiles[id] != null)
            throw new RuntimeException("Game initialization error: Tile " + id + " is already registered!");
        tiles[id] = this;
        tileCounter++;
    }

    public void render(Graphics graphics, int x, int y) { }

    public int getId() {
        return this.id;
    }

    public int getTextureX() {
        return this.tx;
    }

    public int getTextureY() {
        return this.ty;
    }

    public String getTileName() {
        return this.tileName;
    }

}
