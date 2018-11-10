package net.comorevi.cphone.cphone.core;

import net.comorevi.cphone.cphone.application.ApplicationBase;
import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.utils.ManifestLoader;
import net.comorevi.cphone.presenter.SharingData;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarFile;

class ApplicationLoader {

    protected static Map<String, ApplicationManifest> load(String path) {
        File appDir = new File(path);
        Map<String, ApplicationManifest> applications = new HashMap<>();

        if (!appDir.isDirectory()) return null;

        for (File file : appDir.listFiles()) {
            try {
                if (file.isDirectory() || !file.getName().endsWith(".jar")) continue;

                JarFile jar = new JarFile(file);
                ApplicationManifest manifest = ManifestLoader.loadManifest(jar.getInputStream(jar.getJarEntry("ApplicationManifest.xml")));

                if (manifest.toString().contains("null")) {
                    SharingData.server.getLogger().alert(manifest.getTitle() + "has illegal Manifest.");
                    continue;
                }

                if (manifest.getMain().startsWith("net.comorevi.cphone")) {
                    SharingData.server.getLogger().alert(manifest.getTitle() + " main class must not start with \"net.comorevi.cphone\".");
                    continue;
                }

                Class<? extends ApplicationBase> mainClass = new URLClassLoader(new URL[] {file.toURI().toURL()}, SharingData.pluginInstance.getClass().getClassLoader())
                        .loadClass(manifest.getMain())
                        .asSubclass(ApplicationBase.class);

                applications.put(manifest.getTitle(), manifest);

            } catch (ClassNotFoundException e) {
                SharingData.server.getLogger().alert(file.getName().replaceAll(".jar", "") + " main class is not found.");
                continue;

            } catch (ClassCastException e) {
                SharingData.server.getLogger().alert(file.getName().replaceAll(".jar", "") + " must be sub class of ApplicationBase.");
                continue;

            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }

        return applications;
    }

}
