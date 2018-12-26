package net.comorevi.cphone.cphone;

import cn.nukkit.Player;
import net.comorevi.cphone.cphone.data.RuntimeData;
import net.comorevi.cphone.cphone.data.StringsData;
import net.comorevi.cphone.cphone.utils.ManifestLoader;
import net.comorevi.cphone.cphone.widget.activity.Activity;

import java.util.Calendar;

public final class CPhone {

    private Player player;
    private Activity activity;
    private boolean isOpening;
    private String homeMessage;

    public CPhone(Player player) {
        this.player = player;

        String homeText = RuntimeData.config.getString("HomeText");
        setHomeMessage(homeText == null ? StringsData.strings.get("message_home_nonotification") : homeText);
    }

    public void home() {
        isOpening = true;

        Calendar cTime = Calendar.getInstance();
        int year = cTime.get(Calendar.YEAR);
        int month = cTime.get(Calendar.MONTH) + 1;
        int date = cTime.get(Calendar.DATE);
        int hour = cTime.get(Calendar.HOUR_OF_DAY);
        int minute = cTime.get(Calendar.MINUTE);

        HomeActivity activity = new HomeActivity(ManifestLoader.loadManifest(this.getClass().getClassLoader().getResourceAsStream("CPhoneManifest.xml")));
        activity.setContent(year + "/" + month + "/" + date + " " + hour + ":" + minute + "\n" + StringsData.strings.get("home_notification") + ": " + homeMessage);
        activity.start(player, null);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public boolean back() {
        if (activity == null) {
            setHomeMessage(StringsData.strings.get("message_home_noback"));
            home();
            return false;

        } else {
            activity.onRestart();
            activity.start(player, activity.getStrings());
            return true;
        }
    }

    public boolean isOpening() {
        return isOpening;
    }

    public void setOpening(boolean opening) {
        isOpening = opening;
    }

    public String getHomeMessage() {
        return homeMessage;
    }

    public void setHomeMessage(String homeMessage) {
        this.homeMessage = homeMessage;
    }

    public Player getPlayer() {
        return player;
    }
}
