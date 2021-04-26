package me.v0idpointer.ld48.spell;

import me.v0idpointer.ld48.Game;
import me.v0idpointer.ld48.entity.Fireball;
import me.v0idpointer.ld48.entity.GuidedFireball;
import me.v0idpointer.ld48.entity.HoveringInferno;
import me.v0idpointer.ld48.entity.Player;

public class FireSpell extends Spell {

    public FireSpell() {
        super(10, "Fire", 0, 4, "Every wizards' first", "spell. Good enough", "for every situation.", "", "Rated 7 / 10 by the", "Order of Royal Wizards", "", "Obligatory warning:", "Summoning Hovering", "Inferno may result", "death or molten arms.", "Remember the emergency", "hotline: 0775418");
        this.createLowMagic("Fireball", 13, 0, "Sometimes, the simplest", "spell may be the", "most effective", "", "", "Cost: 10 mana");
        this.createMediumMagic("Barrage", 14, 0, "Deals massive damage", "near the player", "", "", "Cost: 45 mana");
        this.createHighMagic("Hovering Inferno", 15, 0, "Summons a sphere that attacks" , "the enemies, dealing massive", "damage over time." , "", "", "Cost: 100 mana");
    }

    @Override
    public void callLow(Player player) {
        if(player.getPower() >= 10) {
            player.setPower(player.getPower() - 10);

            int dirX = 0, dirY = 0;
            if(player.getFacing() == 0) dirY = 2;
            else if(player.getFacing() == 1) dirY = -2;
            else if(player.getFacing() == 2) dirX = 2;
            else if(player.getFacing() == 3) dirX = -2;

            Game.instance.getDisplayComponent().world.getEntities().add(new Fireball(player.getX(), player.getY(), dirX, dirY));
        }
    }

    @Override
    public void callMedium(Player player) {
        if(player.getPower() >= 45) {
            player.setPower(player.getPower() - 45);

            Game.instance.getDisplayComponent().world.getEntities().add(new GuidedFireball(player.getX() - 48, player.getY() + 24, 0));
            Game.instance.getDisplayComponent().world.getEntities().add(new GuidedFireball(player.getX() + 48, player.getY() + 24, 64));
            Game.instance.getDisplayComponent().world.getEntities().add(new GuidedFireball(player.getX() - 24, player.getY() + 48, 128));
            Game.instance.getDisplayComponent().world.getEntities().add(new GuidedFireball(player.getX(), player.getY() + 24, 192));
            Game.instance.getDisplayComponent().world.getEntities().add(new GuidedFireball(player.getX() + 24, player.getY() + 16, 256));
        }
    }

    @Override
    public void callHigh(Player player) {
        if(player.getPower() >= 100) {
            player.setPower(player.getPower() - 100);
            player.setReleaseCharge(player.getReleaseCharge() - 600);
            Game.instance.getDisplayComponent().world.getEntities().add(new HoveringInferno(player.getX(), player.getY() - 64));
        }
    }
}
