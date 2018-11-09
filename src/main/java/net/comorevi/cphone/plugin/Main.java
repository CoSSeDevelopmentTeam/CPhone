package net.comorevi.cphone.plugin;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;
import net.comorevi.cphone.cphone.core.Kernel;
import net.comorevi.cphone.data.SharingData;

import java.io.File;

public class Main extends PluginBase {

    @Override
    public void onEnable() {
        getLogger().info("Enabling...");
        getServer().getPluginManager().registerEvents(new EventListener(), this);

        SharingData.pluginInstance = this;
        SharingData.server = getServer();
        SharingData.kernel = new Kernel(new File("./"), new File(getServer().getPluginPath()));

        SharingData.kernel.start();
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return CommandProcessor.onCommand(sender, command, label, args);
    }

}
