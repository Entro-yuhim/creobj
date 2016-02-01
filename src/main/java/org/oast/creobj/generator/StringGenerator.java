package org.oast.creobj.generator;

import java.util.UUID;

public class StringGenerator extends CommonValueGenerator<String> {

    @Override
    public Class<String> getValueClass() {
        return String.class;
    }

    @Override
    public String generateValue(Class<?> claz) {
        if (claz.equals(String.class)) {
            return UUID.randomUUID().toString();
        }
        throw new IllegalArgumentException(getIllegalClassAlert(claz));
    }

}
