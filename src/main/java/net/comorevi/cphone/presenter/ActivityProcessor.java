package net.comorevi.cphone.presenter;

import cn.nukkit.IPlayer;
import cn.nukkit.Player;
import cn.nukkit.entity.Attribute;
import cn.nukkit.network.protocol.ModalFormRequestPacket;
import cn.nukkit.network.protocol.UpdateAttributesPacket;
import cn.nukkit.scheduler.Task;
import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.application.ApplicationPermission;
import net.comorevi.cphone.cphone.data.ApplicationData;
import net.comorevi.cphone.cphone.data.RuntimeData;
import net.comorevi.cphone.cphone.data.StringsData;
import net.comorevi.cphone.cphone.model.CustomResponse;
import net.comorevi.cphone.cphone.model.Response;
import net.comorevi.cphone.cphone.sql.ApplicationSQLManager;
import net.comorevi.cphone.cphone.utils.ErrorReporter;
import net.comorevi.cphone.cphone.utils.StringLoader;
import net.comorevi.cphone.cphone.widget.activity.Activity;
import net.comorevi.cphone.cphone.widget.activity.ActivityBase;
import net.comorevi.cphone.cphone.widget.activity.ReturnType;
import net.comorevi.cphone.cphone.widget.activity.base.ListActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;

public class ActivityProcessor {

    public static void send(Player player, ActivityBase activity) {
        SharingData.activities.put(activity.getId(), activity);

        ModalFormRequestPacket packet = new ModalFormRequestPacket();
        packet.data = activity.getJson();
        packet.formId = activity.getId();

        player.dataPacket(packet);

        if (activity instanceof ListActivity) {
            SharingData.server.getScheduler().scheduleDelayedTask(new Task() {
                @Override
                public void onRun(int i) {
                    if (player.isOnline()) {
                        UpdateAttributesPacket pk = new UpdateAttributesPacket();
                        pk.entityId = player.getId();
                        pk.entries = new Attribute[]{Attribute.getAttribute(Attribute.EXPERIENCE)};
                        player.dataPacket(pk);
                    }
                }
            }, 1);
        }
    }

    public static void startActivity(Player player, String appName) {
        ApplicationManifest manifest = ApplicationData.applications.get(appName);

        try {
            Class<? extends ActivityBase> mainClass = new URLClassLoader(new URL[]{new File(RuntimeData.currentDirectory + "/app/" + manifest.getTitle() + ".jar").toURI().toURL()}, SharingData.pluginInstance.getClass().getClassLoader())
                    .loadClass(manifest.getMain())
                    .asSubclass(ActivityBase.class);

            Constructor<ActivityBase> constructor = (Constructor<ActivityBase>) mainClass.getConstructor(ApplicationManifest.class);
            ActivityBase activityBase = constructor.newInstance(manifest);

            String lang = player.getLoginChainData().getLanguageCode().toLowerCase();

            InputStream stream = activityBase.getStringFile("strings-" + lang.substring(0, 2) + ".xml");
            if (stream == null) stream = activityBase.getStringFile("strings.xml");

            activityBase.start(player, stream != null ? StringLoader.loadString(stream) : Collections.emptyMap());

        } catch (Exception ex) {
            ErrorReporter.reportError(player.getName(), manifest.getTitle(), ex);
            SharingData.phones.get(player.getName()).setHomeMessage(StringsData.strings.get("message_application_error_start") + manifest.getTitle());
            SharingData.phones.get(player.getName()).home();
        }
    }

    public static void stop(Player player, ActivityBase activity, Response response) {
        if (response instanceof CustomResponse && ((CustomResponse) response).getResult().size() == 0) {
            activity.onPause();
            SharingData.phones.get(player.getName()).setActivity(activity);
            SharingData.phones.get(player.getName()).home();
            return;
        }

        ReturnType type = activity.onStop(response);
        if (type == null) type = ReturnType.TYPE_END;

        activity.onDestroy();

        switch (type) {
            case TYPE_END:
                SharingData.activities.remove(activity.getId());
                SharingData.phones.get(player.getName()).home();
                break;

            case TYPE_IGNORE:
                SharingData.phones.get(player.getName()).setOpening(false);

            case TYPE_CONTINUE:
                SharingData.activities.remove(activity.getId());
                break;
        }
    }

}
