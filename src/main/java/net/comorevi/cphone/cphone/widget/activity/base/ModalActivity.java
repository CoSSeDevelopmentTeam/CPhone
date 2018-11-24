package net.comorevi.cphone.cphone.widget.activity.base;

import com.google.gson.Gson;
import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.widget.activity.ActivityBase;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public abstract class ModalActivity extends ActivityBase {

    private int id;
    private String title;
    private String content;
    private String button1Text;
    private String button2Text;

    private Gson gson;
    private String json;


    public ModalActivity(ApplicationManifest manifest) {
        super(manifest);

        id = new Random().nextInt(9999999);
        gson = new Gson();
        json = "";
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getButton1Text() {
        return button1Text;
    }

    public void setButton1Text(String button1Text) {
        this.button1Text = button1Text;
    }

    public String getButton2Text() {
        return button2Text;
    }

    public void setButton2Text(String button2Text) {
        this.button2Text = button2Text;
    }

    @Override
    public String getJson() {
        encode();
        return json;
    }

    private void encode() {
        Map<String, Object> data = new HashMap<>();

        data.put("type", "modal");
        data.put("title", this.title);
        data.put("content", this.content);
        data.put("button1", this.button1Text);
        data.put("button2", this.button2Text);

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
