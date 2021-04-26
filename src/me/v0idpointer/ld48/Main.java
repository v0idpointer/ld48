package me.v0idpointer.ld48;

public class Main {

    public Main(int flags) {
        new Game(flags);
    }

    public static void main(String[] args) {
        int flags = 0;

        for(String arg : args) {
            if(arg.equalsIgnoreCase("/i")) flags |= 1;
            if(arg.equalsIgnoreCase("/noai")) flags |= 2;
            if(arg.equalsIgnoreCase("/c")) flags |= 4;
            if(arg.equalsIgnoreCase("/v")) flags |= 8;
            if(arg.equalsIgnoreCase("/mx")) flags |= 16;
            if(arg.equalsIgnoreCase("/hb")) flags |= 32;
            if(arg.equalsIgnoreCase("/nd")) flags |= 64;
            if(arg.equalsIgnoreCase("/nohud")) flags |= 128;
            if(arg.equalsIgnoreCase("/ngs")) flags |= 256;
            if(arg.equalsIgnoreCase("/as")) flags |= 512;
        }

        new Main(flags);
    }

}
