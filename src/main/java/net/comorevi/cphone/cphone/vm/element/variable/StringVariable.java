package net.comorevi.cphone.cphone.vm.element.variable;

import java.util.Objects;

public class StringVariable extends Variable {

    private String value;

    public StringVariable(String name) {
        this(name, "");
    }

    public StringVariable(String name, String value) {
        super(name);
        this.value = value == null ? "" : value;
    }

    public StringVariable subString(IntegerVariable start) {
        int s = (int) start.get();
        return new StringVariable(getName(), value.substring(s));
    }

    public StringVariable subString(IntegerVariable start, IntegerVariable end) {
        int s = (int) start.get();
        int e = (int) end.get();
        if (value == null) return new StringVariable(getName(), null);
        else return new StringVariable(getName(), value.substring(s, e));
    }

    public StringVariable add(StringVariable str) {
        return new StringVariable(getName(), value + str.get());
    }

    public BooleanVariable equals(StringVariable value1, StringVariable value2) {
        return new BooleanVariable(getName() + "_equals", Objects.equals(value1.get(), value2.get()));
    }

    public IntegerVariable length() {
        return new IntegerVariable(getName() + "_length", value.length());
    }

    @Override
    public Object get() {
        return value;
    }

    @Override
    public void set(Object obj) {
        value = String.valueOf(obj);
    }

    @Override
    public Type getType() {
        return Type.VARIABLE_STRING;
    }

}
