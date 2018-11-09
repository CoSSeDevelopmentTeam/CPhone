package net.comorevi.cphone.cphone.core;

import net.comorevi.cphone.cphone.data.RuntimeData;
import net.comorevi.cphone.presenter.SharingData;

import java.io.File;

class Kernel implements Runnable {

    public Kernel(File nukkitDirectory, File pluginDirectory) {
        RuntimeData.nukkitDirectory = nukkitDirectory;
        RuntimeData.pluginDirectory = pluginDirectory;
        RuntimeData.currentDirectory = new File(pluginDirectory.getPath().replaceAll("\\\\", "/") + (pluginDirectory.getPath().replaceAll("\\\\", "/").endsWith("/") ? "" : "/") + "CPhone/");

        RuntimeData.currentDirectory.mkdirs();
    }

    public void start() {
        SharingData.server.getScheduler().scheduleRepeatingTask(SharingData.pluginInstance, this::run, 1);
    }

    private void tick() {
        RuntimeData.maxMemory = Runtime.getRuntime().maxMemory();
        RuntimeData.freeMemory = Runtime.getRuntime().freeMemory();
        RuntimeData.totalMemory = Runtime.getRuntime().totalMemory();
        RuntimeData.usingMemory = RuntimeData.totalMemory - RuntimeData.freeMemory;
    }

    @Override
    public void run() {
        tick();
    }

}
