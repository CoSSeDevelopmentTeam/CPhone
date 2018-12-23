package net.comorevi.cphone.cphone.application;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ApplicationClassLoader extends URLClassLoader {

    private final Map<String, Class> classes = new HashMap<>();
    private final Map<String, ApplicationClassLoader> classLoaders = new HashMap<>();

    public ApplicationClassLoader(ClassLoader parent, File file) throws MalformedURLException {
        super(new URL[]{file.toURI().toURL()}, parent);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return this.findClass(name, true);
    }

    protected Class<?> findClass(String name, boolean checkGlobal) throws ClassNotFoundException {
        if (name.startsWith("net.comorevi.cphone.")) {
            throw new ClassNotFoundException(name);
        }
        Class<?> result = classes.get(name);

        if (result == null) {
            if (checkGlobal) {
                result = getClassByName(name);
            }

            if (result == null) {
                result = super.findClass(name);

                if (result != null) {
                    setClass(name, result);
                }
            }

            classes.put(name, result);
        }

        return result;
    }

    Set<String> getClasses() {
        return classes.keySet();
    }

    Class<?> getClassByName(final String name) {
        Class<?> cachedClass = classes.get(name);

        if (cachedClass != null) {
            return cachedClass;
        } else {
            for (ApplicationClassLoader loader : this.classLoaders.values()) {

                try {
                    cachedClass = loader.findClass(name, false);
                } catch (ClassNotFoundException e) {
                    //ignore
                }
                if (cachedClass != null) {
                    return cachedClass;
                }
            }
        }
        return null;
    }

    void setClass(final String name, final Class<?> clazz) {
        if (!classes.containsKey(name)) {
            classes.put(name, clazz);
        }
    }

    private void removeClass(String name) {
        Class<?> clazz = classes.remove(name);
    }

    public void put(String name, ApplicationClassLoader loader) {
        classLoaders.put(name, loader);
    }
}
