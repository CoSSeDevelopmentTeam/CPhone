package net.comorevi.cphone;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class TestMain {

    public static void main(String[] args) {
        String text = "[null, 1, \"100\"]";
        List<Object> list = new Gson().fromJson(text, new TypeToken<List>(){}.getType());
        List<String> option = new ArrayList<>() {
            {
                add("test");
            }
        };
        list.forEach(System.out::println);

        System.out.println(option.get(Math.round(Float.parseFloat(String.valueOf("0.0")))));
    }
}
