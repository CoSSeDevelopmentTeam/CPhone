package net.comorevi.cphone.presenter;

import cn.nukkit.Player;
import cn.nukkit.network.protocol.ModalFormRequestPacket;
import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.data.ApplicationData;
import net.comorevi.cphone.cphone.data.RuntimeData;
import net.comorevi.cphone.cphone.data.StringsData;
import net.comorevi.cphone.cphone.model.CustomResponse;
import net.comorevi.cphone.cphone.model.Response;
import net.comorevi.cphone.cphone.utils.StringLoader;
import net.comorevi.cphone.cphone.widget.activity.Activity;
import net.comorevi.cphone.cphone.widget.activity.ActivityBase;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ActivityProcessor {

    public static Activity send(Player player, ActivityBase activity) {
        SharingData.activities.put(activity.getId(), activity);

        ModalFormRequestPacket packet = new ModalFormRequestPacket();
        packet.data = activity.getJson();
        packet.formId = activity.getId();

        player.dataPacket(packet);

        return activity;
    }

    public static void startActivity(Player player, String appName) {
        ApplicationManifest manifest = ApplicationData.applications.get(appName);

        try {
            Class<? extends ActivityBase> mainClass = new URLClassLoader(new URL[]{new File(RuntimeData.currentDirectory + "/app/" + manifest.getTitle() + ".jar").toURI().toURL()}, SharingData.pluginInstance.getClass().getClassLoader())
                    .loadClass(manifest.getMain())
                    .asSubclass(ActivityBase.class);

            Constructor<ActivityBase> constructor = (Constructor<ActivityBase>) mainClass.getConstructor(ApplicationManifest.class);
            ActivityBase activityBase = constructor.newInstance(manifest);
            InputStream stream;

            switch (player.getLoginChainData().getLanguageCode().toLowerCase()) {
                case "ja-jp":
                    stream = activityBase.getStringFile("strings-ja.xml");
                    break;

                case "en-us":
                    stream = activityBase.getStringFile("strings-en.xml");
                    break;

                default:
                    stream = activityBase.getStringFile("strings.xml");
                    break;
            }

            activityBase.start(player, stream != null ? StringLoader.loadString(stream) : null);

        } catch (MalformedURLException | ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException ex) {
            ex.printStackTrace();
            SharingData.phones.get(player.getName()).setHomeMessage(StringsData.strings.get("message_application_error_start") + manifest.getTitle());
            SharingData.phones.get(player.getName()).back();
        }
    }

    public static void stop(Player player, ActivityBase activity, Response response) {
        if (response instanceof CustomResponse && ((CustomResponse) response).getResult().size() == 0) {
            SharingData.phones.get(player.getName()).setActivity(activity);
            return;
        }

        switch (activity.onStop(response)) {
            case TYPE_END:
                activity.onDestroy();
                SharingData.activities.remove(activity.getId());
                SharingData.phones.get(player.getName()).home();
                break;

            case TYPE_CONTINUE:
                activity.onDestroy();
                SharingData.activities.remove(activity.getId());
                break;
        }
    }
}
