package net.comorevi.cphone.cphone.core;

import net.comorevi.cphone.cphone.data.RuntimeData;
import net.comorevi.cphone.cphone.sql.ApplicationSQLManager;
import net.comorevi.cphone.cphone.utils.PropertiesConfig;
import net.comorevi.cphone.presenter.SharingData;

import java.io.File;

class Kernel implements Runnable {

    private boolean started;

    public Kernel(File nukkitDirectory, File pluginDirectory) {
        RuntimeData.nukkitDirectory = nukkitDirectory;
        RuntimeData.pluginDirectory = pluginDirectory;
        RuntimeData.currentDirectory = new File(pluginDirectory.getPath().replaceAll("\\\\", "/") + (pluginDirectory.getPath().replaceAll("\\\\", "/").endsWith("/") ? "" : "/") + "CPhone/");

        RuntimeData.currentDirectory.mkdirs();

        prepareConfig(RuntimeData.currentDirectory + "configuration.properties");

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
    }

    private void prepareConfig(String path) {
        PropertiesConfig conf  = new PropertiesConfig(path);
        conf.set("SQLClass", "org.sqlite.JDBC");
        conf.set("SQLEngine", "sqlite");
        conf.set("ApplicationSQL", "${currentDir}/Applications.db");
        conf.save();

        RuntimeData.config = conf;
        RuntimeData.config.load(path);
    }

    @Override
    public void run() {
        tick();
    }

}
