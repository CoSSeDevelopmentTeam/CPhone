package net.comorevi.cphone;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class TestMain {

    public static void main(String[] args) {
        double used = (double) Runtime.getRuntime().totalMemory() - (double) Runtime.getRuntime().freeMemory();
        System.out.println(used * 100 / (double) Runtime.getRuntime().maxMemory());
        System.out.println("CPU: " + Runtime.getRuntime().availableProcessors());
    }
}
