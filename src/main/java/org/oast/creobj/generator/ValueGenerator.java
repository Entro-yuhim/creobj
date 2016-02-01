package org.oast.creobj.generator;

public interface ValueGenerator <T> {

    boolean isAssignable();

    Class<T> getValueClass();

    T generateValue(Class<?> claz);

}
