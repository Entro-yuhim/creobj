package org.oast.creobj.generator;

import java.util.Date;
import java.util.Random;

public class EnumGenerator extends CommonValueGenerator<Enum> {

    @Override
    public boolean isAssignable() {
        return true;
    }

    @Override
    public Class<Enum> getValueClass() {
        return Enum.class;
    }

    @Override
    public Enum generateValue(Class<?> claz) {
        Random random = new Random(new Date().getTime());
        if (Enum.class.isAssignableFrom(claz)) {
            Object[] enumValues = claz.getEnumConstants();
            return (Enum) enumValues[random.nextInt(enumValues.length)];
        }
        throw new IllegalArgumentException(getIllegalClassAlert(claz));
    }
}
