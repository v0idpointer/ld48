package me.v0idpointer.ld48.spell;

import me.v0idpointer.ld48.entity.Player;

public abstract class Spell {

    public static Spell[] spells = new Spell[128];
    public static int spellCounter = 0;

    public static Spell fireSpell = new FireSpell();
    public static Spell iceSpell = new IceSpell();
    public static Spell lightSpell = new LightSpell();
    public static Spell currentSpell = new CurrentSpell();

    private final int id;
    private final String spellCategoryName;
    private final String[] lore;
    private final int categoryTx, categoryTy;
    private int lowTx, lowTy, lowId;
    private int mediumTx, mediumTy, mediumId;
    private int highTx, highTy, highId;
    private String lowName, mediumName, highName;
    private String[] lowLore, mediumLore, highLore;

    public Spell(int id, String spellCategoryName, int tx, int ty, String...lore) {
        this.id = id;
        this.spellCategoryName = spellCategoryName;
        this.categoryTx = tx;
        this.categoryTy = ty;
        this.lore = lore;

        if(spells[id] != null) throw new RuntimeException("Game initialization error: Spell id " + id + " is already registered.");
        spells[id] = this;
        spellCounter++;
    }

    public void createLowMagic(String name, int tx, int ty, String...lore) {
        this.lowId = this.id + 1;
        this.lowTx = tx;
        this.lowTy = ty;
        this.lowName = name;
        this.lowLore = lore;
    }

    public void createMediumMagic(String name, int tx, int ty, String...lore) {
        this.mediumId = this.id + 2;
        this.mediumTx = tx;
        this.mediumTy = ty;
        this.mediumName = name;
        this.mediumLore = lore;
    }

    public void createHighMagic(String name, int tx, int ty, String...lore) {
        this.highId = this.id + 3;
        this.highTx = tx;
        this.highTy = ty;
        this.highName = name;
        this.highLore = lore;
    }

    public int getId() {
        return id;
    }

    public String getSpellCategoryName() {
        return spellCategoryName;
    }

    public String[] getLore() {
        return lore;
    }

    public int getCategoryTx() {
        return categoryTx;
    }

    public int getCategoryTy() {
        return categoryTy;
    }

    public int getLowTx() {
        return lowTx;
    }

    public void setLowTx(int lowTx) {
        this.lowTx = lowTx;
    }

    public int getLowTy() {
        return lowTy;
    }

    public void setLowTy(int lowTy) {
        this.lowTy = lowTy;
    }

    public int getLowId() {
        return lowId;
    }

    public void setLowId(int lowId) {
        this.lowId = lowId;
    }

    public int getMediumTx() {
        return mediumTx;
    }

    public void setMediumTx(int mediumTx) {
        this.mediumTx = mediumTx;
    }

    public int getMediumTy() {
        return mediumTy;
    }

    public void setMediumTy(int mediumTy) {
        this.mediumTy = mediumTy;
    }

    public int getMediumId() {
        return mediumId;
    }

    public void setMediumId(int mediumId) {
        this.mediumId = mediumId;
    }

    public int getHighTx() {
        return highTx;
    }

    public void setHighTx(int highTx) {
        this.highTx = highTx;
    }

    public int getHighTy() {
        return highTy;
    }

    public void setHighTy(int highTy) {
        this.highTy = highTy;
    }

    public int getHighId() {
        return highId;
    }

    public void setHighId(int highId) {
        this.highId = highId;
    }

    public String getLowName() {
        return lowName;
    }

    public void setLowName(String lowName) {
        this.lowName = lowName;
    }

    public String getMediumName() {
        return mediumName;
    }

    public void setMediumName(String mediumName) {
        this.mediumName = mediumName;
    }

    public String getHighName() {
        return highName;
    }

    public void setHighName(String highName) {
        this.highName = highName;
    }

    public String[] getLowLore() {
        return lowLore;
    }

    public void setLowLore(String[] lowLore) {
        this.lowLore = lowLore;
    }

    public String[] getMediumLore() {
        return mediumLore;
    }

    public void setMediumLore(String[] mediumLore) {
        this.mediumLore = mediumLore;
    }

    public String[] getHighLore() {
        return highLore;
    }

    public void setHighLore(String[] highLore) {
        this.highLore = highLore;
    }

    public abstract void callLow(Player player);
    public abstract void callMedium(Player player);
    public abstract void callHigh(Player player);
}
