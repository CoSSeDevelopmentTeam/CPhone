package net.comorevi.cphone.cphone.application;

import java.io.Serializable;

public class ApplicationManifest implements Serializable {

    private String title;
    private String version;
    private String mcbeVersion;
    private String main;
    private String description;
    private String author;
    private String icon;
    private int price;

    public ApplicationManifest(String title, String main, String description, String version, String mcbeVersion, String author, String icon, int price) {
        this.title = title;
        this.main = main;
        this.description = description;
        this.version = version;
        this.mcbeVersion = mcbeVersion;
        this.author = author;
        this.icon = icon;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMcbeVersion() {
        return mcbeVersion;
    }

    public void setMcbeVersion(String mcbeVersion) {
        this.mcbeVersion = mcbeVersion;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDecription(String decription) {
        this.description = decription;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
