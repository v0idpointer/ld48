package me.v0idpointer.ld48.entity;

import me.v0idpointer.ld48.Game;
import me.v0idpointer.ld48.GameState;
import me.v0idpointer.ld48.gfx.Art;
import me.v0idpointer.ld48.gfx.SpriteSheet;
import me.v0idpointer.ld48.input.KeyboardInput;
import me.v0idpointer.ld48.item.Item;
import me.v0idpointer.ld48.sfx.Sound;
import me.v0idpointer.ld48.spell.Spell;

import java.awt.*;
import java.util.Random;

public class Player extends Entity {

    private enum PlayerAnimationState {
        BACK_STILL() {
            @Override
            public int[] getTextureXY() {
                return new int[] {0, 1};
            }
        },
        FORWARD_STILL() {
            @Override
            public int[] getTextureXY() {
                return new int[] {2, 1};
            }
        },
        SIDE_STILL() {
            @Override
            public int[] getTextureXY() {
                return new int[] {1, 1};
            }
        },
        BACK_RUN_0() {
            @Override
            public int[] getTextureXY() {
                return new int[] {0, 2};
            }
        },
        BACK_RUN_1() {
            @Override
            public int[] getTextureXY() {
                return new int[] {1, 2};
            }
        },
        FORWARD_RUN_0() {
            @Override
            public int[] getTextureXY() {
                return new int[] {2, 2};
            }
        },
        FORWARD_RUN_1() {
            @Override
            public int[] getTextureXY() {
                return new int[] {3, 2};
            }
        },
        SIDE_RUN_0() {
            @Override
            public int[] getTextureXY() {
                return new int[] {0, 3};
            }
        },
        SIDE_RUN_1() {
            @Override
            public int[] getTextureXY() {
                return new int[] {1, 3};
            }
        };

        public abstract int[] getTextureXY();
    }

    private PlayerAnimationState animationState = PlayerAnimationState.FORWARD_STILL;
    private int facing = 0;
    private boolean isFlipped = false;
    private int animationTimer = 0;
    private boolean animationFrame = false;

    private Item itemZ = Item.book;
    private Item itemX = null;
    private Spell spell = Spell.fireSpell;

    private int manaTimer = 0;
    private int castCooldown = 64;
    private int releaseCharge = 0;
    private int healthTimer = 0;
    private int freezeTimer = 0;
    private int burningTimer = 0;
    private int gems = 0;
    private int unlockedSpells = 0;

    private boolean bookOpen = false;

    private int healthPotionAmount = 0;
    private int manaPotionAmount = 0;

    public Player(int x, int y) {
        super(1, x, y, "player");
    }

    @Override
    public void update() {
        this.setX(this.getX() + this.getVelX());
        this.setY(this.getY() + this.getVelY());

        int dirX, dirY;

        if(Game.instance.getKeyboardInput().isDown(KeyboardInput.KEY_FORWARD) && !Game.instance.getKeyboardInput().isDown(KeyboardInput.KEY_BACK)) dirY = -1;
        else if (!Game.instance.getKeyboardInput().isDown(KeyboardInput.KEY_FORWARD) && Game.instance.getKeyboardInput().isDown(KeyboardInput.KEY_BACK)) dirY = 1;
        else dirY = 0;

        if(Game.instance.getKeyboardInput().isDown(KeyboardInput.KEY_LEFT) && !Game.instance.getKeyboardInput().isDown(KeyboardInput.KEY_RIGHT)) dirX = -1;
        else if (!Game.instance.getKeyboardInput().isDown(KeyboardInput.KEY_LEFT) && Game.instance.getKeyboardInput().isDown(KeyboardInput.KEY_RIGHT)) dirX = 1;
        else dirX = 0;

        this.setVelX(5 * dirX);
        this.setVelY(5 * dirY);

        updateDirection();
        updateAnimations((short) 16);
        updateSprite();

        handleKeyInput();

        if(this.getPower() < 200) {
            this.manaTimer--;
            if(this.manaTimer <= 0) {
                this.setPower(this.getPower() + 5);
                if(this.getPower() > 200) this.setPower(200);
                this.manaTimer = 48;
                if(this.spell == Spell.currentSpell) this.manaTimer *= 3;
            }
        }

        if(this.getHealth() < 120) {
            this.healthTimer--;
            if(this.healthTimer <= 0) {
                this.setHealth(this.getHealth() + 2);
                if(this.getHealth() > 120) this.setHealth(120);
                this.healthTimer = 300;
            }
        }

        if(this.freezeTimer > 0) {
            this.freezeTimer --;
            if(new Random().nextInt(30) == 0) this.setHealth(this.getHealth() - 1);
            this.setVelX( (int)( (this.getVelX() * 2.0f) / 4.0f ) );
            this.setVelY( (int)( (this.getVelY() * 2.0f) / 4.0f ) );
        }

        if(this.burningTimer > 0) {
            this.burningTimer --;
            if(new Random().nextInt(18) == 0) this.setHealth(this.getHealth() - 1);
        }

        if(((Game.instance.flags >> 2) & 1) == 1) {
            this.setHealth(this.getMaxHealth());
            this.setPower(200);
            this.releaseCharge = 600;
            this.unlockedSpells = 15;
        }
        if(((Game.instance.flags >> 9) & 1) == 1){
            this.unlockedSpells = 15;
        }

        this.updateBook();

        if(this.getHealth() <= 0) {
            Game.instance.getMenu().displayDeathPage(Integer.parseInt(Game.instance.getDisplayComponent().world.getName()), this.getGems());
        }
    }

