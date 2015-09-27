package org.yamler.yamler.Converter;

import org.yamler.yamler.GenericData;
import org.yamler.yamler.InternalConverter;

import java.util.ArrayList;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class List implements Converter {
    private InternalConverter internalConverter;

    public List(InternalConverter internalConverter) {
        this.internalConverter = internalConverter;
    }

    @Override
    public Object toConfig(Class<?> type, Object obj) throws Exception {
        java.util.List values = (java.util.List) obj;
        java.util.List newList = new ArrayList();

        for (Object val : values) {
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
        java.util.List newList = new ArrayList();
        try {
            newList = ((java.util.List) type.newInstance());
        } catch (Exception e) {
        }

        java.util.List values = (java.util.List) section;

        Class<?> listType = genericData.getType(type.getTypeParameters()[0]);
        if (listType != null) {
            Converter converter = internalConverter.getConverter(listType);

            if (converter != null) {
                for ( int i = 0; i < values.size(); i++ ) {
                    newList.add( converter.fromConfig(listType, values.get( i ), genericData ) );
                }
            } else {
                newList = values;
            }
        } else {
            newList = values;
        }

        return newList;
    }

    @Override
    public boolean supports(Class<?> type) {
        return java.util.List.class.isAssignableFrom(type);
    }
}
