package me.v0idpointer.ld48.world;

import me.v0idpointer.ld48.entity.*;

import java.util.Random;

public class WorldGeneration {

    private static String[] levelBiomes = {"Normal", "Ice", "Hell", "Magical", "Dark"};

    public static World generate(int floor, Player player) {
        if(floor == 0) return generateWorld1();
        if(floor == -1) return generateWorld2(player);
        if(floor == -2) return generateWorld3(player);
        if(floor == -3) return generateWorld4(player);
        if(floor == -4) return generateWorld5(player);
        if(floor == -5) return generateWorld6(player);
        if(floor == -10) return generateWorld7(player);
        if(floor == -25) return generateWorld8(player);

        World world = new World("" + floor);

        Random random = new Random();
        int theme = random.nextInt(levelBiomes.length);
        for(int i = 0; i < world.getTiles().length; i++)
            world.getTiles()[i] = theme + 1;

        world.setDx(random.nextInt(512));

        int ec = random.nextInt(3 + ((floor < -10) ? 3 : 0)) + 1;
        for(int i = 0; i < ec; i++) {
            Entity slime;
            int ex, ey;
            ex = 64 + random.nextInt(608);
            ey = 128 + random.nextInt(408);
            switch(theme) {
                case 0: default:
                    slime = new Slime(ex,ey, "slime");
                    break;

                case 1:
                    slime = new FrostbiteSlime(ex,ey, "frostbite_slime");
                    break;

                case 2:
                    slime = new FireSlime(ex, ey, "fire_slime");
                    break;

                case 3:
                    slime = new MagicSlime(ex, ey, "magic_slime");
                    break;

                case 4:
                    slime = new PhantomSlime(ex, ey, "dark_slime");
                    break;
            }
            world.getEntities().add(slime);
        }
        world.setEnemyCounter(ec);

        int cc = 2;
        if(random.nextInt(10) < 2) {
            int cx, cy;
            cx = 64 + random.nextInt(608);
            cy = 128 + random.nextInt(280);
            world.getEntities().add(new StorageChest(cx, cy, (random.nextBoolean() ? 1 : 6), 1 + random.nextInt(2)));
            cc = (random.nextInt(10) == 3) ? 1 : 0 ;
        }

        if(floor < -15) cc += 1;
        if(floor < -25) cc += 1;
        for(int i = 0; i < cc; i++) {
            int gc = random.nextInt(6) + 1;
            int cx, cy;
            cx = 64 + random.nextInt(608);
            cy = 128 + random.nextInt(280);
            world.getEntities().add(new Chest(cx, cy, gc));
        }

        if(floor < -12) {
            int cx, cy;
            cx = 64 + random.nextInt(608);
            cy = 128 + random.nextInt(280);
            Witch witch = new Witch(cx, cy);
            if(floor < -31) {
                witch.setMaxHealth(witch.getMaxHealth() + 10);
                witch.setHealth(witch.getMaxHealth());
            }
            world.getEntities().add(witch);
            world.setEnemyCounter(world.getEnemyCounter() + 1);
        }

        if(player != null) {
            player.setPosition(412, 620);
            if(player.getPower() < 70) player.setPower(70);
            else player.setPower(player.getPower() + 40);
            if(player.getPower() > 200) player.setPower(200);
            world.getEntities().add(player);
        }
        else world.getEntities().add(new Player(412, 620));

        return world;
    }

    private static World generateWorld1() {
        World world = new World("0");

        for(int i = 0; i < world.getTiles().length; i++)
            world.getTiles()[i] = 1;

        world.setDx(0);

        Slime slime = new Slime(412, 180, "slime");
        slime.setNoAI();
        world.getEntities().add(slime);
        world.setEnemyCounter(1);

        world.getEntities().add(new Player(412, 620));
        return world;
    }

    private static World generateWorld2(Player player) {
        World world = new World("-1");

        for(int i = 0; i < world.getTiles().length; i++)
            world.getTiles()[i] = 1;

        world.setDx(128);

        Random random = new Random();
        for(int i = 0; i < 3; i++) {
            int ex, ey;
            ex = 64 + random.nextInt(608);
            ey = 128 + random.nextInt(188);
            Slime s = new Slime(ex, ey, "slime");
            s.setNoAI();
            world.getEntities().add(s);
        }
        world.setEnemyCounter(3);

        world.getEntities().add(player);
        player.setPosition(412, 620);
        return world;
    }

