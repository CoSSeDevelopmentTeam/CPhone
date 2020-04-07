package net.comorevi.cphone.cphone.utils;

public class StringUtils {

    public static String format(String base, Object...args) {
        for (int i = 0; i < args.length; i++) {
            base = base.replaceAll("%" + i + "", String.valueOf(args[i]));
        }
        return base;
    }

}
