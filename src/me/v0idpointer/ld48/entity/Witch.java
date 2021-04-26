package me.v0idpointer.ld48.entity;

import me.v0idpointer.ld48.Game;
import me.v0idpointer.ld48.gfx.Art;
import me.v0idpointer.ld48.gfx.SpriteSheet;
import me.v0idpointer.ld48.sfx.Sound;

import java.awt.*;
import java.util.Random;

public class Witch extends Entity {


    private enum MobAnimationState {
        BACK_STILL() {
            @Override
            public int[] getTextureXY() {
                return new int[] {7, 4};
            }
        },
        FORWARD_STILL() {
            @Override
            public int[] getTextureXY() {
                return new int[] {9, 4};
            }
        },
        SIDE_STILL() {
            @Override
            public int[] getTextureXY() {
                return new int[] {8, 4};
            }
        },
        BACK_RUN_0() {
            @Override
            public int[] getTextureXY() {
                return new int[] {7, 5};
            }
        },
        BACK_RUN_1() {
            @Override
            public int[] getTextureXY() {
                return new int[] {8, 5};
            }
        },
        FORWARD_RUN_0() {
            @Override
            public int[] getTextureXY() {
                return new int[] {9, 5};
            }
        },
        FORWARD_RUN_1() {
            @Override
            public int[] getTextureXY() {
                return new int[] {10, 5};
            }
        },
        SIDE_RUN_0() {
            @Override
            public int[] getTextureXY() {
                return new int[] {7, 6};
            }
        },
        SIDE_RUN_1() {
            @Override
            public int[] getTextureXY() {
                return new int[] {8, 6};
            }
        },
        SUMMON() {
            @Override
            public int[] getTextureXY() {
                return new int[] {9, 6};
            }
        };

        public abstract int[] getTextureXY();
    }

    private MobAnimationState animationState = MobAnimationState.FORWARD_STILL;
    private int facing = 0;
    private boolean isFlipped = false;
    private int animationTimer = 0;
    private boolean animationFrame = false;

    private int targetX = -1, targetY = -1;
    private boolean ready = false;
    private int lastKnownHealth;
    private boolean summonActive = false;
    private int summonCountdown = 0;
    private int summonType = 0;

    public Witch(int x, int y) {
        super(60, x, y, "elaina_");
        this.setMaxHealth(75);
        this.setHealth(75);
        this.setHasAi(true);
        this.setBarOffsetY(48);
    }

    @Override
    public void update() {
        this.setX(this.getX() + this.getVelX());
        this.setY(this.getY() + this.getVelY());

        Random random = new Random();

        if(!this.ready) {
            if(targetX == -1 && targetY == -1) {
                targetX = 32 + random.nextInt(672);
                targetY = 128 + random.nextInt(408);
            }else{
                double d = Math.sqrt( (this.getX() - targetX)*(this.getX() - targetX) + (this.getY() - targetY)*(this.getY() - targetY) );

                int dx = 0, dy = 0;
                dx = ( targetX > this.getX() ) ? 1 : -1;
                dy = ( targetY > this.getY() ) ? 1 : -1;

                this.setVelX(3 * dx);
                this.setVelY(3 * dy);

                if(d <= 32.0) {
                    targetX = -1;
                    targetY = -1;
                    this.setVelX(0);
                    this.setVelY(0);
                    this.lastKnownHealth = this.getHealth();
                    this.summonActive = false;
                    ready = true;
                }

            }
        }else {
            if(this.getHealth() != this.lastKnownHealth) ready = false;
            this.facing = 0;

            if(this.summonActive) {
                this.summonCountdown--;

                if(this.summonCountdown < 0 && this.summonType == 1) {
                    this.summonCountdown = 0;
                    this.summonActive = false;
                    this.summonType = 0;

                    Game.instance.getDisplayComponent().world.getEntities().add(new WitchBeam(this.getX(), this.getY(), 0, -1));
                    Game.instance.getDisplayComponent().world.getEntities().add(new WitchBeam(this.getX(), this.getY(), 1, -1));
                    Game.instance.getDisplayComponent().world.getEntities().add(new WitchBeam(this.getX(), this.getY(), 1, 0));
                    Game.instance.getDisplayComponent().world.getEntities().add(new WitchBeam(this.getX(), this.getY(), 1, 1));
                    Game.instance.getDisplayComponent().world.getEntities().add(new WitchBeam(this.getX(), this.getY(), 0, 1));
                    Game.instance.getDisplayComponent().world.getEntities().add(new WitchBeam(this.getX(), this.getY(), -1, 1));
                    Game.instance.getDisplayComponent().world.getEntities().add(new WitchBeam(this.getX(), this.getY(), -1, 0));
                    Game.instance.getDisplayComponent().world.getEntities().add(new WitchBeam(this.getX(), this.getY(), -1, -1));

                    Sound.witch_summon.play();
                }

                if(this.summonCountdown < 0 && this.summonType == 2) {
                    this.summonCountdown = 0;
                    this.summonActive = false;
                    this.summonType = 0;

                    Gem gem = null;
                    for(Entity entity : Game.instance.getDisplayComponent().world.getEntities()) {
                        if(entity instanceof Gem) gem = (Gem)entity;
                    }
                    if(gem != null) {
                        Game.instance.getDisplayComponent().world.getEntities().remove(gem);
                        Game.instance.getDisplayComponent().world.getEntities().add(new ManaSpirit(gem.getX(), gem.getY()));
                        Sound.consume_mana.play();
                    }

                }

            }else {

                if(random.nextInt(190) == 2) {
                    this.summonCountdown = 64 + random.nextInt(64);
                    this.summonActive = true;
                    this.summonType = 1;
                }

                if(!this.summonActive && random.nextInt(170) == 2) {

                    boolean match = false;
                    for(Entity entity : Game.instance.getDisplayComponent().world.getEntities()) {
                        if(entity instanceof Gem) match = true;
                    }
                    if(!match) return;

                    this.summonCountdown = 32 + random.nextInt(64);
                    this.summonActive = true;
                    this.summonType = 2;
                }

            }

        }

        updateDirection();
        updateAnimations((short) 16);
        updateSprite();

        if(this.getHealth() <= 0) {
            this.summonManaSpiritReward();
            Game.instance.getDisplayComponent().world.getEntities().remove(this);
            Game.instance.getDisplayComponent().world.setEnemyCounter(Game.instance.getDisplayComponent().world.getEnemyCounter() - 1);
        }
    }

