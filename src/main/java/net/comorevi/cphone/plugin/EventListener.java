package net.comorevi.cphone.plugin;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.ModalFormResponsePacket;

class EventListener implements Listener {

    public EventListener() {

    }

    @EventHandler
    public void onDataPacket(DataPacketReceiveEvent event) {
        if (event.getPacket() instanceof ModalFormResponsePacket) {
            ModalFormResponsePacket packet = (ModalFormResponsePacket) event.getPacket();

        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

    }

}
