package com.adinho.pdf_reader.pdf_reader.core.kernel.ddd;


import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.util.Objects;
import java.util.UUID;

public abstract class Identify<T> implements ValueObject {
    private T value;

    public Identify(T value) {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        Class<?> paramter = (Class<?>) parameterizedType.getActualTypeArguments()[0];
        //Validacao para caso o tipo do ID seja String
        if (paramter.isAssignableFrom(String.class)) {
            String strValue = (String) value;
            if (value == null || strValue.trim().isEmpty()) {
                throw new IllegalArgumentException("ID invalido");
            }
        }
        this.value = value;
    }

    static String gerarProximoUUID() {
        return UUID.randomUUID().toString();
    }

    public T getValue() {
        return value;
    }

    public static <C extends Identify> C criar(Class<C> clazz) {
        try {

            ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericSuperclass();
            Class<?> paramter = (Class<?>) parameterizedType.getActualTypeArguments()[0];
            Constructor<C> constructor = clazz.getConstructor(paramter);

            if (paramter.isAssignableFrom(String.class)) {
                return constructor.newInstance(gerarProximoUUID());
            }

            throw new IllegalArgumentException("Tipo de ID nao suportado para criacao automatica");
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }

    }

    public static <V, C extends Identify> C pegar(Class<C> clazz, V value) {
        try {
            Constructor<C> constructor = clazz.getConstructor(value.getClass());
            return constructor.newInstance(value);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Identify<?> identify = (Identify<?>) o;
        return Objects.equals(value, identify.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}