package net.comorevi.cphone.cphone.widget.activity;

import net.comorevi.cphone.cphone.model.Bundle;
import net.comorevi.cphone.cphone.model.Response;

import java.io.Serializable;

public abstract class ActivityBase implements Activity, Serializable {

    public abstract String serialize();

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

}
