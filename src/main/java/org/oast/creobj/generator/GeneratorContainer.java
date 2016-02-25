package org.oast.creobj.generator;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bandr on 24.02.2016.
 */
public class GeneratorContainer {
    private Map<Class, ValueGenerator> defaultGenerators = new HashMap<>();
    private Map<String, ValueGenerator> userDefinedGenerators = new HashMap<>();

    public Map<Class, ValueGenerator> getDefaultGenerators() {
        return defaultGenerators;
    }

    public Map<String, ValueGenerator> getUserDefinedGenerators() {
        return userDefinedGenerators;
    }

    public void addDefaultGenerator(Class key, ValueGenerator value) {
        defaultGenerators.put(key, value);
    }

    public void addDefaultGenerators(Map<Class, ValueGenerator> values) {
        defaultGenerators.putAll(values);
    }

    public void addUserGenerators(Map<String, ValueGenerator> valueGeneratorMap) {
        userDefinedGenerators.putAll(valueGeneratorMap);
    }

    public void addUserGenerator(String key, ValueGenerator value) {
        userDefinedGenerators.put(key, value);
    }

    public boolean canApplyDefaultGenerator(Field f) {
        return canApplyDefaultGenerator(f.getType());
    }

    public boolean canApplyDefaultGenerator(Class clazz) {
        if (defaultGenerators.containsKey(clazz)) return true;
        else {
            for (Map.Entry<Class, ValueGenerator> entry : defaultGenerators.entrySet()) {
                if (!entry.getValue().isAssignable()) continue; // FIXME: 24.02.2016 re-write to ValueGenerator#canApplyForClass
                if (entry.getKey().isAssignableFrom(clazz)) return true;
            }
        }
        return false;
    }

    public ValueGenerator getValueGenerator(Class clazz) {
        if (defaultGenerators.containsKey(clazz)) return defaultGenerators.get(clazz);
        else {
            for (Map.Entry<Class, ValueGenerator> entry : defaultGenerators.entrySet()) {
                if (!entry.getValue().isAssignable()) continue; // FIXME: 24.02.2016 re-write to ValueGenerator#canApplyForClass
                if (entry.getKey().isAssignableFrom(clazz)) return entry.getValue();
            }
        }
        return null;
    }

    public boolean canApplyUserDefinedGenerator(Field f) {
        //Generator is provided for this field and it can be applied by type
        return userDefinedGenerators.containsKey(f.getName()) && userDefinedGenerators.get(f.getName()).canApplyForClass(f.getType());
    }

    public ValueGenerator getUserDefinedGenerator(String fieldName) {
        return userDefinedGenerators.get(fieldName);
    }
}
