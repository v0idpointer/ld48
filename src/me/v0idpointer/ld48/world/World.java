package me.v0idpointer.ld48.world;

import me.v0idpointer.ld48.Game;
import me.v0idpointer.ld48.entity.Entity;
import me.v0idpointer.ld48.entity.Player;
import me.v0idpointer.ld48.gfx.SpriteSheet;
import me.v0idpointer.ld48.partice.Particle;
import me.v0idpointer.ld48.tile.Tile;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class World {

    private String name;
    private int worldWidth = 16;
    private int worldHeight = 16;
    private int[] tiles;
    private CopyOnWriteArrayList<Entity> entities;
    private CopyOnWriteArrayList<Particle> particles;
    private boolean levelOpen = false;
    private int enemyCounter = 0;
    private int dx = 0;
    private boolean pauseAI = false;

    public World(String name) {
        this.name = name;

        this.tiles = new int[this.worldWidth * this.worldHeight];
        this.entities = new CopyOnWriteArrayList<>();
        this.particles = new CopyOnWriteArrayList<>();
    }

    public void update() {
        for(Entity entity : entities) if(!((((Game.instance.flags >> 1) & 1) == 1) && entity.hasAi()) && ( ( entity.getId() == 1 ? true : !this.pauseAI ) )) {
            entity.update();
            if(!entity.hasNoClipPrivilege() && entity.getY() < 96) {
                if(entity instanceof Player) {
                    if(this.levelOpen) {
                        if(!(entity.getX() > 64 + this.dx && entity.getX() < 64 + this.dx + 128)) entity.setY(96);
                        else {
                            entity.setY(64);
                            Game.instance.getDisplayComponent().world = WorldGeneration.generate(Integer.parseInt(this.name) - 1, (Player) entity);
                        }
                    }else{
                        entity.setY(96);
                    }
                }
            }
            if(entity.getY() + 64 + 16 > 600) entity.setY(entity.getY() - 6);
            if(entity.getX() < 0) entity.setX(0);
            if(entity.getX() + 32 > 800) entity.setX(768);
        }
        if(!Game.instance.getHUD().hasTarget()) {
            Entity entity = this.getEntityByName("player");
            Game.instance.getHUD().attachTarget(entity);
        }
        for(Particle particle : particles) particle.update();

        if(this.enemyCounter <= 0) this.levelOpen = true;
    }

    public void render(Graphics g) {
        for(int i = 0; i < this.tiles.length; i++) {
            int xi = i % this.worldWidth;
            int yi = i / this.worldWidth;
            Tile.tiles[this.tiles[i]].render(g, xi * 64, yi * 64);
        }

        for(int yy = 0; yy < 2; yy++) {
            for(int xx = 0; xx < 13; xx ++) {
                g.drawImage(SpriteSheet.TERRAIN.getSprite(Tile.tiles[this.tiles[0] + 10].getTextureX(), 0), xx * 64 - (yy * 48), yy * 64, 64, 64, null);
            }
        }

        int to = ( levelOpen ? -1 : 0 );
        g.drawImage(SpriteSheet.TERRAIN.getSprite(10 + to*2, 1), 64 + this.dx, 0, 64, 64, null);
        g.drawImage(SpriteSheet.TERRAIN.getSprite(10 + to*2, 2), 64 + this.dx, 64, 64, 64, null);
        g.drawImage(SpriteSheet.TERRAIN.getSprite(11 + to*2, 1), 128 + this.dx, 0, 64, 64, null);
        g.drawImage(SpriteSheet.TERRAIN.getSprite(11 + to*2, 2), 128 + this.dx, 64, 64, 64, null);

        for(Entity entity : entities) {
            if(!( ( entity.getId() == 1 ? true : !this.pauseAI ) )) continue;
            entity.render(g);
            if(entity.hasAi() && entity.getHealth() > 0 && entity.getHealth() < entity.getMaxHealth()) {
                g.setColor(Color.gray);
                g.fillRect(entity.getX() - 8, entity.getY() - 8 - entity.getBarOffsetY(), 64, 8);
                g.setColor(Color.green);
                g.fillRect(entity.getX() - 8, entity.getY() - 8 - entity.getBarOffsetY(), (int)( (entity.getHealth() / (entity.getMaxHealth() * 1.0d)) * 64), 8);
            }
            if(((Game.instance.flags >> 5) & 1) == 1) {
                g.setColor(Color.blue);
                g.drawRect(entity.getHitbox().x, entity.getHitbox().y, entity.getHitbox().width, entity.getHitbox().height);
            }
        }
        for(Particle particle : particles) particle.render(g);
    }

    public String getName() {
        return this.name;
    }

    public Entity getEntityByName(String name) {
        Entity e = null;
        for(Entity entity : this.entities) {
            if(entity.getName().equalsIgnoreCase(name)) e = entity;
        }
        return e;
    }

    public CopyOnWriteArrayList<Entity> getEntities() {
        return this.entities;
    }

    public CopyOnWriteArrayList<Particle> getParticles() {
        return this.particles;
    }

    public int[] getTiles() {
        return this.tiles;
    }

    public boolean isLevelOpen() {
        return levelOpen;
    }

    public void setLevelOpen(boolean levelOpen) {
        this.levelOpen = levelOpen;
    }

    public int getEnemyCounter() {
        return enemyCounter;
    }

    public void setEnemyCounter(int enemyCounter) {
        this.enemyCounter = enemyCounter;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void pauseAI() {
        this.pauseAI = true;
    }

    public void unpauseAI() {
        this.pauseAI = false;
    }
}
