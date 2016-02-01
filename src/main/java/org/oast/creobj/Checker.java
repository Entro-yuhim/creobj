package org.oast.creobj;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.lang.reflect.Field;

public class Checker {

    // TODO: 12/28/2015 write javadoc
    public static boolean checkObjectCopy(Object o1, Object o2) throws Exception {
        Class<?> claz = o1.getClass();
        Field[] declaredFields = claz.getDeclaredFields();
        try {
            for (Field f : declaredFields) {
                Field declaredField = claz.getDeclaredField(f.getName());
                Util.makeAccessible(declaredField);
                Object val1 = declaredField.get(o1);
                Object val2 = declaredField.get(o2);

                if (isNulls(val1, val2)) {
                    throw new AssertionError(String.format("Field <%s %s> is not initialized properly. " +
                            "Check the assignments.", f.getType().getName(), f.getName()));
                }
                if (isPointingOnTheSameObject(val1, val2)) {
                    // TODO: 12/25/2015 add message
                    throw new AssertionError("Cloning does not work good, as we got two different pointers on the same object."
                            + System.lineSeparator() + ToStringBuilder.reflectionToString(val1));
                }
                if (isEqual(val1, val2)) {
                    // TODO: 12/25/2015 add message
                    throw new AssertionError(val1 + " != " + val2);
                }
                if (isEqualContainers(val1, val2)) {
                    // TODO: 12/28/2015 add message
                    throw new AssertionError(val1 + " != " + val2);
                }

            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new Exception(e);
        }

        return true;
    }

    private static boolean isNulls(Object val1, Object val2) {
        return val1 == null || val2 == null;
    }

    private static boolean isEqualContainers(Object val1, Object val2) {
        return Util.isContainer(val1.getClass()) && !val1.equals(val2);
    }

    private static boolean isEqual(Object val1, Object val2) {
        return !Util.isContainer(val1.getClass()) && !EqualsBuilder.reflectionEquals(val1, val2);
    }

    private static boolean isPointingOnTheSameObject(Object val1, Object val2) {
        return !val1.getClass().isEnum() && !val1.getClass().isPrimitive() && !Util.isPoolCachedType(val1) && val1 == val2;
    }

}
