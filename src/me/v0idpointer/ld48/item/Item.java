package me.v0idpointer.ld48.item;

public class Item {

    public static Item[] items = new Item[128];
    public static int itemCounter = 0;

    public static Item healthPotion = new Item(1, 0, 6, "Health Potion", 1, "On use: restores 55 health", "", "", "", "... healthy!");
    public static Item shield = new Item(2, 1, 6, "Shield of go f*** yourself", 1);
    public static Item book = new Item(3, 2, 6, "Eternal Scarlet", 1,
            "This vintage spellbook", "contains the most powerful", "spells a wizard would", "need.", "", "Imbued with an ancient power");
    public static Item sword = new Item(4, 3, 6, "Desert Rose", 1);
    public static Item spellBookPage = new Item(5, 0, 7, "Spell Book Page", 1);
    public static Item manaPotion = new Item(6, 1, 7, "Cloudy Potion", 1, "On use: restores 120 mana", "", "", "", "it's very blue..");
    public static Item redoHealPotion = new Item(8, 3, 9, "Potion of Un-Healing", 1, "This potion can redo", "any healing action.");

    private final int id;
    private final int tx;
    private final int ty;
    private final String itemName;
    private int durability;
    private final String[] lore;

    public Item(int id, int tx, int ty, String itemName, int durability, String...lore) {
        this.id = id;
        this.tx = tx;
        this.ty = ty;
        this.itemName = itemName;
        this.durability = durability;
        this.lore = lore;

        if(items[id] != null) throw new RuntimeException("Game initialization error: Item " + id + " is already registered.");
        items[id] = this;
        itemCounter ++;
    }

    public int getId() {
        return id;
    }

    public int getTx() {
        return tx;
    }

    public int getTy() {
        return ty;
    }

    public String getItemName() {
        return itemName;
    }

    public String[] getLore() {
        return lore;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }
}
