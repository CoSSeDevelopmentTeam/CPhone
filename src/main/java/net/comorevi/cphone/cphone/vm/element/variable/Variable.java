package net.comorevi.cphone.cphone.vm.element.variable;

public abstract class Variable {

    private String name;

    public Variable(String name) {
        if (name == null) this.name = "ERROR";
        else this.name = name;
    }

    abstract Object get();

    abstract void set(Object obj);

    abstract Type getType();

    public final String getName() {
        return name;
    }

    enum Type {
        VARIABLE_STRING,
        VARIABLE_INTEGER,
        VARIABLE_BOOLEAN,
        VARIABLE_NUMBER,
        VARIABLE_LIST
    }
}

