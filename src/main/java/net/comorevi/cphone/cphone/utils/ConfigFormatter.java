package net.comorevi.cphone.cphone.utils;

import net.comorevi.cphone.cphone.data.RuntimeData;

import java.util.HashMap;
import java.util.Map;

class ConfigFormatter {

    private static Map<String, String> replacements;
    private static String result;

    static {
        replacements = new HashMap<>();
        replacements.put("currentDir", RuntimeData.currentDirectory.getPath().endsWith("/") ? RuntimeData.currentDirectory.getPath().substring(0, RuntimeData.currentDirectory.getPath().length() - 2) : RuntimeData.currentDirectory.getPath());
        replacements.put("nukkitDir", RuntimeData.nukkitDirectory.getPath().endsWith("/") ? RuntimeData.nukkitDirectory.getPath().substring(0, RuntimeData.nukkitDirectory.getPath().length() - 2) : RuntimeData.nukkitDirectory.getPath());
        replacements.put("pluginDir", RuntimeData.pluginDirectory.getPath().endsWith("/") ? RuntimeData.pluginDirectory.getPath().substring(0, RuntimeData.pluginDirectory.getPath().length() - 2) : RuntimeData.pluginDirectory.getPath());
    }

    static String format(final String source) {
        result = source;
        replacements.entrySet().forEach(entry -> result = result.replaceAll("\\$\\{" + entry.getKey() + "\\}", entry.getValue()));
        return result;
    }

}
