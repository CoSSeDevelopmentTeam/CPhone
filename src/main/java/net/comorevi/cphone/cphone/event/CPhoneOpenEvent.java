package net.comorevi.cphone.cphone.event;

import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.player.PlayerEvent;
import net.comorevi.cphone.cphone.CPhone;

public class CPhoneOpenEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private CPhone cPhone;

    public CPhoneOpenEvent(CPhone cPhone) {
        this.cPhone = cPhone;
        this.player = cPhone.getPlayer();
        this.eventName = "CPhoneOpenEvent";
    }

    public CPhone getCPhone() {
        return cPhone;
    }

    public static HandlerList getHandlers() {
        return handlers;
    }

}
