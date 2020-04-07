package net.comorevi.cphone.cphone.event;

import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;
import net.comorevi.cphone.cphone.CPhone;

public class CPhoneOpenEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private CPhone cPhone;

    public CPhoneOpenEvent(CPhone cPhone) {
        this.cPhone = cPhone;
        this.eventName = "CPhoneOpenEvent";
    }

    public CPhone getCPhone() {
        return cPhone;
    }

    public static HandlerList getHandlers() {
        return handlers;
    }

}
