package org.yamler.yamler;

import org.yamler.yamler.Converter.Converter;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class MapConfigMapper extends YamlConfigMapper {
    public Map<String, Object> saveToMap(Class clazz) throws Exception {
        Map<String, Object> returnMap = new HashMap<>();

        if (!clazz.getSuperclass().equals(Config.class) && !clazz.getSuperclass().equals(Object.class)) {
            Map<String, Object> map = saveToMap(clazz.getSuperclass());
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                returnMap.put( entry.getKey(), entry.getValue() );
            }
        }

        for (Field field : clazz.getDeclaredFields()) {
            if (doSkip(field)) continue;

            String path = (CONFIG_MODE.equals(ConfigMode.DEFAULT)) ? field.getName().replaceAll("_", ".") : field.getName();

            if (field.isAnnotationPresent(Path.class)) {
                Path path1 = field.getAnnotation(Path.class);
                path = path1.value();
            }

            if (Modifier.isPrivate(field.getModifiers())) {
                field.setAccessible(true);
            }

            try {
                returnMap.put(path, field.get(this));
            } catch (IllegalAccessException e) { }
        }

        Converter mapConverter = converter.getConverter(Map.class);
        return (Map<String, Object>) mapConverter.toConfig(HashMap.class, returnMap, null);
    }

    public void loadFromMap(Map section, Class clazz) throws Exception {
        if (!clazz.getSuperclass().equals(Config.class) && !clazz.getSuperclass().equals(Config.class)) {
            loadFromMap(section, clazz.getSuperclass());
        }

        for (Field field : clazz.getDeclaredFields()) {
            if (doSkip(field)) continue;

            String path = (CONFIG_MODE.equals(ConfigMode.DEFAULT)) ? field.getName().replaceAll("_", ".") : field.getName();

            if (field.isAnnotationPresent(Path.class)) {
                Path path1 = field.getAnnotation(Path.class);
                path = path1.value();
            }

            if(Modifier.isPrivate(field.getModifiers())) {
                field.setAccessible(true);
            }

            converter.fromConfig((Config) this, field, ConfigSection.convertFromMap(section), path);
        }
    }
}