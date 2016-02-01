package org.oast.creobj.generator;

import java.util.Date;
import java.util.Random;

public class NumberGenerator extends CommonValueGenerator<Number> {

    @Override
    public boolean isAssignable() {
        return true;
    }

    @Override
    public Class<Number> getValueClass() {
        return Number.class;
    }

    // all boxed values, including BigDecimal, BigInteger, excluding Character and Boolean
    @Override
    public Number generateValue(Class<?> claz) {
        Random random = new Random(new Date().getTime());
        if (Number.class.isAssignableFrom(claz)) {
            return random.nextInt(Byte.MAX_VALUE) + 1;
        }
        throw new IllegalArgumentException(getIllegalClassAlert(claz));
    }

}
