package net.comorevi.cphone.plugin;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;
import net.comorevi.cphone.cphone.core.KernelManager;
import net.comorevi.cphone.presenter.SharingData;

import java.io.File;

public class Main extends PluginBase {

    @Override
    public void onEnable() {
        getLogger().info("Enabling...");
        getServer().getPluginManager().registerEvents(new EventListener(), this);

        SharingData.pluginInstance = this;
        SharingData.server = getServer();

        KernelManager.startKernel(new File("./"), new File(getServer().getPluginPath()));
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return CommandProcessor.onCommand(sender, command, label, args);
    }

}
