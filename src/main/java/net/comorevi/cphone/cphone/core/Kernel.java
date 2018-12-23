package net.comorevi.cphone.cphone.core;

import net.comorevi.cphone.cphone.data.ApplicationData;
import net.comorevi.cphone.cphone.data.RuntimeData;
import net.comorevi.cphone.cphone.data.StringsData;
import net.comorevi.cphone.cphone.sql.ApplicationSQLManager;
import net.comorevi.cphone.cphone.utils.PropertiesConfig;
import net.comorevi.cphone.cphone.utils.StringLoader;
import net.comorevi.cphone.presenter.SharingData;

import java.io.File;
import java.util.HashMap;

final class Kernel implements Runnable {

    private boolean started;

    public Kernel(File nukkitDirectory, File pluginDirectory) {
        RuntimeData.nukkitDirectory = nukkitDirectory;
        RuntimeData.pluginDirectory = pluginDirectory;
        RuntimeData.currentDirectory = new File(pluginDirectory.getPath()+ "/CPhone/");

        RuntimeData.currentDirectory.mkdirs();
        new File(RuntimeData.currentDirectory + "/app/").mkdirs();

        prepareConfig(RuntimeData.currentDirectory + "/configuration.properties");

        StringsData.strings = StringLoader.loadString(this.getClass().getClassLoader().getResourceAsStream("strings-ja.xml"));
        SharingData.triggerItemId = RuntimeData.config.getInt("TriggerItemId");
        SharingData.phones = new HashMap<>();
        SharingData.activities = new HashMap<>();

        ApplicationData.applications = ApplicationLoader.loadAll(RuntimeData.currentDirectory + "/app/");
        ApplicationSQLManager.init();
    }

    public void start() {
        if (!started) {
            started = true;
            SharingData.server.getScheduler().scheduleRepeatingTask(SharingData.pluginInstance, this::run, 1);
        }
    }

    private void tick() {
        RuntimeData.maxMemory = Runtime.getRuntime().maxMemory();
        RuntimeData.freeMemory = Runtime.getRuntime().freeMemory();
        RuntimeData.totalMemory = Runtime.getRuntime().totalMemory();
        RuntimeData.usingMemory = RuntimeData.totalMemory - RuntimeData.freeMemory;

        double usedMemory = (double) RuntimeData.usingMemory;
        if (usedMemory * 100 / (double) RuntimeData.maxMemory > 80) {
            SharingData.phones.values().forEach(phone -> phone.setActivity(null));
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
            conf.save();
        }

        RuntimeData.config = conf;
        RuntimeData.config.load(path);
    }

    @Override
    public void run() {
        tick();
    }

}
