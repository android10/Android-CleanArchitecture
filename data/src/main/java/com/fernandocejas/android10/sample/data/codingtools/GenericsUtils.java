package com.fernandocejas.android10.sample.data.codingtools;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author goraczka
 */
public class GenericsUtils {

    public static <T> List<Class<?>> getTypeArguments(Class<T> baseClass, Class<? extends T> childClass) {

        Map<Type, Type> resolvedTypes = new HashMap<>();
        Type type = childClass;
        while (!getClass(type).equals(baseClass)) {
            if (type instanceof Class) {
                type = ((Class) type).getGenericSuperclass();
            } else {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Class<?> rawType = (Class) parameterizedType.getRawType();
                Type[] actualTypeArguments = parameterizedType
                        .getActualTypeArguments();
                TypeVariable<?>[] typeParameters = rawType.getTypeParameters();
                for (int i = 0; i < actualTypeArguments.length; i++) {
                    resolvedTypes.put(typeParameters[i], actualTypeArguments[i]);
                }
                if (!rawType.equals(baseClass)) {
                    type = rawType.getGenericSuperclass();
                }
            }
        }

        Type[] actualTypeArguments;
        if (type instanceof Class) {
            actualTypeArguments = ((Class) type).getTypeParameters();
        } else {
            actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
        }
        List<Class<?>> typeArgumentsAsClasses = new ArrayList<>();

        for (Type baseType : actualTypeArguments) {
            while (resolvedTypes.containsKey(baseType)) {
                baseType = resolvedTypes.get(baseType);
            }
            typeArgumentsAsClasses.add(getClass(baseType));
        }

        return typeArgumentsAsClasses;
    }

    private static Class<?> getClass(Type type) {

        if (type instanceof Class) {
            return (Class) type;
        } else if (type instanceof ParameterizedType) {
            return getClass(((ParameterizedType) type).getRawType());
        } else if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            Class<?> componentClass = getClass(componentType);
            if (componentClass != null) {
                return Array.newInstance(componentClass, 0).getClass();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}
