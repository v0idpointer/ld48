package me.v0idpointer.ld48.entity;

import me.v0idpointer.ld48.Game;

import java.awt.*;
import java.util.Random;

public abstract class Entity {

    private int id;
    private int x;
    private int y;
    private String name;

    private int velX;
    private int velY;

    private int health = 120;
    private int power = 200;
    private boolean hasAi = false;
    private int maxHealth = 120;

    private int barOffsetY = 0;
    private boolean noClipPrivilege = false;

    public Entity(int id, int x, int y, String name) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public abstract void update();
    public abstract void render(Graphics g);
    public abstract Rectangle getHitbox();

    public int getId() {
        return id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public int getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public Rectangle getDefaultHitbox() {
        return new Rectangle(this.x, this.y, 64, 64);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public boolean hasAi() {
        return hasAi;
    }

    public void setHasAi(boolean hasAi) {
        this.hasAi = hasAi;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getBarOffsetY() {
        return barOffsetY;
    }

    public void setBarOffsetY(int barOffsetY) {
        this.barOffsetY = barOffsetY;
    }

    public boolean hasNoClipPrivilege() {
        return noClipPrivilege;
    }

    public void setNoClipPrivilege(boolean noClipPrivilege) {
        this.noClipPrivilege = noClipPrivilege;
    }

    public void summonManaSpiritReward() {
        Random random = new Random();
        for(int i = 0; i < random.nextInt(3); i++) {
            int x = this.getX() + 16;
            int y = this.getY() + 16;

            int dx, dy;
            dx = random.nextInt(3) - 1;
            dy = random.nextInt(3) - 1;

            x += (dx * (random.nextInt(32)));
            y += (dy * (random.nextInt(32)));

            Game.instance.getDisplayComponent().world.getEntities().add(new ManaSpirit(x, y));
        }
    }

}
