package net.comorevi.cphone.presenter;

import cn.nukkit.Server;
import net.comorevi.cphone.cphone.CPhone;
import net.comorevi.cphone.cphone.widget.activity.ActivityBase;
import net.comorevi.cphone.plugin.Main;

import java.util.Map;

public class SharingData {

    public static Server server;

    public static Main pluginInstance;

    public static int triggerItemId;

    public static Map<String, CPhone> phones;

    public static Map<Integer, ActivityBase> activities;

}
