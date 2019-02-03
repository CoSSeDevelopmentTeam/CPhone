package net.comorevi.cphone.cphone.utils;

import net.comorevi.cphone.presenter.SharingData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErrorReporter {

    public static void reportError(String player, String title, Exception ex) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_HHmmss");
            Date date = new Date();
            File file = new File(SharingData.pluginInstance.getDataFolder() + "error/" + title + "_" + format.format(date) + ".txt");
            file.createNewFile();

            PrintWriter pw = new PrintWriter(new FileOutputStream(file));
            pw.println("Reported by:" + player);
            pw.println("Date:" + format.format(date));
            ex.printStackTrace(new PrintWriter(new FileOutputStream(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
