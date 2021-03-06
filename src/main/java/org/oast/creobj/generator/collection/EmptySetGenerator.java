package org.oast.creobj.generator.collection;

import org.oast.creobj.generator.CommonValueGenerator;

import java.util.HashSet;
import java.util.Set;

public class EmptySetGenerator extends CommonValueGenerator {
    @Override
    public Class getValueClass() {
        return Set.class;
    }

    @Override
    public boolean canApplyForClass(Class clazz) {
        return Set.class.isAssignableFrom(clazz);
    }

    @Override
    public Set generateValue(Class claz) {
        return new HashSet();
    }
}