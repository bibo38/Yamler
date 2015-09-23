package org.yamler.yamler.Converter;

import org.yamler.yamler.ConfigSection;
import org.yamler.yamler.InternalConverter;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class Config implements Converter {
    private InternalConverter internalConverter;

    public Config(InternalConverter internalConverter) {
        this.internalConverter = internalConverter;
    }

    @Override
    public Object toConfig(Class<?> type, Object obj, ParameterizedType parameterizedType) throws Exception {
        return (obj instanceof Map) ? obj : ((org.yamler.yamler.Config) obj).saveToMap( obj.getClass() );
    }

    @Override
    public Object fromConfig(Class type, Object section, ParameterizedType genericType) throws Exception {
        org.yamler.yamler.Config obj = (org.yamler.yamler.Config) newInstance(type);

        // Inject Converter stack into subconfig
        for (Class aClass : internalConverter.getCustomConverters()) {
            obj.addConverter(aClass);
        }

        obj.loadFromMap((section instanceof Map) ? (Map) section : ((ConfigSection) section).getRawMap(), type);
        return obj;
    }
    
    // recursively handles enclosed classes
    public Object newInstance(Class type) throws Exception {
        Class enclosingClass = type.getEnclosingClass();
        if (enclosingClass != null) {
            Object instanceOfEnclosingClass = newInstance(enclosingClass);
            return type.getConstructor(enclosingClass).newInstance(instanceOfEnclosingClass);
        } else {
            return type.newInstance();
        }
    }

    @Override
    public boolean supports(Class<?> type) {
        return org.yamler.yamler.Config.class.isAssignableFrom(type);
    }
}
