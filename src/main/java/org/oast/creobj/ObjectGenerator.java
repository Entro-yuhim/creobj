package org.oast.creobj;

import org.oast.creobj.generator.*;
import org.oast.creobj.generator.collection.EmptyHashSetGenerator;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

@SuppressWarnings("unchecked")
public class ObjectGenerator {

    private Objenesis objenesis = new ObjenesisStd();

    private boolean generateInterfaceClasses = false;
    private Map<String, ValueGenerator> userDefinedGenerators = new HashMap<>();
    private GeneratorContainer generatorContainer = new GeneratorContainer();

    protected void addValueGenerator(ValueGenerator valueGenerator) {
        generatorContainer.addDefaultGenerator(valueGenerator.getValueClass(), valueGenerator);
    }

    /**
     * Instantiates Object generator with default generators
     */
    public ObjectGenerator() {
        addValueGenerator(new UtilDateGenerator());
        addValueGenerator(new StringGenerator());
        addValueGenerator(new NumberGenerator());
        addValueGenerator(new EnumGenerator());

        // TODO: i suppose this is caused by initializing all the fields
        // consider replacing field initilization by setters instead (think of different strategies)
        // or add @Exclude analogue for fields that should not be populated

        // objenesis workaround
        addValueGenerator(new EmptyHashSetGenerator());
    }

    /**
     * <p>Instantiates Object generator with generateInterfaceClasses flag,
     * which leads to creation of instances of the interface fields.</p>
     * <b>Note: may lead to errors, the better way is to use generators and/or populate entity fields</b>
     *
     * @param generateInterfaceClasses flag to be checked when you need to init interfaces
     */
    public ObjectGenerator(boolean generateInterfaceClasses) {
        this();
        this.generateInterfaceClasses = generateInterfaceClasses;
    }

    /**
     * <p>Instantiates Object generator with generators user passes to the constructor</p>
     *
     * @param valueGenerators - user-defined generators
     */
    public ObjectGenerator(ValueGenerator... valueGenerators) {
        this();
        for (ValueGenerator valueGenerator : valueGenerators) {
            addValueGenerator(valueGenerator);
        }
    }

    public ObjectGenerator(Map<String, ValueGenerator> userDefinedGenerators) {
        this();

    }

    public Map<String, ValueGenerator> getUserDefinedGenerators() {
        return userDefinedGenerators;
    }

    /**
     * Pass user-defined generators to ObjectGenerator. Key is class field name in CamelCase.
     *
     * @param userDefinedGenerators map of user-defined {@link ValueGenerator}.
     */
    public void setUserDefinedGenerators(Map<String, ValueGenerator> userDefinedGenerators) {
        this.userDefinedGenerators = userDefinedGenerators;
    }

    /**
     * <p>Instantiates Object generator with generateInterfaceClasses flag,
     * which leads to creation of instances of the interface fields and generators user passes to the constructor.</p>
     * <b>Note: generateInterfaceClasses flag may lead to errors, the better way is to use generators and/or populate
     * entity fields</b>
     *
     * @param generateInterfaceClasses flag to be checked when you need to init interfaces
     * @param valueGenerators          user-defined generators
     */
    public ObjectGenerator(boolean generateInterfaceClasses, ValueGenerator... valueGenerators) {
        this(valueGenerators);
        this.generateInterfaceClasses = generateInterfaceClasses;
    }

