package org.oast.creobj.generator.collection;

import org.oast.creobj.generator.CommonValueGenerator;

import java.util.HashMap;
import java.util.Map;

public class EmptyMapGenerator extends CommonValueGenerator {
    @Override
    public Class getValueClass() {
        return Map.class;
    }

    @Override
    public boolean canApplyForClass(Class clazz) {
        return Map.class.isAssignableFrom(clazz);
    }

    @Override
    public Map generateValue(Class claz) {
        return new HashMap();
    }
}