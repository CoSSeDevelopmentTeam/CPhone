package net.comorevi.cphone.cphone.model;

import cn.nukkit.Player;
import net.comorevi.cphone.cphone.CPhone;

import java.io.File;
import java.io.Serializable;
import java.util.Map;

public final class Bundle implements Serializable {

    private final CPhone cphone;
    private final long time;
    private final File currentDir;
    private final Map<String, String> strings;

    public Bundle(CPhone cphone, long time, File currentDir, Map<String, String> strings) {
        this.cphone = cphone;
        this.time = time;
        this.currentDir = currentDir;

        this.strings = strings;
    }

    public CPhone getCPhone() {
        return cphone;
    }

    public long getTime() {
        return time;
    }

    public File getCurrentDir() {
        return currentDir;
    }

    public String getString(String name) {
        return strings.get(name);
    }

    public Map<String, String> getStrings() {
        return strings;
    }
}
