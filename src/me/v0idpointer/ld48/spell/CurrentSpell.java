package me.v0idpointer.ld48.spell;

import me.v0idpointer.ld48.Game;
import me.v0idpointer.ld48.entity.*;

public class CurrentSpell extends Spell {

    public CurrentSpell() {
        super(40, "Electricity", 0, 8, "This spell was banned", "by the Order of", "Royal Wizards for", "breaking many", "electronic devices", "like phones and", "consoles. Use on", "your own risk!", "", "It\'s simple: ", "High risk - high", "reward");
        this.createLowMagic("Zap", 13, 3, "Hold: Summon electrical sparks", "- Drains mana while in use", "- 300% slower mana recharge", "", "Legends say that Tesla", "himself wrote this spell.");
        this.createMediumMagic("Spark", 14, 3, "Summons an electrical spark", "that bounces off enemies.", "", "Cost: 40");
        this.createHighMagic("Stormy Storm", 15, 3, "Summons a storm cloud", "that destroys all enemies", "inside.", "", "Cost: 150");
    }

    @Override
    public void callLow(Player player) {
        if(player.getPower() >= 1) {
            player.setPower(player.getPower() - 1);
            player.setCastCooldown(0);

            int ox = 0, oy = 0;
            if(player.getFacing() == 0) oy = 32;
            else if(player.getFacing() == 1) oy = -32;
            else if(player.getFacing() == 2) ox = 16;
            else if(player.getFacing() == 3) ox = -16;

            Game.instance.getDisplayComponent().world.getEntities().add(new Zap(player.getX() + ox, player.getY() + oy));
        }
    }

    @Override
    public void callMedium(Player player) {
        if(player.getPower() >= 40) {
            player.setPower(player.getPower() - 40);
            Game.instance.getDisplayComponent().world.getEntities().add(new PowerBeam(player.getX(), player.getY()));
        }
    }

    @Override
    public void callHigh(Player player) {
        if(player.getPower() >= 150) {
            player.setPower(player.getPower() - 150);
            Game.instance.getDisplayComponent().world.getEntities().add(new StormyStorm(player.getX(), player.getY(), player));
            player.setReleaseCharge(player.getReleaseCharge() - 600);
        }
    }
}
