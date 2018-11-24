package net.comorevi.cphone.cphone.widget.activity.base;

import com.google.gson.Gson;
import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.widget.activity.ActivityBase;
import net.comorevi.cphone.cphone.widget.element.Button;
import net.comorevi.cphone.cphone.widget.element.Element;

import java.util.*;

public abstract class ListActivity extends ActivityBase {

    private int id;
    private String title;
    private String content;
    private List<Button> buttons;

    private Map<String, Object> data;
    private Gson gson;
    private String json;


    public ListActivity(ApplicationManifest manifest) {
        super(manifest);

        id = new Random().nextInt(9999999);
        buttons = new ArrayList<>();
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

    public List<Button> getButtons() {
        return buttons;
    }

    public void addButton(Button button) {
        this.buttons.add(button);
    }

    @Override
    public String getJson() {
        encode();
        return json;
    }

    private void encode() {
        data.clear();

        data.put("type", "form");
        data.put("title", this.title);
        data.put("content", this.content);

        List<Map<String, Object>> list = new ArrayList<>();
        for(Button button : buttons) {
            Map<String, Object> map = new HashMap<>();
            map.put("text", button.getText());
            map.put("data", button.getImage());
            list.add(map);
        }

        data.put("buttons", list);

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
