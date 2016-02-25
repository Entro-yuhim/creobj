package org.oast.creobj;

import org.oast.creobj.generator.*;
import org.oast.creobj.generator.collection.EmptyListGenerator;
import org.oast.creobj.generator.collection.EmptyMapGenerator;
import org.oast.creobj.generator.collection.EmptySetGenerator;

import java.util.*;

/**
 * Created by bandr on 22.02.2016.
 */

public class PrototypeFactory {
    public static final Map<Class, ValueGenerator> SUPPORTED_GENERATORS = new HashMap<Class, ValueGenerator>() {{
        put(Enum.class, new EnumGenerator());
        put(Number.class, new NumberGenerator());
        put(String.class, new StringGenerator());
        put(Date.class, new UtilDateGenerator());
        put(Set.class, new EmptySetGenerator());
        put(List.class, new EmptyListGenerator());
        put(Map.class, new EmptyMapGenerator());
    }};

    public static boolean isTypeSupportedByDefault(Class clazz) {
        return SUPPORTED_GENERATORS.containsKey(clazz);
    }
}
