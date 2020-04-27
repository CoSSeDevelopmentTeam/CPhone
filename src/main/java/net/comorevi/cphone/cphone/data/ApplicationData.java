package net.comorevi.cphone.cphone.data;

import net.comorevi.cphone.cphone.application.ApplicationInstance;
import net.comorevi.cphone.cphone.application.ApplicationManifest;

import java.util.HashMap;
import java.util.Map;

public final class ApplicationData {

    public static Map<String, ApplicationManifest> applications = new HashMap<>();

    public final static Map<String, ApplicationInstance> instances = new HashMap<>();

}
