package net.comorevi.cphone.cphone.core;

import net.comorevi.cphone.cphone.service.AbstractService;

import java.io.File;

public class KernelManager {

    private static Kernel kernel;

    public static void startKernel(File nukkitDir, File pluginDir) {
        kernel = new Kernel(nukkitDir, pluginDir);
        kernel.start();
    }

    public static void shutdown() {
        kernel.shutdown();
    }

    public static void startService(AbstractService service) {
        kernel.startService(service);
    }

}
