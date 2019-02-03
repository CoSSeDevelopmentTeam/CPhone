package net.comorevi.cphone.cphone.service;

import net.comorevi.cphone.cphone.core.KernelManager;

public class ServiceManager {

    public static void startService(AbstractService service) {
        KernelManager.startService(service);
    }
    
}
