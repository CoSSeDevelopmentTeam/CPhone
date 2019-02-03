package net.comorevi.cphone.cphone.service;

import net.comorevi.cphone.cphone.model.Bundle;

public abstract class AbstractService extends Thread {

    private final Bundle bundle;

    public AbstractService(String name, Bundle bundle) {
        super(name);
        this.bundle = bundle;
    }

    @Override
    public final void run() {
        onStart(bundle);
    }

    public abstract void onStart(Bundle bundle);
}
