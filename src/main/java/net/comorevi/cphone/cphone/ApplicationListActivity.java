package net.comorevi.cphone.cphone;

import cn.nukkit.Player;
import net.comorevi.cphone.cphone.application.ApplicationBase;
import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.data.ApplicationData;
import net.comorevi.cphone.cphone.data.RuntimeData;
import net.comorevi.cphone.cphone.data.StringsData;
import net.comorevi.cphone.cphone.model.Bundle;
import net.comorevi.cphone.cphone.model.ListResponse;
import net.comorevi.cphone.cphone.model.Response;
import net.comorevi.cphone.cphone.sql.ApplicationSQLManager;
import net.comorevi.cphone.cphone.utils.StringLoader;
import net.comorevi.cphone.cphone.widget.activity.ActivityBase;
import net.comorevi.cphone.cphone.widget.activity.ReturnType;
import net.comorevi.cphone.cphone.widget.activity.base.ListActivity;
import net.comorevi.cphone.cphone.widget.element.Button;
import net.comorevi.cphone.presenter.ActivityProcessor;

import static net.comorevi.cphone.cphone.data.StringsData.strings;

public class ApplicationListActivity extends ListActivity {

    private CPhone cPhone;
    private Player player;

    public ApplicationListActivity(ApplicationManifest manifest) {
        super(manifest);
    }

    @Override
    public void onCreate(Bundle bundle) {
        this.setTitle(strings.get("cphone_title"));
        this.cPhone = bundle.getCPhone();
        this.setContent(StringsData.strings.get("message_application_info"));
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
            Button applicationButton = new Button(appName, "url", manifest.getIcon()) {
                @Override
                public void onClick(Player player) {
                    ActivityProcessor.startActivity(player, appName);
                }
            };
            this.addButton(applicationButton);
        });
    }

}
