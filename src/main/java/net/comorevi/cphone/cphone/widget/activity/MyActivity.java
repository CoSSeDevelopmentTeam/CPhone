package net.comorevi.cphone.cphone.widget.activity;

import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.model.Bundle;
import net.comorevi.cphone.cphone.model.Response;
import net.comorevi.cphone.cphone.widget.activity.base.CustomActivity;

public class MyActivity extends CustomActivity {

    public MyActivity(ApplicationManifest manifest) {
        super(manifest);
    }

    @Override
    public void onCreate(Bundle bundle) {
        this.setTitle("TEST");
    }

    @Override
    public ReturnType onStop(Response response) {
        return ReturnType.TYPE_END;
    }

}
