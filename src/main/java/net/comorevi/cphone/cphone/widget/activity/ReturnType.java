package net.comorevi.cphone.cphone.widget.activity;

public enum ReturnType {

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
            case "end":
                return TYPE_END;

            case "continue":
                return TYPE_CONTINUE;

            default:
                return null;
        }
    }

}
