package me.v0idpointer.ld48.spell;

import me.v0idpointer.ld48.Game;
import me.v0idpointer.ld48.entity.Entity;
import me.v0idpointer.ld48.entity.Ice;
import me.v0idpointer.ld48.entity.Player;
import me.v0idpointer.ld48.entity.Snowstorm;
import me.v0idpointer.ld48.partice.SmokeParticle;
import me.v0idpointer.ld48.sfx.Sound;

public class IceSpell extends Spell {

    public IceSpell() {
        super(20, "Ice", 0, 4, "Originally created by", "the legendary wizards", "who got bored of", "throwing fireballs all", "day long.", "", "Ice magic is perfect", "for slowing down", "enemies and", "extinguishing forest", "fires.", "", "When things get hot,", "stay frosty!");
        this.createLowMagic("Snow Shuriken", 13, 1, "Throw a shuriken made", "out of snow and ice,", "that deeply penetrates", "enemies", "", "", "Cost: 15 mana");
        this.createMediumMagic("Deep Freeze", 14, 1, "Freezes nearby enemies", "lowering their health", "up to -70%", "", "Cost: 65 mana");
        this.createHighMagic("Snowstorm", 15, 1, "Summons a snowstorm that", "deals massive damage", "to nearby enemies.", "Damage dealt is returned", "as health.", "Cost 125 mana");
    }

    @Override
    public void callLow(Player player) {
        if(player.getPower() >= 15) {
            player.setPower(player.getPower() - 15);

            int dirX = 0, dirY = 0;
            if(player.getFacing() == 0) dirY = 2;
            else if(player.getFacing() == 1) dirY = -2;
            else if(player.getFacing() == 2) dirX = 2;
            else if(player.getFacing() == 3) dirX = -2;

            Game.instance.getDisplayComponent().world.getEntities().add(new Ice(player.getX(), player.getY(), dirX, dirY));
        }
    }

    @Override
    public void callMedium(Player player) {
        if(player.getPower() >= 65) {
            player.setPower(player.getPower() - 65);
            for(Entity entity : Game.instance.getDisplayComponent().world.getEntities()) {
                if(entity.hasAi()) {
                    int ex = entity.getX();
                    int ey = entity.getY();
                    int px = player.getX();
                    int py = player.getY();

                    double d = Math.sqrt( (ex - px)*(ex - px) + (ey - py)*(ey-py) );
                    if(d < 256) {
                        entity.setHealth((int)(entity.getHealth() * 0.3f));
                        Game.instance.getDisplayComponent().world.getParticles().add(new SmokeParticle(ex + 24, ey + 24, 24, 0xFFD5FFFF, 5, 16, true, true));
                        Sound.snow_explode.play();
                    }
                }
            }
        }
    }

    @Override
    public void callHigh(Player player) {
        if(player.getPower() >= 125) {
            player.setPower(player.getPower() - 125);
            Game.instance.getDisplayComponent().world.getEntities().add(new Snowstorm(player.getX(), player.getY(), player));
            player.setReleaseCharge(player.getReleaseCharge() - 600);
        }
    }
}
