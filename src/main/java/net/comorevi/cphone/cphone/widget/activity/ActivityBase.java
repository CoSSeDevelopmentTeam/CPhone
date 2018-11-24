package net.comorevi.cphone.cphone.widget.activity;

import cn.nukkit.Player;
import net.comorevi.cphone.cphone.application.ApplicationBase;
import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.model.Bundle;
import net.comorevi.cphone.cphone.model.Response;
import net.comorevi.cphone.presenter.ActivityProcessor;

import java.io.Serializable;

public abstract class ActivityBase extends ApplicationBase implements Activity, Buildable, Serializable {

    public ActivityBase(ApplicationManifest manifest) {
        super(manifest);
    }

    public abstract void onCreate(Bundle bundle);

    public abstract ReturnType onStop(Response response);

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onPause() {

    }

    public void start(Player player) {
        send(player);
        this.onStart();
    }

    private void send(Player player) {
        ActivityProcessor.send(player, this);
    }

    @Deprecated (since = "1.0")
    public String serialize() {
        return null;
    }

}