    private static World generateWorld3(Player player) {
        World world = new World("-2");

        for(int i = 0; i < world.getTiles().length; i++)
            world.getTiles()[i] = 1;

        world.setDx(64);

        Random random = new Random();
        for(int i = 0; i < 3; i++) {
            int ex, ey;
            ex = 64 + random.nextInt(608);
            ey = 128 + random.nextInt(260);
            Slime s = new Slime(ex, ey, "slime");
            world.getEntities().add(s);
        }
        world.setEnemyCounter(3);

        world.getEntities().add(player);
        player.setPosition(412, 620);
        return world;
    }

    private static World generateWorld4(Player player) {
        World world = new World("-3");

        for(int i = 0; i < world.getTiles().length; i++)
            world.getTiles()[i] = 1;

        world.setDx(96);

        Random random = new Random();
        int ex, ey;
        ex = 64 + random.nextInt(608);
        ey = 128 + random.nextInt(260);
        Slime s = new Slime(ex, ey, "slime");
        world.getEntities().add(s);
        world.setEnemyCounter(1);

        world.getEntities().add(new Chest(128, 180, 8));
        world.getEntities().add(new Chest(256+128, 180, 8));

        world.getEntities().add(player);
        player.setPosition(412, 620);
        return world;
    }

    private static World generateWorld5(Player player) {
        World world = new World("-4");

        for(int i = 0; i < world.getTiles().length; i++)
            world.getTiles()[i] = 1;

        world.setDx(96);

        Random random = new Random();
        int ex, ey;
        ex = 64 + random.nextInt(608);
        ey = 128 + random.nextInt(260);
        Slime s = new Slime(ex, ey, "slime");
        world.getEntities().add(s);
        world.setEnemyCounter(2);

        world.getEntities().add(new LibraryChest(128, 180+128, SpellbookPage.ICE_PAGE));
        world.getEntities().add(new Chest(256+128, 180, 8));

        world.getEntities().add(player);
        player.setPosition(412, 620);
        return world;
    }

    private static World generateWorld6(Player player) {
        World world = new World("-5");

        for(int i = 0; i < world.getTiles().length; i++)
            world.getTiles()[i] = 1;

        world.setDx(0);

        Random random = new Random();
        for(int i = 0; i < 3; i ++) {
            int ex, ey;
            ex = 64 + random.nextInt(608);
            ey = 128 + random.nextInt(260);
            Slime s = new Slime(ex, ey, "slime");
            world.getEntities().add(s);
        }
        world.setEnemyCounter(3);

        world.getEntities().add(player);
        player.setPosition(412, 620);
        return world;
    }

    private static World generateWorld7(Player player) {
        World world = new World("-10");

        for(int i = 0; i < world.getTiles().length; i++)
            world.getTiles()[i] = 4;

        world.setDx(256);

        Random random = new Random();
        for(int i = 0; i < 3; i ++) {
            int ex, ey;
            ex = 64 + random.nextInt(608);
            ey = 128 + random.nextInt(260);
            PhantomSlime s = new PhantomSlime(ex, ey, "phantom_slime");
            world.getEntities().add(s);
        }
        world.setEnemyCounter(4);

        world.getEntities().add(new LibraryChest(128 + 64, 180, SpellbookPage.LIGHT_PAGE));

        world.getEntities().add(player);
        player.setPosition(412, 620);
        return world;
    }

    private static World generateWorld8(Player player) {
        World world = new World("-25");

        for(int i = 0; i < world.getTiles().length; i++)
            world.getTiles()[i] = 3;

        world.setDx(256);

        Random random = new Random();
        for(int i = 0; i < 3; i ++) {
            int ex, ey;
            ex = 64 + random.nextInt(608);
            ey = 128 + random.nextInt(260);
            FrostbiteSlime s = new FrostbiteSlime(ex, ey, "frostbite_slime");
            world.getEntities().add(s);
        }
        world.setEnemyCounter(3);

        world.getEntities().add(new LibraryChest(128 + 64, 180, SpellbookPage.ELECTRICAL_PAGE));

        world.getEntities().add(player);
        player.setPosition(412, 620);
        return world;
    }

    public static World generateWorldPanorama() {
        World world = new World("69");

        for(int i = 0; i < world.getTiles().length; i++)
            world.getTiles()[i] = 1;

        world.setDx(0);

        Slime slime = new Slime(412, 180, "slime");
        slime.setNoAI();
        world.getEntities().add(slime);
        world.setEnemyCounter(1);

        return world;
    }

}
