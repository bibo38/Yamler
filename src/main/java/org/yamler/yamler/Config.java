package org.yamler.yamler;

import org.yamler.yamler.Converter.Converter;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class Config extends YamlConfigMapper implements IConfig {
    public Config() {

    }

    public Config(String filename, String ... header) {
        CONFIG_FILE = new File(filename + (filename.endsWith(".yml") ? "" : ".yml"));
        CONFIG_HEADER = header;
    }

    @Override
    public void save() throws InvalidConfigurationException {
        if (CONFIG_FILE == null) {
            throw new IllegalArgumentException("Saving a config without given File");
        }

        clearComments();

        try
        {
            root = ConfigSection.convertFromMap(saveToMap(getClass()));
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        saveToYaml();
    }

    @Override
    public void save(File file) throws InvalidConfigurationException {
        if (file == null) {
            throw new IllegalArgumentException("File argument can not be null");
        }

        CONFIG_FILE = file;
        save();
    }

    @Override
    public void init() throws InvalidConfigurationException {
        if (!CONFIG_FILE.exists()) {
            if (CONFIG_FILE.getParentFile() != null)
                CONFIG_FILE.getParentFile().mkdirs();

            try {
                CONFIG_FILE.createNewFile();
                save();
            } catch (IOException e) {
                throw new InvalidConfigurationException("Could not create new empty Config", e);
            }
        } else {
            load();
        }
    }

    @Override
    public void init(File file) throws InvalidConfigurationException {
        if (file == null) {
            throw new IllegalArgumentException("File argument can not be null");
        }

        CONFIG_FILE = file;
        init();
    }

    @Override
    public void load() throws InvalidConfigurationException {
        if (CONFIG_FILE == null) {
            throw new IllegalArgumentException("Loading a config without given File");
        }

        loadFromYaml();
        update(root);
        try
        {
            loadFromMap(root.getRawMap(), getClass(), new GenericData());
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }

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
        return (Map<String, Object>) mapConverter.toConfig(HashMap.class, returnMap);
    }

    public void loadFromMap(Map section, Class clazz, GenericData genericData) throws Exception {
        if (!clazz.getSuperclass().equals(Config.class) && !clazz.getSuperclass().equals(Config.class)) {
            loadFromMap(section, clazz.getSuperclass(), genericData); // TODO Mapping parameterized type
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

            converter.fromConfig(this, field, ConfigSection.convertFromMap(section), path, genericData);
        }
    }

    @Override
    public void load(File file) throws InvalidConfigurationException {
        if (file == null) {
            throw new IllegalArgumentException("File argument can not be null");
        }

        CONFIG_FILE = file;
        load();
    }
}
