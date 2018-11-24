package net.comorevi.cphone.cphone.model;

import cn.nukkit.Player;

public class ModalResponse implements Response {

    private final Player player;
    private final int index;

    public ModalResponse(Player player, int index) {
        this.player = player;
        this.index = index;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isButton1Clicked() {
        return index == 1;
    }

    public boolean isButton2Clicked() {
        return index == 1;
    }

}
