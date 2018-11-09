package net.comorevi.cphone.cphone.application;

public enum ApplicationAttribute {

    ATTRIBUTE_DEFAULT("default"),

    ATTRIBUTE_OPERATOR("operator"),

    ATTRIBUTE_OWNER("owner"),

    ATTRIBUTE_EVERYONE("everyone");

    private String name;

    ApplicationAttribute(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
