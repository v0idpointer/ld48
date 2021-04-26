package me.v0idpointer.ld48.hud;

import me.v0idpointer.ld48.Game;
import me.v0idpointer.ld48.entity.Entity;
import me.v0idpointer.ld48.entity.Player;
import me.v0idpointer.ld48.gfx.SpriteSheet;

import java.awt.*;

public class HUD {

    private boolean paused = false;
    private Game gameComponent;
    private Entity target = null;
    private int timer = 0;
    private boolean scoreState = false;

    public HUD(Game gameComponent) {
        this.gameComponent = gameComponent;
    }

    public void update() {
        if(this.paused) return;

        this.timer++;
        if(this.timer >= 256) {
            this.timer = 0;
            this.scoreState = !this.scoreState;
        }
    }

    public void render(Graphics g) {
        if(((Game.instance.flags >> 7) & 1) == 1) return;
        if(this.paused) return;
        if(target == null) return;

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor( (target.getHealth() > 40) ? Color.gray : Color.red);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
        g2d.fillRect(16, 16, 288, 16);

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.95f));
        g2d.setColor( (target.getHealth() > 40) ? Color.green : Color.yellow);
        if(target instanceof Player) {
            Player player = (Player) target;
            if(player.getFreezeTimer() > 0) g2d.setColor(Color.CYAN);
            if(player.getBurningTimer() > 0) g2d.setColor(Color.RED);
        }
        g2d.fillRect(20, 20, (int)((target.getHealth() / 120.0d) * 280), 8);

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
        g2d.setColor( (target.getHealth() > 0) ? Color.gray : Color.red);
        g2d.fillRect(16, 40, 224, 16);

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.95f));
        g2d.setColor(Color.blue);
        g2d.fillRect(20, 44, (int)((this.target.getPower() / 200.0f) * 216), 8);

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
        g2d.setColor(Color.gray);

        if(this.target instanceof Player) {
            Player player = (Player)this.target;

            g2d.fillRect(320, 16, 48, 48);
            if(player.getSpell() != null) {
                g2d.drawImage(SpriteSheet.TERRAIN.getSprite(player.getSpell().getLowTx(), player.getSpell().getLowTy()), 324, 20, 40, 40, null);
                int[] m = Game.instance.getMouseInput().getMousePosition();
                if(new Rectangle(m[0] - 8, m[1] - 32, 1, 1).intersects(new Rectangle(320, 16, 48, 48))) {
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                    Game.instance.getFrame().renderWindow(g, 288, 64, 256, 128);
                    Game.instance.getFont().RenderString(g, player.getSpell().getLowName(), 320, 88, 2, 0xffffffff);
                    for(int ii = 0; ii < player.getSpell().getLowLore().length; ii++) {
                        String text = player.getSpell().getLowLore()[ii];
                        Game.instance.getFont().RenderString(g, text, 320, 114 + ii*8, 1, this.getTextColor(text));
                    }
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
                }
            }
            this.gameComponent.getFont().RenderString(g2d,"Q", 320, 12, 2, 0xffffffff);

            g2d.fillRect(384, 16, 48, 48);
            if(player.getSpell() != null) {
                g2d.drawImage(SpriteSheet.TERRAIN.getSprite(player.getSpell().getMediumTx(), player.getSpell().getMediumTy()), 388, 20, 40, 40, null);
                int[] m = Game.instance.getMouseInput().getMousePosition();
                if(new Rectangle(m[0] - 8, m[1] - 32, 1, 1).intersects(new Rectangle(384, 16, 48, 48))) {
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                    Game.instance.getFrame().renderWindow(g, 288, 64, 256, 128);
                    Game.instance.getFont().RenderString(g, player.getSpell().getMediumName(), 320, 88, 2, 0xffffffff);
                    for(int ii = 0; ii < player.getSpell().getMediumLore().length; ii++) {
                        String text = player.getSpell().getMediumLore()[ii];
                        Game.instance.getFont().RenderString(g, text, 320, 114 + ii*8, 1, this.getTextColor(text));
                    }
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
                }
            }
            this.gameComponent.getFont().RenderString(g2d,"E", 384, 12, 2, 0xffffffff);

            g2d.setColor( (player.getReleaseCharge() >= 600) ? Color.green : Color.gray );
            g2d.fillRect(448, 16, 48, 48);
            if(!(player.getReleaseCharge() >= 600)) {
                g2d.setColor(Color.yellow);
                int c = player.getReleaseCharge();
                if(c > 600) c = 600;
                g2d.fillRect(448, 16, (int)( (c / 600.0d) * 48 ), 48);
            }
            if(player.getSpell() != null) {
                g2d.drawImage(SpriteSheet.TERRAIN.getSprite(player.getSpell().getHighTx(), player.getSpell().getHighTy()), 452, 20, 40, 40, null);
                int[] m = Game.instance.getMouseInput().getMousePosition();
                if(new Rectangle(m[0] - 8, m[1] - 32, 1, 1).intersects(new Rectangle(448, 16, 48, 48))) {
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                    Game.instance.getFrame().renderWindow(g, 288, 64, 256, 128);
                    Game.instance.getFont().RenderString(g, player.getSpell().getHighName(), 320, 88, 2, 0xFF7100A5);
                    for(int ii = 0; ii < player.getSpell().getHighLore().length; ii++) {
                        String text = player.getSpell().getHighLore()[ii];
                        Game.instance.getFont().RenderString(g, text, 320, 114 + ii*8, 1, this.getTextColor(text));
                    }
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
                }
            }
            this.gameComponent.getFont().RenderString(g2d,"R", 448, 12, 2, 0xffffffff);

            g2d.setColor( (player.isBookOpen()) ? Color.green : Color.gray );
            g2d.fillRect(544, 16, 48, 48);
            if(player.getItemZ() != null) {
                g2d.drawImage(SpriteSheet.TERRAIN.getSprite(player.getItemZ().getTx(), player.getItemZ().getTy()), 548, 20, 40, 40, null);
                int[] m = Game.instance.getMouseInput().getMousePosition();
                if(new Rectangle(m[0] - 8, m[1] - 32, 1, 1).intersects(new Rectangle(544, 16, 48, 48))) {
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                    Game.instance.getFrame().renderWindow(g, 512, 64, 256, 128);
                    Game.instance.getFont().RenderString(g, player.getItemZ().getItemName(), 544, 88, 2, 0xffffffff);
                    for(int ii = 0; ii < player.getItemZ().getLore().length; ii++) {
                        String text = player.getItemZ().getLore()[ii];
                        Game.instance.getFont().RenderString(g, text, 544, 114 + ii*8, 1, this.getTextColor(text));
                    }
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
                }
            }
            this.gameComponent.getFont().RenderString(g2d,"Z", 544, 12, 2, 0xffffffff);

            g2d.setColor(Color.gray);
            g2d.fillRect(608, 16, 48, 48);
            if(player.getItemX() != null) {
                g2d.drawImage(SpriteSheet.TERRAIN.getSprite(player.getItemX().getTx(), player.getItemX().getTy()), 612, 20, 40, 40, null);
                int[] m = Game.instance.getMouseInput().getMousePosition();
                if(new Rectangle(m[0] - 8, m[1] - 32, 1, 1).intersects(new Rectangle(608, 16, 48, 48))) {
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                    Game.instance.getFrame().renderWindow(g, 512, 64, 256, 128);
                    Game.instance.getFont().RenderString(g, player.getItemX().getItemName(), 544, 88, 2, 0xffffffff);
                    for(int ii = 0; ii < player.getItemX().getLore().length; ii++) {
                        String text = player.getItemX().getLore()[ii];
                        Game.instance.getFont().RenderString(g, text, 544, 114 + ii*8, 1, this.getTextColor(text));
                    }
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
                }
                if(player.getSelectedItemAmount() > 0) this.gameComponent.getFont().RenderString(g2d,"" + player.getSelectedItemAmount(), 652, 54, 2, 0xffffffff);
            }
            this.gameComponent.getFont().RenderString(g2d,"X", 608, 12, 2, 0xffffffff);

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
            g2d.setColor(Color.gray);
            g2d.fillRect(668, 16, 100, 48);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.95f));
            int lvl = Integer.parseInt(Game.instance.getDisplayComponent().world.getName());
            int gems = player.getGems();
            if(scoreState) {
                Game.instance.getFont().RenderString(g2d, "Floor:", 672, 20, 2, 0xff000000);
                Game.instance.getFont().RenderString(g2d, "" + lvl, 672, 40, 2, 0xff000000);
            }else{
                Game.instance.getFont().RenderString(g2d, "Gems:", 672, 20, 2, 0xff000000);
                Game.instance.getFont().RenderString(g2d, "" + gems, 672, 40, 2, 0xff000000);
            }

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.00f));
            if(((Game.instance.flags >> 8) & 1) == 0 && !player.isBookOpen()) {

                if (Game.instance.getDisplayComponent().world.getName().equalsIgnoreCase("0")) {
                    if(Game.instance.getDisplayComponent().world.getEnemyCounter() > 0) {
                        Game.instance.getFont().RenderString(g, "Move using W,S,A,D", 16, 256 - 64, 2, 0xffffffff);
                        if(player.getFacing() == 1) {
                            Game.instance.getFont().RenderString(g, "To shoot a fireball,", 16, 256 - 32, 2, 0xffffffff);
                            Game.instance.getFont().RenderString(g, "press Q", 16, 256 - 8, 2, 0xffffffff);
                        }else{
                            Game.instance.getFont().RenderString(g, "Look towards the", 16, 256 - 32, 2, 0xffffffff);
                            Game.instance.getFont().RenderString(g, "green slime.", 16, 256 - 8, 2, 0xffffffff);
                        }
                    }else{
                        Game.instance.getFont().RenderString(g, "Good job! Now move", 16, 256 - 64, 2, 0xffffffff);
                        Game.instance.getFont().RenderString(g, "towards the door.", 16, 256 - 32, 2, 0xffffffff);
                    }
                }

                if (Game.instance.getDisplayComponent().world.getName().equalsIgnoreCase("-1")) {
                    if(Game.instance.getDisplayComponent().world.getEnemyCounter() > 0) {
                        Game.instance.getFont().RenderString(g, "Use spells to kill", 16, 256 - 64, 2, 0xffffffff);
                        Game.instance.getFont().RenderString(g, "slimes.", 16, 256 - 40, 2, 0xffffffff);
                        Game.instance.getFont().RenderString(g, "Cast spells with Q and E.", 16, 256, 2, 0xffffffff);
                    }else{
                        Game.instance.getFont().RenderString(g, "Good job! Now move", 16, 256 - 64, 2, 0xffffffff);
                        Game.instance.getFont().RenderString(g, "towards the door.", 16, 256 - 32, 2, 0xffffffff);
                    }
                }

                if (Game.instance.getDisplayComponent().world.getName().equalsIgnoreCase("-2")) {
                    Game.instance.getFont().RenderString(g, "Use spells to kill", 16, 256 - 64, 2, 0xffffffff);
                    Game.instance.getFont().RenderString(g, "slimes.", 16, 256 - 40, 2, 0xffffffff);
                    Game.instance.getFont().RenderString(g, "Cast spells with Q and E.", 16, 256, 2, 0xffffffff);
                }

                if (Game.instance.getDisplayComponent().world.getName().equalsIgnoreCase("-3")) {
                    if(Game.instance.getDisplayComponent().world.getEnemyCounter() > 0) {
                        Game.instance.getFont().RenderString(g, "Use spells to kill", 16, 256 - 64, 2, 0xffffffff);
                        Game.instance.getFont().RenderString(g, "the slime.", 16, 256 - 40, 2, 0xffffffff);
                        Game.instance.getFont().RenderString(g, "Cast spells with Q and E.", 16, 256, 2, 0xffffffff);
                    }else{
                        Game.instance.getFont().RenderString(g, "Don't forget to open", 16, 384 - 64, 2, 0xffffffff);
                        Game.instance.getFont().RenderString(g, "those chests.", 16, 384 - 40, 2, 0xffffffff);
                        Game.instance.getFont().RenderString(g, "Shoot the chest to", 16, 384, 2, 0xffffffff);
                        Game.instance.getFont().RenderString(g, "open it.", 16, 384+24, 2, 0xffffffff);
                        Game.instance.getFont().RenderString(g, "Chests may contain gems", 16, 384 + 64, 2, 0xffffffff);
                        Game.instance.getFont().RenderString(g, "or other valuable items.", 16, 384+86, 2, 0xffffffff);
                    }
                }

                if (Game.instance.getDisplayComponent().world.getName().equalsIgnoreCase("-4")) {
                    if(Game.instance.getDisplayComponent().world.getEnemyCounter() > 0) {
                        Game.instance.getFont().RenderString(g, "Cast spells with Q and E.", 16, 256 - 64, 2, 0xffffffff);
                        Game.instance.getFont().RenderString(g, "Once charged, you can release", 16, 256 - 40, 2, 0xffffffff);
                        Game.instance.getFont().RenderString(g, "all magic power using R", 16, 256-16, 2, 0xffffffff);
                    }else{
                        if(Game.instance.getDisplayComponent().world.getEnemyCounter() == 0) {
                            Game.instance.getFont().RenderString(g, "Use Z to open your spellbook.", 16, 384, 2, 0xffffffff);
                            Game.instance.getFont().RenderString(g, "There you can change what spells", 16, 384+24, 2, 0xffffffff);
                            Game.instance.getFont().RenderString(g, "you use.", 16, 384+48, 2, 0xffffffff);
                            Game.instance.getFont().RenderString(g, "Different spells are better", 16, 384+86, 2, 0xffffffff);
                            Game.instance.getFont().RenderString(g, "against certain enemies.", 16, 384+110, 2, 0xffffffff);
                        }else{
                            Game.instance.getFont().RenderString(g, "The deeper down you go,", 16, 384 - 64, 2, 0xffffffff);
                            Game.instance.getFont().RenderString(g, "you will find better items.", 16, 384 - 40, 2, 0xffffffff);
                        }
                    }
                }

                if (Game.instance.getDisplayComponent().world.getName().equalsIgnoreCase("-5")) {
                    Game.instance.getFont().RenderString(g, "The deeper down you go", 16, 256 - 64, 2, 0xffffffff);
                    Game.instance.getFont().RenderString(g, "you will find better items.", 16, 256 - 40, 2, 0xffffffff);
                }

            }
        }

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.00f));
    }

    public boolean isPaused() {
        return this.paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void attachTarget(Entity entity) {
        this.target = entity;
        this.paused = false;
    }

    public boolean hasTarget() {
        return this.target != null;
    }

    public int getTextColor(String text) {
        if(text.startsWith("Imbued")) return 0xFF7100A5;
        else if(text.startsWith("-")) return 0xFFBF0000;
        else if(text.startsWith("+") || text.startsWith("On use:") || text.startsWith("Hold:")) return 0xFF3651D8;
        else return 0xFFFFFFFF;
    }

    public void awardPlayerDamage(int damage) {
        if(this.target != null) {
            if(this.target instanceof Player) {
                Player player = (Player)this.target;
                player.setReleaseCharge(player.getReleaseCharge() + damage);
            }
        }
    }
}