    @Override
    public void render(Graphics g) {
        Art art = new Art(SpriteSheet.TERRAIN.getSprite(this.animationState.getTextureXY()[0], this.animationState.getTextureXY()[1]));
        g.drawImage(( (this.isFlipped) ? art.getFlippedHorizontally() : art.getImage()), this.getX() - 48, this.getY() - 48, 96, 96, null );
    }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(this.getX() - 24, this.getY() - 24, 48, 48);
    }

    public void updateDirection() {

        if(this.getVelX() > 0) this.facing = 2;
        if(this.getVelX() < 0) this.facing = 3;

        if(this.getVelY() > 0) this.facing = 0;
        if(this.getVelY() < 0) this.facing = 1;

    }

    public void updateAnimations(short max) {
        this.animationTimer ++;
        if(this.animationTimer >= max) {
            this.animationTimer = 0;
            this.animationFrame = !this.animationFrame;
        }
    }

    public void updateSprite() {
        this.isFlipped = false;

        if(this.summonCountdown > 0 && this.ready) {
            this.animationState = MobAnimationState.SUMMON;
            return;
        }

        if (this.getVelX() == 0 && this.getVelY() == 0) {

            switch (this.facing) {
                case 0:
                    this.animationState = MobAnimationState.BACK_STILL;
                    break;

                case 1:
                    this.animationState = MobAnimationState.FORWARD_STILL;
                    break;

                case 2:
                    this.animationState = MobAnimationState.SIDE_STILL;
                    break;

                case 3:
                    this.animationState = MobAnimationState.SIDE_STILL;
                    this.isFlipped = true;
                    break;
            }

        } else {

            switch (this.facing) {
                case 0:
                    this.animationState = (this.animationFrame) ? MobAnimationState.BACK_RUN_0 : MobAnimationState.BACK_RUN_1;
                    break;

                case 1:
                    this.animationState = (this.animationFrame) ? MobAnimationState.FORWARD_RUN_0 : MobAnimationState.FORWARD_RUN_1;
                    break;

                case 2:
                    this.animationState = (this.animationFrame) ? MobAnimationState.SIDE_RUN_0 : MobAnimationState.SIDE_RUN_1;
                    break;

                case 3:
                    this.animationState = (this.animationFrame) ? MobAnimationState.SIDE_RUN_0 : MobAnimationState.SIDE_RUN_1;
                    this.isFlipped = true;
                    break;
            }

        }
    }
}
