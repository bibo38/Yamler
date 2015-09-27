package org.yamler.yamler.Converter;

import org.yamler.yamler.GenericData;
import org.yamler.yamler.InternalConverter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Set implements Converter {
    private InternalConverter internalConverter;

    public Set(InternalConverter internalConverter) {
        this.internalConverter = internalConverter;
    }

    @Override
    public Object toConfig(Class<?> type, Object obj) throws Exception {
        java.util.Set<Object> values = (java.util.Set<Object>) obj;
        java.util.List newList = new ArrayList();

        Iterator<Object> iterator = values.iterator();
        while(iterator.hasNext()) {
            Object val = iterator.next();

            Converter converter = internalConverter.getConverter(val.getClass());

            if (converter != null)
                newList.add(converter.toConfig(val.getClass(), val));
            else
                newList.add(val);
        }

        return newList;
    }

    @Override
    public Object fromConfig(Class type, Object section, GenericData genericData) throws Exception {
        java.util.List<Object> values = (java.util.List<Object>) section;
        java.util.Set<Object> newList = new HashSet<>();

        try {
            newList = (java.util.Set<Object>) type.newInstance();
        } catch (Exception e) { }

        Class<?> setClass = genericData.getType(type.getTypeParameters()[0]);
        if (setClass != null) {
            Converter converter = internalConverter.getConverter(setClass);

            if (converter != null) {
                for ( int i = 0; i < values.size(); i++ ) {
                    newList.add( converter.fromConfig( setClass, values.get( i ), genericData ) );
                }
            } else {
                newList.addAll( values );
            }
        } else {
            newList.addAll( values );
        }

        return newList;
    }

    @Override
    public boolean supports(Class<?> type) {
        return java.util.Set.class.isAssignableFrom(type);
    }

}
