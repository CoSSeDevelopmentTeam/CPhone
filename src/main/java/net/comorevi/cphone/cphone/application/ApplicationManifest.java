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
    private String initialize;
    private ApplicationPermission permission;
    private int price;

    public ApplicationManifest() {

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

    public void setDescription(String decription) {
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

    public String getInitialize() {
        return initialize;
    }

    public void setInitialize(String initialize) {
        this.initialize = initialize;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ApplicationPermission getPermission() {
        return permission;
    }

    public void setPermission(ApplicationPermission attribute) {
        this.permission = attribute;
    }

    @Override
    public String toString() {
        return "ApplicationManifest{" +
                "title='" + title + '\'' +
                ", version='" + version + '\'' +
                ", mcbeVersion='" + mcbeVersion + '\'' +
                ", main='" + main + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", icon='" + icon + '\'' +
                ", initialize='" + initialize + '\'' +
                ", permission='" + permission.getName() + '\'' +
                ", price=" + price +
                '}';
    }
}
