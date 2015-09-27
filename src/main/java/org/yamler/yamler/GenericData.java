package org.yamler.yamler;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bibo38
 */
public class GenericData
{
	protected Map<TypeVariable<?>, Class<?>> connection = new HashMap<>();

	public void addField(Field f)
	{
		Type type = f.getGenericType();
		if(!(type instanceof ParameterizedType))
			return;

		Class<?> cl = f.getType();
		TypeVariable<?> types[] = cl.getTypeParameters();
		Type actArguments[] = ((ParameterizedType) type).getActualTypeArguments();
		for(int i = 0; i < types.length; i++)
			connection.put(types[i], getType(actArguments[i]));
	}

	public Class<?> getType(Type t)
	{
		if(t instanceof Class<?>)
			return (Class<?>) t;
		else
			return connection.get(t);
	}

	public void removeClass(Class<?> cl)
	{
		TypeVariable<?> types[] = cl.getTypeParameters();
		for(TypeVariable<?> var : types)
			connection.remove(var);
	}
}
