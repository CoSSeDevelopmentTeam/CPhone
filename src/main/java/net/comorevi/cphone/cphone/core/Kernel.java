package net.comorevi.cphone.cphone.core;

import net.comorevi.cphone.cphone.data.ApplicationData;
import net.comorevi.cphone.cphone.data.RuntimeData;
import net.comorevi.cphone.cphone.data.StringsData;
import net.comorevi.cphone.cphone.service.AbstractService;
import net.comorevi.cphone.cphone.sql.ApplicationSQLManager;
import net.comorevi.cphone.cphone.utils.PropertiesConfig;
import net.comorevi.cphone.cphone.utils.StringLoader;
import net.comorevi.cphone.presenter.SharingData;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

final class Kernel implements Runnable {

    private boolean started;
    private final ExecutorService executor;

    Kernel(File nukkitDirectory, File pluginDirectory) {
        this.executor = Executors.newCachedThreadPool();

        RuntimeData.nukkitDirectory = nukkitDirectory;
        RuntimeData.pluginDirectory = pluginDirectory;
        RuntimeData.currentDirectory = new File(pluginDirectory.getPath()+ "/CPhone/");

        RuntimeData.currentDirectory.mkdirs();
        new File(RuntimeData.currentDirectory + "/app/").mkdirs();
        new File(RuntimeData.currentDirectory + "/error/").mkdirs();

        prepareConfig(RuntimeData.currentDirectory + "/configuration.properties");

        SharingData.triggerItemId = RuntimeData.config.getInt("TriggerItemId");
        SharingData.phones = new HashMap<>();
        SharingData.activities = new HashMap<>();

        ApplicationData.applications = ApplicationLoader.loadAll(RuntimeData.currentDirectory + "/app/");
        ApplicationSQLManager.init();
    }

    final void start() {
        if (!started) {
            started = true;
            SharingData.server.getScheduler().scheduleRepeatingTask(SharingData.pluginInstance, this::run, 1);
        }
    }

    final void shutdown() {
        executor.shutdown();
    }

    final void startService(AbstractService service) {
        executor.submit(service);
    }

    private void tick() {
        RuntimeData.maxMemory = Runtime.getRuntime().maxMemory();
        RuntimeData.freeMemory = Runtime.getRuntime().freeMemory();
        RuntimeData.totalMemory = Runtime.getRuntime().totalMemory();
        RuntimeData.usingMemory = RuntimeData.totalMemory - RuntimeData.freeMemory;

        double usedMemory = (double) RuntimeData.usingMemory;
        if (usedMemory * 100 / (double) RuntimeData.maxMemory > 80) {
            SharingData.phones.values().forEach(phone -> {
                phone.setActivity(null);
                phone.setHomeMessage(StringsData.get(phone.getPlayer(), "message_home_suspendappreset"));
            });
        }
    }

    private void prepareConfig(String path) {
        PropertiesConfig conf  = new PropertiesConfig(path);

        if (!new File(path).exists()) {
            conf.set("SQLClass", "org.sqlite.JDBC");
            conf.set("SQLEngine", "sqlite");
            conf.set("ApplicationSQL", "${currentDir}/Applications.db");
            conf.set("TriggerItemId", 370);
            conf.set("HomeText", "おしらせはありません。");
            conf.set("ServerOwner", "ServerOwner_Name");
            conf.save();
        }

        RuntimeData.config = conf;
        RuntimeData.config.load(path);
    }

    @Override
    public final void run() {
        tick();
    }

}
