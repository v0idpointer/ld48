package me.v0idpointer.ld48.spell;

import me.v0idpointer.ld48.Game;
import me.v0idpointer.ld48.entity.Entity;
import me.v0idpointer.ld48.entity.LightBeam;
import me.v0idpointer.ld48.entity.Player;
import me.v0idpointer.ld48.sfx.Sound;

public class LightSpell extends Spell {

    public LightSpell() {
        super(30, "Light", 0, 8, "\"The brightest spell", "in the book\" - said", "famous wizard Okrabus", "", "Light beams are", "powerful as they can", "penetrate multiple", "enemies.", "", "You get some, you", "lose some...");
        this.createLowMagic("Light Beam", 13, 2, "Fires a directed beam of", "light that can damage", "multiple enemies.", "", "Cost: 15 mana");
        this.createMediumMagic("Sun Beams", 14, 2, "Fires multiple beams of", "light in all 8 directions", "", "Cost: 65 mana");
        this.createHighMagic("Flash", 15, 2, "Flashes everyone in the room", "including you. Deals massive", "damage.", "", "Cost: 80 mana");
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

            Game.instance.getDisplayComponent().world.getEntities().add(new LightBeam(player.getX(), player.getY(), dirX, dirY));
            Sound.beam_shoot.play();
        }
    }

    @Override
    public void callMedium(Player player) {
        if(player.getPower() >= 65) {
            player.setPower(player.getPower() - 65);

            Game.instance.getDisplayComponent().world.getEntities().add(new LightBeam(player.getX(), player.getY(), 0, -1));
            Game.instance.getDisplayComponent().world.getEntities().add(new LightBeam(player.getX(), player.getY(), 1, -1));
            Game.instance.getDisplayComponent().world.getEntities().add(new LightBeam(player.getX(), player.getY(), 1, 0));
            Game.instance.getDisplayComponent().world.getEntities().add(new LightBeam(player.getX(), player.getY(), 1, 1));
            Game.instance.getDisplayComponent().world.getEntities().add(new LightBeam(player.getX(), player.getY(), 0, 1));
            Game.instance.getDisplayComponent().world.getEntities().add(new LightBeam(player.getX(), player.getY(), -1, 1));
            Game.instance.getDisplayComponent().world.getEntities().add(new LightBeam(player.getX(), player.getY(), -1, 0));
            Game.instance.getDisplayComponent().world.getEntities().add(new LightBeam(player.getX(), player.getY(), -1, -1));

            Sound.beam_shoot.play();
        }
    }

    @Override
    public void callHigh(Player player) {
        if(player.getPower() >= 80) {
            player.setPower(player.getPower() - 80);
            Game.instance.getDisplayComponent().setFlashTimer(32);

            for(Entity entity : Game.instance.getDisplayComponent().world.getEntities()) {
                if (entity.hasAi()) {
                    entity.setHealth((int)(entity.getHealth() * 0.55f));
                }
            }

            player.setReleaseCharge(player.getReleaseCharge() - 600);
            Sound.flashbang.play();
        }
    }
}
