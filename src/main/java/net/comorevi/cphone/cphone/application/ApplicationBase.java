package net.comorevi.cphone.cphone.application;

import net.comorevi.cphone.cphone.data.RuntimeData;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarFile;

public abstract class ApplicationBase implements Application {

    private ApplicationManifest manifest;

    public ApplicationBase(ApplicationManifest manifest) {
        this.manifest = manifest;
    }

    public ApplicationManifest getManifest() {
        return manifest;
    }

    public InputStream getStringFile(String name) {
        try {
            JarFile jar = new JarFile(RuntimeData.currentDirectory + "/app/" + manifest.getTitle() + ".jar");

            if (jar.getJarEntry(name) == null) return null;
            else return jar.getInputStream(jar.getJarEntry(name));
        } catch (IOException ex) {
            return null;
        }
    }

}
