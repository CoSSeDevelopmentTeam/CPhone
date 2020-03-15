package net.comorevi.cphone.cphone.vm.element.variable;

public class IntegerVariable extends Variable {

    private int value;

    public IntegerVariable(String name) {
        this(name, 0);
    }

    public IntegerVariable(String name, int value) {
        super(name);
        this.value = value;
    }

    @Override
    public Object get() {
        return value;
    }

    @Override
    public void set(Object obj) {
        value = (int) obj;
    }

    @Override
    public Type getType() {
        return Type.VARIABLE_INTEGER;
    }

}
