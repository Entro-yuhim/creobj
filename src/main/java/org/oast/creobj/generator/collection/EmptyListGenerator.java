package org.oast.creobj.generator.collection;

import org.oast.creobj.generator.CommonValueGenerator;

import java.util.ArrayList;
import java.util.List;

public class EmptyListGenerator extends CommonValueGenerator {
    @Override
    public Class getValueClass() {
        return List.class;
    }

    @Override
    public List generateValue(Class claz) {
        return new ArrayList();
    }
}
