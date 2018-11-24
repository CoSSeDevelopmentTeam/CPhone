package net.comorevi.cphone.cphone.utils;

import net.comorevi.cphone.cphone.data.RuntimeData;

import java.util.HashMap;
import java.util.Map;

class ConfigFormatter {

    private static Map<String, String> replacements;
    private static String result;

    static {
        replacements = new HashMap<>();
        replacements.put("currentDir", RuntimeData.currentDirectory.getPath());
        replacements.put("nukkitDir", RuntimeData.nukkitDirectory.getPath());
        replacements.put("pluginDir", RuntimeData.pluginDirectory.getPath());
    }

    static String format(final String source) {
        result = source;
        replacements.entrySet().forEach(entry -> {
            String value = entry.getValue().replaceAll("\\\\", "/");
            result = result.replaceAll("\\$\\{" + entry.getKey() + "\\}", value);
        });
        return result;
    }

}
