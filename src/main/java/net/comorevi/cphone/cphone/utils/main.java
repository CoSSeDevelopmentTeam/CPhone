package net.comorevi.cphone.cphone.utils;

import net.comorevi.cphone.cphone.data.RuntimeData;

import java.io.File;

public class main {

    public static void main(String[] args) {
        RuntimeData.currentDirectory = new File("./");
        RuntimeData.nukkitDirectory = new File("./");
        RuntimeData.pluginDirectory = new File("./");
        System.out.println(ConfigFormatter.format("${currentDir}aaaaaaaaa${nukkitDir}${currentDir}"));
    }
}
