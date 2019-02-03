package net.comorevi.cphone.cphone.widget.activity;

public enum ReturnType {

    TYPE_IGNORE("ignore"),

    TYPE_END("end"),

    TYPE_CONTINUE("continue");

    private String name;

    ReturnType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ReturnType fromName(String name) {
        switch(name) {
            case "ignore":
                return TYPE_IGNORE;

            case "end":
                return TYPE_END;

            case "continue":
                return TYPE_CONTINUE;

            default:
                return null;
        }
    }

}