    @Override
    public void render(Graphics g) {
        Art art = new Art(SpriteSheet.TERRAIN.getSprite(this.animationState.getTextureXY()[0], this.animationState.getTextureXY()[1]));
        g.drawImage(( (this.isFlipped) ? art.getFlippedHorizontally() : art.getImage()), this.getX() - 48, this.getY() - 48, 96, 96, null );
        this.renderBook(g);
    }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(this.getX() - 16, this.getY() - 16, 32, 48);
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

        if (this.getVelX() == 0 && this.getVelY() == 0) {

            switch (this.facing) {
                case 0:
                    this.animationState = PlayerAnimationState.BACK_STILL;
                    break;

                case 1:
                    this.animationState = PlayerAnimationState.FORWARD_STILL;
                    break;

                case 2:
                    this.animationState = PlayerAnimationState.SIDE_STILL;
                    break;

                case 3:
                    this.animationState = PlayerAnimationState.SIDE_STILL;
                    this.isFlipped = true;
                    break;
            }

        } else {

            switch (this.facing) {
                case 0:
                    this.animationState = (this.animationFrame) ? PlayerAnimationState.BACK_RUN_0 : PlayerAnimationState.BACK_RUN_1;
                    break;

                case 1:
                    this.animationState = (this.animationFrame) ? PlayerAnimationState.FORWARD_RUN_0 : PlayerAnimationState.FORWARD_RUN_1;
                    break;

                case 2:
                    this.animationState = (this.animationFrame) ? PlayerAnimationState.SIDE_RUN_0 : PlayerAnimationState.SIDE_RUN_1;
                    break;

                case 3:
                    this.animationState = (this.animationFrame) ? PlayerAnimationState.SIDE_RUN_0 : PlayerAnimationState.SIDE_RUN_1;
                    this.isFlipped = true;
                    break;
            }

        }
    }

    public Item getItemZ() {
        return itemZ;
    }

    public void setItemZ(Item itemZ) {
        this.itemZ = itemZ;
    }

    public Item getItemX() {
        return itemX;
    }

    public void setItemX(Item itemX) {
        this.itemX = itemX;
    }

    public Spell getSpell() {
        return spell;
    }

    public void setSpell(Spell spell) {
        this.spell = spell;
    }

    public void handleKeyInput() {
        if(castCooldown > 0) {
            this.castCooldown --;
            return;
        }

        if(Game.instance.getKeyboardInput().isDown(KeyboardInput.KEY_MAGIC_Q)) {
            this.castCooldown = 32;
            this.spell.callLow(this);
        }
        if(Game.instance.getKeyboardInput().isDown(KeyboardInput.KEY_MAGIC_E)) {
            this.spell.callMedium(this);
            this.castCooldown = 48;
        }
        if(Game.instance.getKeyboardInput().isDown(KeyboardInput.KEY_MAGIC_RELEASE) && releaseCharge >= 600) {
            this.spell.callHigh(this);
            this.castCooldown = 128;
        }

        if(Game.instance.getKeyboardInput().isDown(KeyboardInput.KEY_ITEM_Z)) {
            this.bookOpen = !this.bookOpen;
            this.castCooldown = 16;
        }

        if(Game.instance.getKeyboardInput().isDown(KeyboardInput.KEY_ESCAPE) && !this.isBookOpen()) {
            Game.instance.state = GameState.MAIN_MENU;
            Game.instance.getMenu().sendPausedMessage();
            this.castCooldown = 16;
        }

        if(Game.instance.getKeyboardInput().isDown(KeyboardInput.KEY_ESCAPE) && this.isBookOpen()) {
            this.bookOpen = false;
            this.castCooldown = 16;
        }

        if(Game.instance.getKeyboardInput().isDown(KeyboardInput.KEY_ITEM_X)) {
            this.castCooldown = 16;
            this.useSelectedItem();
        }

        if(Game.instance.getKeyboardInput().isDown(KeyboardInput.KEY_PICKUP)) {
            for(Entity e: Game.instance.getDisplayComponent().world.getEntities()) {
                if(e instanceof DroppedItem) {
                    DroppedItem di = (DroppedItem) e;
                    DroppedItem ni = new DroppedItem(this.getX(), this.getY(), this.itemX.getId(), this.getSelectedItemAmount());
                    this.setSelectedItemAmount(0);
                    this.setItemX(Item.items[di.getValue()]);
                    Game.instance.getDisplayComponent().world.getEntities().add(ni);
                    Game.instance.getDisplayComponent().world.getEntities().remove(di);
                    this.castCooldown = 16;
                }
            }
        }

    }

    public int getFacing() {
        return facing;
    }

    public void setFacing(int facing) {
        this.facing = facing;
    }

    public int getReleaseCharge() {
        return releaseCharge;
    }

    public void setReleaseCharge(int releaseCharge) {
        this.releaseCharge = releaseCharge;
    }

    public void setPosition(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    public int getFreezeTimer() {
        return freezeTimer;
    }

    public void setFreezeTimer(int freezeTimer) {
        this.freezeTimer = freezeTimer;
    }

    public int getBurningTimer() {
        return burningTimer;
    }

    public void setBurningTimer(int burningTimer) {
        this.burningTimer = burningTimer;
    }

    public int getGems() {
        return gems;
    }

    public void setGems(int gems) {
        this.gems = gems;
    }

    public int getCastCooldown() {
        return castCooldown;
    }

    public void setCastCooldown(int castCooldown) {
        this.castCooldown = castCooldown;
    }

    public void updateBook() {
        if(this.bookOpen) Game.instance.getDisplayComponent().world.pauseAI();
        else Game.instance.getDisplayComponent().world.unpauseAI();

        if(!this.bookOpen) return;

        int[] mx = Game.instance.getMouseInput().getMousePosition();
        Rectangle mr = new Rectangle(mx[0], mx[1], 1, 1);

        if(mx[2] == 1) {
            if(mr.intersects(new Rectangle(48 + 0 * (161 + 16), 128 + 64, 161, 256))) {
                this.spell = Spell.fireSpell;
                Sound.select.play();
            }
            if(mr.intersects(new Rectangle(48 + 1 * (161 + 16), 128 + 64, 161, 256)) && ( ((this.unlockedSpells >> 1) & 1) == 1 )) {
                this.spell = Spell.iceSpell;
                Sound.select.play();
            }
            if(mr.intersects(new Rectangle(48 + 2 * (161 + 16), 128 + 64, 161, 256)) && ( ((this.unlockedSpells >> 2) & 1) == 1 )) {
                this.spell = Spell.lightSpell;
                Sound.select.play();
            }
            if(mr.intersects(new Rectangle(48 + 3 * (161 + 16), 128 + 64, 161, 256)) && ( ((this.unlockedSpells >> 3) & 1) == 1 )) {
                this.spell = Spell.currentSpell;
                Sound.select.play();
            }
        }
    }

    public void renderBook(Graphics g) {
        if(!this.bookOpen) return;

        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.darkGray);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
        g2d.fillRect(0, 0, 800, 600);

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.00f));

        Game.instance.getFrame().renderWindow(g, 24, 80, 740, 480);
        Game.instance.getFont().RenderString(g, "Spellbook", 256+48, 128, 3, 0xffffffff);

        for(int i = 0; i < 4; i++ ) {
            g.setColor( ( (i + 1) * 10 == this.spell.getId() ) ? new Color(0xFF00AA0E) : Color.darkGray );
            g.fillRect(48 + i * (161 + 16), 128 + 64, 161, 256);

            if( ((this.unlockedSpells >> i) & 1) == 1 || i == 0) {
                Game.instance.getFont().RenderString(g, Spell.spells[ (i + 1) * 10 ].getSpellCategoryName(), 64 + i * (161 + 16), 256-48, 2, 0xffffffff);
                int ii = 0;
                for(String lore : Spell.spells[ (i + 1) * 10 ].getLore()) {
                    Game.instance.getFont().RenderString(g, lore, 64 + i * (161 + 16), 256-24 + ii*(10), 1, 0xffffffff);
                    ii++;
                }
            }else {
                Game.instance.getFont().RenderString(g, "???", 64 + i * (161 + 16), 256-48, 2, 0xffffffff);
            }
        }

        Game.instance.getFont().RenderString(g, "Press 'Z' to close the book.", 64, 512 - 48, 2, 0xffffffff);
        Game.instance.getFont().RenderString(g, "Use the mouse to select and / or inspect spells.", 64, 512 - 24, 2, 0xffffffff);
        Game.instance.getFont().RenderString(g, "Tip: Hover over spell icons in the HUD, for more info.", 64, 512, 2, 0xffffffff);
    }

    public boolean isBookOpen() {
        return bookOpen;
    }

    public int getUnlockedSpells() {
        return unlockedSpells;
    }

    public void setUnlockedSpells(int unlockedSpells) {
        this.unlockedSpells = unlockedSpells;
    }

    public void setBookOpen(boolean bookOpen) {
        this.bookOpen = bookOpen;
    }

    public void pickUp(Item item) {

    }

    public int getHealthPotionAmount() {
        return healthPotionAmount;
    }

    public void setHealthPotionAmount(int healthPotionAmount) {
        this.healthPotionAmount = healthPotionAmount;
    }

    public int getManaPotionAmount() {
        return manaPotionAmount;
    }

    public void setManaPotionAmount(int manaPotionAmount) {
        this.manaPotionAmount = manaPotionAmount;
    }

    public int getSelectedItemAmount() {
        if(this.itemX.getId() == Item.healthPotion.getId()) return this.healthPotionAmount;
        else if(this.itemX.getId() == Item.manaPotion.getId()) return this.manaPotionAmount;
        else if(this.itemX.getId() == Item.sword.getId()) return 0;
        else return -1;
    }

    public void useSelectedItem() {

        if(this.itemX != null && this.itemX.getId() == Item.healthPotion.getId()) {
            Sound.consume_mana.play();
            this.setHealth(this.getHealth() + 55);
            if(this.getHealth() > this.getMaxHealth()) this.setHealth(this.getMaxHealth());
            this.healthPotionAmount--;
            if(this.healthPotionAmount <= 0) this.itemX = null;
        }

        if(this.itemX != null && this.itemX.getId() == Item.manaPotion.getId()) {
            Sound.consume_mana.play();
            this.setPower(this.getPower() + 120);
            if(this.getPower() > 200) this.setPower(200);
            this.manaPotionAmount--;
            if(this.manaPotionAmount <= 0) this.itemX = null;
        }

        if(this.itemX != null && this.itemX.getId() == Item.sword.getId()) {  }

    }

    public void setSelectedItemAmount(int a) {
        if(this.itemX.getId() == Item.healthPotion.getId()) this.healthPotionAmount = a;
        else if(this.itemX.getId() == Item.manaPotion.getId()) this.manaPotionAmount = a;
    }

}
