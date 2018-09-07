package com.slimgears.util.reflect;

import com.slimgears.util.reflect.internal.CanonicalGenericArrayType;
import com.slimgears.util.reflect.internal.CanonicalParameterizedType;
import com.slimgears.util.reflect.internal.Require;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TypeToken<T> {
    private final Type type;

    protected TypeToken() {
        this.type = Optional
                .ofNullable(getClass().getGenericSuperclass())
                .filter(ParameterizedType.class::isInstance)
                .map(ParameterizedType.class::cast)
                .map(pt -> pt.getActualTypeArguments()[0])
                .map(CanonicalType::of)
                .orElseGet(() -> getClass().getSuperclass());
    }

    public Type type() {
        return this.type;
    }

    private TypeToken(Type type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof TypeToken) && type.equals(((TypeToken)obj).type);
    }

    @Override
    public String toString() {
        return type.getTypeName();
    }

    public static <T> TypeToken<T[]> ofArray(Class<T> componentType) {
        return new TypeToken<>(new CanonicalGenericArrayType(CanonicalType.of(componentType)));
    }

    public static <T> TypeToken<T[]> ofArray(TypeToken<T> componentToken) {
        return new TypeToken<>(new CanonicalGenericArrayType(componentToken.type()));
    }

    public static <T> TypeToken<T> of(Class<T> cls) {
        return new TypeToken<>(CanonicalType.of(cls));
    }

    public static <T> TypeToken<T> ofParameterized(Class rawClass, Class... args) {
        return parameterizedBuilder(rawClass).typeArgs(args).build();
    }

    public static <T> TypeToken<T> ofParameterized(Class rawClass, TypeToken<?>... args) {
        return parameterizedBuilder(rawClass).typeArgs(args).build();
    }

    public static ParameterizedBuilder parameterizedBuilder(Class rawClass) {
        return new ParameterizedBuilder(rawClass);
    }

    public static class ParameterizedBuilder {
        private final Class rawClass;
        private final List<Type> params = new ArrayList<>();

        public ParameterizedBuilder(Class rawClass) {
            this.rawClass = rawClass;
        }

        public ParameterizedBuilder typeArgs(Type... types) {
            Arrays.stream(types)
                    .map(CanonicalType::of)
                    .forEach(params::add);
            return this;
        }

        public ParameterizedBuilder typeArgs(TypeToken<?>... tokens) {
            Arrays.stream(tokens)
                    .map(token -> token.type)
                    .map(CanonicalType::of)
                    .forEach(params::add);
            return this;
        }

        public <T> TypeToken<T> build() {
            Require.argument(
                    rawClass.getTypeParameters().length == params.size(),
                    String.format("Type parameter number mismatch (expected: %d, actual: %d)",
                            rawClass.getTypeParameters().length,
                            params.size()));

            Type[] args = params.toArray(new Type[0]);
            return new TypeToken<>(new CanonicalParameterizedType(rawClass, rawClass.getEnclosingClass(), params.toArray(args)));
        }
    }
}