    /**
     * <p>Creates an object using the given type via reflection operations
     * and populates all fields with random values.</p>
     * <p><b>Note: does NOT populate final and static fields.</b></p>
     * <p> Supported field types: </p>
     * <ol>
     * <li>All primitives (byte, short, int, long, float, double, boolean, char) and their boxed values</li>
     * <li>java.lang.BigDecimal, java.lang.BigInteger</li>
     * <li>java.util.Date</li>
     * <li>java.lang.String</li>
     * <li>Enums</li>
     * <li>Any object (recursively filled with random values)</li>
     * </ol>
     *
     * @param clazz type of the created object
     * @return {@link Object} - created object
     * @throws ReflectiveOperationException in case of any problems in reflection operations
     */
    public <T> T generateObject(Class<T> clazz) throws ReflectiveOperationException {
        T obj = objenesis.getInstantiatorOf(clazz).newInstance();
        Field[] declaredFields = clazz.getDeclaredFields();
        try {
            for (Field f : declaredFields) {
                Util.makeAccessible(f);
                if (!Modifier.isFinal(f.getModifiers()) && !Modifier.isStatic(f.getModifiers())) {
                    if (generatorContainer.canApplyUserDefinedGenerator(f)) {
                        f.set(obj, generatorContainer.getUserDefinedGenerators());
                    } else if (isAvailableForGeneration(f, obj)) {
                        f.set(obj, getObjectValue(f.getType()));
                    }
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new ReflectiveOperationException(e);
        }
        return obj;
    }

    private <T> Object getObjectValue(Class<T> clazz) throws ReflectiveOperationException {
        if (generatorContainer.canApplyDefaultGenerator(clazz))
            return generatorContainer.getValueGenerator(clazz).generateValue(clazz);
        return generateRandomValue(clazz);
    }

    /**
     * This method generates randomized values when value generator is absent for a specific class
     *
     * @param claz class of the value to be generated
     * @return generated value
     * @throws ReflectiveOperationException in case of any internal errors
     */
    private <T> Object generateRandomValue(Class<T> claz) throws ReflectiveOperationException {
        // arrays
        if (claz.isArray()) {
            Object arr = Array.newInstance(claz.getComponentType(), 1);
            Array.set(arr, 0, generateRandomValue(claz.getComponentType()));
            return arr;
        }
        // primitives
        else if (claz.isPrimitive()) {
            return getRandomPrimitiveValue(claz);
        }
        // collections
        else if (Util.isContainer(claz)) {
            return getContainerImplementation(claz);
        }
        // other objects
        else {
            return generateObject(claz);
        }
    }

    /**
     * Returns random primitive value
     *
     * @param claz class of the value to be generated
     * @return random primitive value
     */
    private Object getRandomPrimitiveValue(Class<?> claz) {
        Random random = new Random(new Date().getTime());
        if (claz.equals(Byte.TYPE)) {
            return (byte) random.nextInt(Byte.MAX_VALUE + 1);
        } else if (claz.equals(Short.TYPE)) {
            return (short) random.nextInt(Short.MAX_VALUE + 1);
        } else if (claz.equals(Integer.TYPE)) {
            return random.nextInt();
        } else if (claz.equals(Long.TYPE)) {
            return random.nextLong();
        } else if (claz.equals(Float.TYPE)) {
            return random.nextFloat();
        } else if (claz.equals(Double.TYPE)) {
            return random.nextDouble();
        } else if (claz.equals(Boolean.TYPE)) {
            return random.nextBoolean();
        } else {
            return (char) random.nextInt(Character.MAX_VALUE + 1);
        }
    }

    /**
     * Generates container using objenesis for classes and hardcoded logic for interfaces.
     * Can be overriden by value generators
     *
     * @param claz type of the container
     * @return created collection
     */
    private <T> Object getContainerImplementation(Class<T> claz) {
        if (!claz.isInterface() || !generateInterfaceClasses) {
            return objenesis.getInstantiatorOf(claz).newInstance();
        }
        System.out.println(String.format("[WARNING] Generating entity from interface <%s>, " +
                "this feature may lead to errors.", claz.getName()));
        if (Map.class.isAssignableFrom(claz)) {
            return new HashMap();
        } else if (List.class.isAssignableFrom(claz)) {
            return new ArrayList();
        } else if (Set.class.isAssignableFrom(claz)) {
            return new HashSet();
        } else {
            return new ArrayDeque();
        }
    }

    /**
     * Checks whether entity can be generated.
     *
     * @param f - field of the object which is being checked
     * @param o - main object
     * @return true if the value can be generated
     * @throws IllegalAccessException in case of internal errors
     */
    private boolean isAvailableForGeneration(Field f, Object o) throws IllegalAccessException {
        Class<?> claz = f.getType();
        // check if primitive
        if (claz.isPrimitive()) return true;
        // check if array
        if (claz.isArray()) return true;
        // check if there is a value so we should not add anything in there
        if (f.get(o) != null) return false;
        // check if we got a generator for this key
        if (generatorContainer.canApplyDefaultGenerator(f)) return true;
        // if it is an interface, check if we got interface generation permission
        if (claz.isInterface() && generateInterfaceClasses) return true;
        // at last, try to generate it via objenesis API
        // if not generated, then this object cannot be initialized
        try {
            return objenesis.getInstantiatorOf(claz).newInstance() != null;
        } catch (InstantiationError error) { // the only way to do it
            System.out.println("[WARNING] Object is not initialized: " + claz.getName() +
                    ". You should initialize it before using the object.");
            return false;
        }
    }

}
