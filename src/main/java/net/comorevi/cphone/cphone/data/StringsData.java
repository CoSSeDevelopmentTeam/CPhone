package net.comorevi.cphone.cphone.data;

import cn.nukkit.Player;
import net.comorevi.cphone.cphone.utils.StringLoader;

import java.util.HashMap;
import java.util.Map;

// This class is used by CPhone internal system, so third-party applications should use Bundle#getString(key, args...)
public class StringsData {

    private static Map<String, Map<String, String>> strings = new HashMap<>();

    static {
        StringsData.strings.put("ja", StringLoader.loadString(StringsData.class.getClassLoader().getResourceAsStream("strings-ja.xml")));
        StringsData.strings.put("en", StringLoader.loadString(StringsData.class.getClassLoader().getResourceAsStream("strings-en.xml")));
    }

    public static String get(Player player, String key) {
        String value = strings.get(player.getLoginChainData().getLanguageCode().substring(0, 2)).get(key);
        if (value == null) strings.get("en").get(key);
        return value;
    }

}
