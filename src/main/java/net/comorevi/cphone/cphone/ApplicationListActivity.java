package net.comorevi.cphone.cphone;

import cn.nukkit.Player;
import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.data.ApplicationData;
import net.comorevi.cphone.cphone.data.StringsData;
import net.comorevi.cphone.cphone.model.Bundle;
import net.comorevi.cphone.cphone.model.ListResponse;
import net.comorevi.cphone.cphone.model.Response;
import net.comorevi.cphone.cphone.sql.ApplicationSQLManager;
import net.comorevi.cphone.cphone.widget.activity.ReturnType;
import net.comorevi.cphone.cphone.widget.activity.base.ListActivity;
import net.comorevi.cphone.cphone.widget.element.Button;
import net.comorevi.cphone.presenter.ActivityProcessor;

public class ApplicationListActivity extends ListActivity {

    private CPhone cPhone;

    public ApplicationListActivity(ApplicationManifest manifest) {
        super(manifest);
    }

    @Override
    public void onCreate(Bundle bundle) {
        this.cPhone = bundle.getCPhone();
        this.setTitle(StringsData.get(cPhone.getPlayer(), "cphone_title"));
        this.setContent(StringsData.get(cPhone.getPlayer(), "message_application_info"));
        init();
    }

    @Override
    public ReturnType onStop(Response response) {
        ListResponse listResponse = (ListResponse) response;
        switch (listResponse.getButtonIndex()) {
            case -1:
                cPhone.home();
                break;
        }
        return ReturnType.TYPE_CONTINUE;
    }

    private void init() {
        ApplicationSQLManager.getApplications(cPhone.getPlayer().getName()).forEach(appName -> {
            ApplicationManifest manifest = ApplicationData.applications.get(appName);
            if (manifest.isVisible()) {
                Button applicationButton = new Button(appName, manifest.getIconType() == null ? "url" : manifest.getIconType(), manifest.getIcon()) {
                    @Override
                    public void onClick(Player player) {
                        ActivityProcessor.startActivity(player, appName);
                    }
                };
                this.addButton(applicationButton);
            }
        });
    }

}
