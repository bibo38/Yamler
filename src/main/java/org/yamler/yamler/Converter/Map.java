package org.yamler.yamler.Converter;

import org.yamler.yamler.ConfigSection;
import org.yamler.yamler.GenericData;
import org.yamler.yamler.InternalConverter;

import java.util.HashMap;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class Map implements Converter {
    private InternalConverter internalConverter;

    public Map(InternalConverter internalConverter) {
        this.internalConverter = internalConverter;
    }

    @Override
    public Object toConfig(Class<?> type, Object obj) throws Exception {
        java.util.Map<Object, Object> map1 = (java.util.Map) obj;

        for (java.util.Map.Entry<Object, Object> entry : map1.entrySet()) {
            if (entry.getValue() == null) continue;

            Class clazz = entry.getValue().getClass();

            Converter converter = internalConverter.getConverter(clazz);
            map1.put(entry.getKey(), (converter != null) ? converter.toConfig(clazz, entry.getValue()) : entry.getValue());
        }

        return map1;
    }

    @Override
    public Object fromConfig(Class type, Object section, GenericData genericData) throws Exception {
        Class<?> keyClass = genericData.getType(type.getTypeParameters()[0]);
        if (keyClass != null) {

            java.util.Map map;
            try {
                map = ((java.util.Map) type.newInstance());
            } catch (InstantiationException e) {
                map = new HashMap();
            }

            if ( section == null )
                section = new HashMap<>();

            java.util.Map<?, ?> map1 = (section instanceof java.util.Map) ? (java.util.Map) section : ((ConfigSection) section).getRawMap();
            for (java.util.Map.Entry<?, ?> entry : map1.entrySet()) {
                Object key;

                if (keyClass.equals(Integer.class) && !(entry.getKey() instanceof Integer)) {
                    key = Integer.valueOf((String) entry.getKey());
                } else if (keyClass.equals(Short.class) && !(entry.getKey() instanceof Short)) {
                    key = Short.valueOf((String) entry.getKey());
                } else if (keyClass.equals(Byte.class) && !(entry.getKey() instanceof Byte)) {
                    key = Byte.valueOf((String) entry.getKey());
                } else if (keyClass.equals(Float.class) && !(entry.getKey() instanceof Float)) {
                    key = Float.valueOf((String) entry.getKey());
                } else if (keyClass.equals(Double.class) && !(entry.getKey() instanceof Double)) {
                    key = Double.valueOf((String) entry.getKey());
                } else {
                    key = entry.getKey();
                }

                Class<?> valueClass = genericData.getType(type.getTypeParameters()[1]);

                Converter converter = internalConverter.getConverter(valueClass);
                map.put(key, (converter != null) ? converter.fromConfig(valueClass, entry.getValue(), genericData) : entry.getValue());
            }

            return map;
        } else {
            return section;
        }
    }

    @Override
    public boolean supports(Class<?> type) {
        return java.util.Map.class.isAssignableFrom(type);
    }
}
