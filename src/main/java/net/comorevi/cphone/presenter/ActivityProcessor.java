package net.comorevi.cphone.presenter;

import cn.nukkit.Player;
import cn.nukkit.network.protocol.ModalFormRequestPacket;
import net.comorevi.cphone.cphone.model.Response;
import net.comorevi.cphone.cphone.widget.activity.Activity;
import net.comorevi.cphone.cphone.widget.activity.ActivityBase;
import net.comorevi.cphone.cphone.widget.activity.ReturnType;

public class ActivityProcessor {

    public static Activity send(Player player, ActivityBase activity) {
        SharingData.activities.put(activity.getId(), activity);

        ModalFormRequestPacket packet = new ModalFormRequestPacket();
        packet.data = activity.getJson();
        packet.formId = activity.getId();

        player.dataPacket(packet);

        return activity;
    }

    public static void stop(Player player, ActivityBase activity, Response response) {
        switch (activity.onStop(response)) {
            case TYPE_END:
                SharingData.phones.get(player.getName()).home();
                break;

            case TYPE_CONTINUE:
                activity.onDestroy();
                SharingData.activities.remove(activity.getId());
                break;
        }
    }
}
