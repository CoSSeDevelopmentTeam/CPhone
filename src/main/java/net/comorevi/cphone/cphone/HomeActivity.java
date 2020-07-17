package net.comorevi.cphone.cphone;

import cn.nukkit.Player;
import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.data.RuntimeData;
import net.comorevi.cphone.cphone.data.StringsData;
import net.comorevi.cphone.cphone.model.Bundle;
import net.comorevi.cphone.cphone.model.ListResponse;
import net.comorevi.cphone.cphone.model.Response;
import net.comorevi.cphone.cphone.widget.activity.ReturnType;
import net.comorevi.cphone.cphone.widget.activity.base.ListActivity;
import net.comorevi.cphone.cphone.widget.element.Button;
import net.comorevi.cphone.presenter.SharingData;

public class HomeActivity extends ListActivity {

    private CPhone cPhone;

    public HomeActivity(ApplicationManifest manifest) {
        super(manifest);
    }

    @Override
    public void onCreate(Bundle bundle) {
        this.cPhone = bundle.getCPhone();
        this.setTitle(StringsData.get(cPhone.getPlayer(), "cphone_title"));
        init();
    }

    @Override
    public ReturnType onStop(Response response) {
        ListResponse listResponse = (ListResponse) response;
        switch (listResponse.getButtonIndex()) {
            case -1:
            case 0:
                cPhone.setOpening(false);

                String homeText = RuntimeData.config.getString("HomeText");
                cPhone.setHomeMessage(homeText == null ? StringsData.get(cPhone.getPlayer(), "message_home_nonotification") : homeText);
                ((ListResponse) response).getPlayer().sendTip(StringsData.get(cPhone.getPlayer(), "message_home_closed"));

                break;

            case 1:
                cPhone.back();
                break;

            case 2:
                ApplicationListActivity applicationListActivity = new ApplicationListActivity(getManifest());
                applicationListActivity.start(cPhone.getPlayer(), null);
                break;

            case 3:
                break;
        }
        return ReturnType.TYPE_CONTINUE;
    }

    private void init() {
        this.addButton(new Button(StringsData.get(cPhone.getPlayer(), "home_close")));
        this.addButton(new Button(StringsData.get(cPhone.getPlayer(), "home_restartApp")));
        this.addButton(new Button(StringsData.get(cPhone.getPlayer(), "home_applications")));
    }

}
