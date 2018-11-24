package net.comorevi.cphone.cphone.model;

import cn.nukkit.Player;

import java.util.List;

public class CustomResponse implements Response {

    private final Player player;
    private final List<Object> result;

    public CustomResponse(Player player, List<Object> result) {
        this.player = player;
        this.result = result;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Object> getResult() {
        return result;
    }

}
