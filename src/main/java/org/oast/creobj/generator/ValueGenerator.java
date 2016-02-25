package org.oast.creobj.generator;

public interface ValueGenerator<T> {

    // FIXME: 22.02.2016 Unnecessary interface parameter.
    boolean isAssignable();

    Class<T> getValueClass();

    T generateValue(Class<?> claz);

    boolean canApplyForClass(Class<?> clazz);
}
