package net.comorevi.cphone.cphone.model;

import cn.nukkit.Player;

import java.io.File;
import java.io.Serializable;

public final class Bundle implements Serializable {

    private Player player;
    private long time;
    private File currentDir;

    public Bundle(Player player, long time, File currentDir) {
        this.player = player;
        this.time = time;
        this.currentDir = currentDir;
    }

    public Player getPlayer() {
        return player;
    }

    public long getTime() {
        return time;
    }

    public File getCurrentDir() {
        return currentDir;
    }

}
