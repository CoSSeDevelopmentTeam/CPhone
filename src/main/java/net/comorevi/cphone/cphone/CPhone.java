package net.comorevi.cphone.cphone;

import cn.nukkit.Player;
import net.comorevi.cphone.cphone.utils.ManifestLoader;
import net.comorevi.cphone.cphone.widget.activity.Activity;

public final class CPhone {

    private Player player;
    private Activity activity;

    public CPhone(Player player) {
        this.player = player;
        this.activity = new HomeActivity(ManifestLoader.loadManifest(this.getClass().getClassLoader().getResourceAsStream("CPhoneManifest.xml")), player);
    }

    public void home() {
        activity.onCreate(null);
        activity.start(player);
    }


}
