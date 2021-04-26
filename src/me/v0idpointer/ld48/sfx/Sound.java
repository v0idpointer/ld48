package me.v0idpointer.ld48.sfx;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {

    public static final Sound select = new Sound("select.wav");
    public static final Sound player_damage = new Sound("player_damage.wav");
    public static final Sound mob_damage = new Sound("mob_damage.wav");
    public static final Sound fireball_explode = new Sound("fireball_explode.wav");
    public static final Sound beam_shoot = new Sound("beam_shoot.wav");
    public static final Sound snow_explode = new Sound("snow_explode.wav");
    public static final Sound flashbang = new Sound("flashbang.wav");
    public static final Sound coin = new Sound("coin.wav");
    public static final Sound spell_learn = new Sound("spell_learn.wav");
    public static final Sound consume_mana = new Sound("consume_mana.wav");
    public static final Sound witch_summon = new Sound("witch_summon.wav");
    public static final Sound hovering_inferno_decay = new Sound("hovering_inferno_decay.wav");
    public static final Sound storm = new Sound("storm.wav");

    private AudioClip clip;

    public Sound(String string) {
        try {
            this.clip = Applet.newAudioClip(this.getClass().getResource("/sounds/" + string));
        }catch(Exception ex) {
            ex.printStackTrace();
            System.out.println("Sound initialization error: " + ex.getLocalizedMessage());
        }
    }

    public void play() {
        try {
            this.clip.play();
        }catch(Exception ex) {
            ex.printStackTrace();
            System.out.println("Sound runtime error: " + ex.getLocalizedMessage());
        }
    }

}
