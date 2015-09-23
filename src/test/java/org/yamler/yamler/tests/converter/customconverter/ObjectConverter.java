package org.yamler.yamler.tests.converter.customconverter;

import org.yamler.yamler.Converter.Converter;
import org.yamler.yamler.InternalConverter;

import java.lang.reflect.ParameterizedType;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class ObjectConverter implements Converter {
    public ObjectConverter(InternalConverter internalConverter) {

    }

    @Override
    public Object toConfig(Class<?> type, Object obj, ParameterizedType parameterizedType) throws Exception {
        return obj;
    }

    @Override
    public Object fromConfig(Class<?> type, Object obj, ParameterizedType parameterizedType) throws Exception {
        return obj;
    }

    @Override
    public boolean supports(Class<?> type) {
        return true;
    }
}
