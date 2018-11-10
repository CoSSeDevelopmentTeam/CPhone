package net.comorevi.cphone.cphone.application;

public enum ApplicationPermission {

    ATTRIBUTE_DEFAULT("default"),

    ATTRIBUTE_OPERATOR("operator"),

    ATTRIBUTE_OWNER("owner"),

    ATTRIBUTE_EVERYONE("everyone");

    private String name;

    ApplicationPermission(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ApplicationPermission fromName(String name) {
        switch (name) {
            case "default":
                return ATTRIBUTE_DEFAULT;

            case "operator":
                return ATTRIBUTE_OPERATOR;

            case "owner":
                return ATTRIBUTE_OWNER;

            case "everyone":
                return ATTRIBUTE_EVERYONE;

            default:
                throw new IllegalArgumentException("attribute must be <default|operator|owner|everyone>.");
        }
    }

}
