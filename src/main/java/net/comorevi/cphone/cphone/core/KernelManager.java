package net.comorevi.cphone.cphone.core;

import java.io.File;

public class KernelManager {

    private static Kernel kernel;

    public static void startKernel(File nukkitDir, File pluginDir) {
        kernel = new Kernel(nukkitDir, pluginDir);
        kernel.start();
    }

}
