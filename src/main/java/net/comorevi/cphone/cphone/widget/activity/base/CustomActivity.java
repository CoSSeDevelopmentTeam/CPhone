package net.comorevi.cphone.cphone.widget.activity.base;

import com.google.gson.Gson;
import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.widget.activity.ActivityBase;
import net.comorevi.cphone.cphone.widget.element.Element;

import java.util.*;

public abstract class CustomActivity extends ActivityBase {

    private int id;
    private String title = "CPhone";
    private List<Element> elements;

    private Gson gson;
    private String json;

    public CustomActivity(ApplicationManifest manifest) {
        super(manifest);

        id = new Random().nextInt(9999999);
        elements = new ArrayList<>();
        gson = new Gson();
        json = "";
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addFormElement(Element element) {
        this.elements.add(element);
    }

    public List<Element> getElements() {
        return elements;
    }

    @Override
    public String getJson() {
        encode();
        return json;
    }

    private void encode() {
        Map<String, Object> data = new HashMap<>();

        data.put("type", "custom_form");
        data.put("title", this.title);

        List<Map<String, Object>> list = new ArrayList<>();
        for(Element element : elements) {
            list.add(element.getData());
        }

        data.put("content", list);

        json = gson.toJson(data);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }
}
