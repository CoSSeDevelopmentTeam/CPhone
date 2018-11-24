package net.comorevi.cphone.cphone;

import cn.nukkit.Player;
import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.data.StringsData;
import net.comorevi.cphone.cphone.model.Bundle;
import net.comorevi.cphone.cphone.model.ListResponse;
import net.comorevi.cphone.cphone.model.Response;
import net.comorevi.cphone.cphone.widget.activity.ReturnType;
import net.comorevi.cphone.cphone.widget.activity.base.ListActivity;
import net.comorevi.cphone.cphone.widget.element.Button;

import static net.comorevi.cphone.cphone.data.StringsData.strings;

public class HomeActivity extends ListActivity {

    private Player player;

    public HomeActivity(ApplicationManifest manifest, Player player) {
        super(manifest);
        this.player = player;
    }

    @Override
    public void onCreate(Bundle bundle) {
        this.setTitle(strings.get("cphone_title"));
        init();
    }

    @Override
    public ReturnType onStop(Response response) {
        ListResponse listResponse = (ListResponse) response;
        switch (listResponse.getButtonIndex()) {
            case -1:
            case 0:
                ((ListResponse) response).getPlayer().sendMessage(StringsData.strings.get("message_home_closed"));
                break;

            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
        return ReturnType.TYPE_CONTINUE;
    }

    private void init() {
        Button closeButton = new Button(strings.get("home_close")) {
            @Override
            public void onClick(Player player) {

            }
        };
        this.addButton(closeButton);

        Button restartButton = new Button(strings.get("home_restartApp")) {
            @Override
            public void onClick(Player player) {

            }
        };
        this.addButton(restartButton);

        Button appsButton = new Button(strings.get("home_applications")) {
            @Override
            public void onClick(Player player) {

            }
        };
        this.addButton(appsButton);

        Button settingButton = new Button(strings.get("home_settings")) {
            @Override
            public void onClick(Player player) {

            }
        };
        this.addButton(settingButton);
    }

}
