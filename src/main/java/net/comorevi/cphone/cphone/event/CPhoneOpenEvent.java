package net.comorevi.cphone.cphone.event;

import cn.nukkit.event.Event;
import net.comorevi.cphone.cphone.CPhone;

public class CPhoneOpenEvent extends Event {

    private CPhone cPhone;

    public CPhoneOpenEvent(CPhone cPhone) {
        this.cPhone = cPhone;
        this.eventName = "CPhoneOpenEvent";
    }

    public CPhone getCPhone() {
        return cPhone;
    }

}
