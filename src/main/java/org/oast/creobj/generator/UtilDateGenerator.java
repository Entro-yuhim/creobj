package org.oast.creobj.generator;

import java.util.Date;

public class UtilDateGenerator extends CommonValueGenerator<Date> {
    @Override
    public Class<Date> getValueClass() {
        return Date.class;
    }

    @Override
    public Date generateValue(Class<?> claz) {
        if (Date.class.isAssignableFrom(claz)) {
            return new Date();
        }
        throw new IllegalArgumentException(getIllegalClassAlert(claz));
    }
}
