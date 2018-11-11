package net.comorevi.cphone.cphone.widget.activity;

import net.comorevi.cphone.cphone.model.Bundle;
import net.comorevi.cphone.cphone.model.Response;

public interface Activity {

    void onCreate(Bundle bundle);

    void onStart();

    void onPause();

    ReturnType onStop(Response response);

    void onDestroy();

    void onRestart();

}