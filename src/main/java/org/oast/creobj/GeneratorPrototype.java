package org.oast.creobj;

import org.oast.creobj.generator.ValueGenerator;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bandr on 22.02.2016.
 */
public class GeneratorPrototype<T> { // FIXME: 22.02.2016 get actual name

    private Map<Field, ValueGenerator> fieldGenerators = new HashMap<>();

    public GeneratorPrototype(Class<T> clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field f : declaredFields) {
            Util.makeAccessible(f);
            if (Modifier.isFinal(f.getModifiers()) || Modifier.isStatic(f.getModifiers())) {
                continue;
            }

        }
    }
}
