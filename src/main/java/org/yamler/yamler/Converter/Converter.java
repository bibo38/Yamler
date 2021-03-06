package org.yamler.yamler.Converter;

import org.yamler.yamler.GenericData;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public interface Converter {
    /**
     * This method gets called on save. It gets the Fields Type and the object the Config wants to save into it. This
     * is needed to pretty print INTO the config.
     *
     * @param type The type (Class) of the Field
     * @param obj The object which is stored in the Config Object
     * @return An Object (mostly a Map or a List)
     * @throws Exception Some generic exception when something went wrong. This gets caught by the Converter
     */
    public Object toConfig(Class<?> type, Object obj) throws Exception;

    /**
     * This method gets called when we want to load something out of the File. You get that what you give into the Config
     * via toConfig as Object passed. The type is the Destination Field Type which this Object should be layed in.
     *
     * @param type The type (Class) of the Field
     * @param obj The Object from toConfig
     * @param genericData A mapping class, which can be used to map generic TypeVariables to Classes
     * @return The correct Object which can be hold by the Field
     * @throws Exception Some generic exception when something went wrong. This gets caught by the Converter
     */
    public Object fromConfig(Class<?> type, Object obj, GenericData genericData) throws Exception;

    /**
     * This checks if this Converter can convert the given Class
     *
     * @param type The type (Class) of the Field to check
     * @return true if this can convert that otherwise false
     */
    public boolean supports(Class<?> type);
}
