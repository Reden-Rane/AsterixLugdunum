package fr.info.game.logic.math;

public class DynamicValue<E extends Number> {

    private E value;

    public DynamicValue() {
    }

    public DynamicValue(E defaultValue) {
        this.setValue(defaultValue);
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }
}
