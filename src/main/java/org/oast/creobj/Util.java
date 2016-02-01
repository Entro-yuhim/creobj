package org.oast.creobj;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

class Util {

    /**
     * If needed, changes modifier of the field to public
     * @param f - field to be accessed
     */
    static void makeAccessible(Field f) {
        if (!f.isAccessible()) f.setAccessible(true);
    }

    /**
     * Returns true if class is boxed primitive
     * @param claz - class to be checked
     * @return result of the check
     */
    public static boolean isBoxedPrimitive(Class<?> claz) {
        return claz.equals(Byte.class) || claz.equals(Short.class) || claz.equals(Integer.class) ||
                claz.equals(Long.class) || claz.equals(Float.class) || claz.equals(Double.class) ||
                claz.equals(Boolean.class) || claz.equals(Character.class);
    }

    static boolean isContainer(Class<?> claz) {
        return Collection.class.isAssignableFrom(claz) || Map.class.isAssignableFrom(claz);
    }

    /**
     * <p>If the value p being boxed is true, false, a byte, or a char in the range \u0000 to \u007f,
     * or an int or short number between -128 and 127 (inclusive), then let r1 and r2 be the results of
     * any two boxing conversions of p. It is always the case that r1 == r2.</p>
     *
     * @see <a href='http://docs.oracle.com/javase/specs/jls/se7/html/jls-5.html#jls-5.1.7'>JLS specs</a>
     */
    static boolean isPoolCachedType(Object val1) {
        Class<?> claz = val1.getClass();
        return claz.equals(String.class) || claz.equals(Boolean.class) ||
                ((claz.equals(Integer.class) || claz.equals(Short.class))
                        && ((Number) val1).intValue() >= -128 && ((Number) val1).intValue() <= 127) ||
                (claz.equals(Byte.class) || claz.equals(Character.class)
                        && (char) val1 >= '\u0000' && (char) val1 <= '\u007f');
    }

}
