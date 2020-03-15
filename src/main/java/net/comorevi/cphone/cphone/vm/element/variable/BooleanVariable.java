package net.comorevi.cphone.cphone.vm.element.variable;

public class BooleanVariable extends Variable {

    private boolean value;

    public BooleanVariable(String name) {
        this(name, false);
    }

    public BooleanVariable(String name, boolean value) {
        super(name);
        this.value = value;
    }

    public BooleanVariable fromStringVariable(StringVariable value) {
        return new BooleanVariable(getName(), Boolean.getBoolean((String) value.get()));
    }

    @Override
    public Object get() {
        return value;
    }

    @Override
    public void set(Object obj) {
        this.value = (boolean) obj;
    }

    @Override
    public Type getType() {
        return Type.VARIABLE_BOOLEAN;
    }

}
