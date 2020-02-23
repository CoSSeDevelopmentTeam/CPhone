package net.comorevi.cphone.cphone.model;

import cn.nukkit.Player;

public class ListResponse implements Response {

    private final Player player;
    private final int buttonIndex;

    public static final int NOT_SELECTED = -1;

    public ListResponse(Player player, int buttonIndex) {
        this.player = player;
        this.buttonIndex = buttonIndex;
    }

    public Player getPlayer() {
        return player;
    }

    public int getButtonIndex() {
        return buttonIndex;
    }

}
