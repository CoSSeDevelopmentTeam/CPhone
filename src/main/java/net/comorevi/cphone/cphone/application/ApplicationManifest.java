package net.comorevi.cphone.cphone.application;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationManifest implements Serializable {

    private String title;
    private Map<String, String> titles;
    private String version;
    private String mcbeVersion;
    private String main;
    private String description;
    private Map<String, String> descriptions;
    private String author;
    private String icon;
    private String iconType;
    private String initialize;
    private ApplicationPermission permission;
    private int price;
    private boolean isVisible = true;

    public ApplicationManifest() {
        titles = new HashMap<>();
        descriptions = new HashMap<>();
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

    public void setDescription(String description) {
        this.description = description;
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

    public String getIconType() {
        return iconType;
    }

    public void setIconType(String iconType) {
        this.iconType = iconType;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
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

    public String getTitleByRegion(String region) {
        String title = titles.get(region);
        return title == null ? this.title : title;
    }

    public void setTitles(Map<String, String> titles) {
        this.titles = titles;
    }

    public void addTitle(String region, String value) {
        this.titles.put(region, value);
    }

    public String getDescriptionByRegion(String region) {
        String description = descriptions.get(region);
        return description == null ? this.description : description;
    }

    public void setDescriptions(Map<String, String> descriptions) {
        this.descriptions = descriptions;
    }

    public void addDescription(String region, String value) {
        this.descriptions.put(region, value);
    }

    @Override
    public String toString() {
        return "ApplicationManifest{" +
                "title='" + title + '\'' +
                ", titles=" + titles +
                ", version='" + version + '\'' +
                ", mcbeVersion='" + mcbeVersion + '\'' +
                ", main='" + main + '\'' +
                ", description='" + description + '\'' +
                ", descriptions=" + descriptions +
                ", author='" + author + '\'' +
                ", icon='" + icon + '\'' +
                ", iconType='" + iconType + '\'' +
                ", initialize='" + initialize + '\'' +
                ", permission=" + permission +
                ", price=" + price +
                ", isVisible=" + isVisible +
                '}';
    }
}
