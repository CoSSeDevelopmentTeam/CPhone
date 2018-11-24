package net.comorevi.cphone.cphone.application;

public abstract class ApplicationBase implements Application {

    private ApplicationManifest manifest;

    public ApplicationBase(ApplicationManifest manifest) {
        this.manifest = manifest;
    }

    public ApplicationManifest getManifest() {
        return manifest;
    }

}
