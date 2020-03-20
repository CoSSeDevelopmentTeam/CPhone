package net.comorevi.cphone.cphone.widget.activity.original;

import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.model.Bundle;
import net.comorevi.cphone.cphone.model.ModalResponse;
import net.comorevi.cphone.cphone.model.Response;
import net.comorevi.cphone.cphone.widget.activity.ActivityBase;
import net.comorevi.cphone.cphone.widget.activity.ReturnType;
import net.comorevi.cphone.cphone.widget.activity.base.ModalActivity;

public class MessageActivity extends ModalActivity {

    private String text;
    private String button1;
    private String button2;
    private ActivityBase[] starts;
    private Bundle bundle;

    @Override
    public void onCreate(Bundle bundle) {
        this.bundle = bundle;

        this.setContent(text);
        this.setButton1Text(button1);
        this.setButton2Text(button2);
    }

    @Override
    public ReturnType onStop(Response response) {
        ModalResponse modalResponse = (ModalResponse) response;

        if (starts.length == 0) return ReturnType.TYPE_END;
        else {
            if (modalResponse.isButton1Clicked()) {
                starts[0].start(bundle);
                return ReturnType.TYPE_CONTINUE;
            }
            if (modalResponse.isButton2Clicked() && starts.length > 1) {
                starts[1].start(bundle);
                return ReturnType.TYPE_CONTINUE;
            }
        }

        return ReturnType.TYPE_END;
    }

    public MessageActivity(ApplicationManifest manifest, String text, String button1, String button2, ActivityBase... starts) {
        super(manifest);
        this.text = text;
        this.button1 = button1;
        this.button2 = button2;
        this.starts = starts;
    }

}
