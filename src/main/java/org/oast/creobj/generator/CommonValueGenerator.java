package org.oast.creobj.generator;

public abstract class CommonValueGenerator<T> implements ValueGenerator<T> {

    protected CommonValueGenerator(){}

    @Override
    public boolean isAssignable() {
        return false;
    }

    String getIllegalClassAlert(Class<?> claz) {
        return "[WARNING] Class " + claz.getName() +
                " cannot be converted to " + getValueClass().getName();
    }
}
