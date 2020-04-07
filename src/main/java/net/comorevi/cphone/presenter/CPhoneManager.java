package net.comorevi.cphone.presenter;

import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import net.comorevi.cphone.cphone.core.KernelManager;

import java.io.File;

public class CPhoneManager {

    public static void addListener() {
        SharingData.server.getPluginManager().registerEvents(new NukkitEventListener(), SharingData.pluginInstance);
    }

    public static void startKernel() {
        KernelManager.startKernel(new File("./"), new File(SharingData.server.getPluginPath()));
    }

    public static void shutdownKernel() {
        KernelManager.shutdown();
    }
}
