package net.comorevi.cphone.plugin;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;
import net.comorevi.cphone.presenter.CPhoneManager;
import net.comorevi.cphone.presenter.SharingData;

public class Main extends PluginBase {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new EventListener(), this);

        SharingData.pluginInstance = this;
        SharingData.server = getServer();

        CPhoneManager.startKernel();
        CPhoneManager.addListener();

        getLogger().info("Enabled.");
    }


    @Override
    public void onDisable() {
        CPhoneManager.shutdownKernel();
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return CommandProcessor.onCommand(sender, command, label, args);
    }

}
