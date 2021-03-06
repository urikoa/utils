package com.slimgears.sample;

import com.google.auto.value.AutoValue;
import com.google.common.reflect.TypeToken;
import com.slimgears.util.autovalue.annotations.BuilderPrototype;
import com.slimgears.util.autovalue.annotations.HasMetaClass;
import com.slimgears.util.autovalue.annotations.MetaBuilder;
import com.slimgears.util.autovalue.annotations.MetaClass;
import com.slimgears.util.autovalue.annotations.PropertyMeta;
import com.slimgears.util.autovalue.annotations.PropertyType;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Generated;

@Generated("com.slimgears.util.autovalue.apt.AutoValuePrototypeAnnotationProcessor")
@AutoValue
public abstract class SampleCustomBuilderValue implements SampleCustomBuilderValuePrototype, HasMetaClass<SampleCustomBuilderValue> {

    @Override
    
    public MetaClass<SampleCustomBuilderValue> metaClass() {
        return metaClass;
    }

    public static final Meta metaClass = new Meta();
    public static class Meta implements MetaClass<SampleCustomBuilderValue> {
        private final TypeToken<SampleCustomBuilderValue> objectType = new TypeToken<SampleCustomBuilderValue>(){};
        private final TypeToken<Builder> builderClass = new TypeToken<Builder>(){};
        private final Map<String, PropertyMeta<SampleCustomBuilderValue, ?>> propertyMap = new LinkedHashMap<>();

        public final PropertyMeta<SampleCustomBuilderValue, Integer> intValue = PropertyMeta.<SampleCustomBuilderValue, Integer, Builder>create(this, "intValue", new PropertyType<Integer>(){}, obj -> obj.intValue(), Builder::intValue);
        public final PropertyMeta<SampleCustomBuilderValue, Double> doubleValue = PropertyMeta.<SampleCustomBuilderValue, Double, Builder>create(this, "doubleValue", new PropertyType<Double>(){}, obj -> obj.doubleValue(), Builder::doubleValue);

        Meta() {
            propertyMap.put("intValue", intValue);
            propertyMap.put("doubleValue", doubleValue);
        }

        @Override
        public TypeToken<Builder> builderClass() {
            return this.builderClass;
        }

        @Override
        public TypeToken<SampleCustomBuilderValue> asType() {
            return this.objectType;
        }

        @Override
        public Iterable<PropertyMeta<SampleCustomBuilderValue, ?>> properties() {
            return propertyMap.values();
        }

        @Override
        @SuppressWarnings("unchecked")
        public <__V> PropertyMeta<SampleCustomBuilderValue, __V> getProperty(String name) {
            return (PropertyMeta<SampleCustomBuilderValue, __V>)propertyMap.get(name);
        }

        @Override
        public <B extends MetaBuilder<SampleCustomBuilderValue>> B createBuilder() {
            return (B)(BuilderPrototype)SampleCustomBuilderValue.builder();
        }

        @Override
        public int hashCode() {
            return Objects.hash(objectType, builderClass);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Meta
            && Objects.equals(((Meta)obj).asType(), asType())
            && Objects.equals(((Meta)obj).builderClass(), builderClass());
        }
    }

    public static SampleCustomBuilderValue create(
         int intValue,
         double doubleValue) {
        return SampleCustomBuilderValue.builder()
            .intValue(intValue)
            .doubleValue(doubleValue)
            .build();
    }

    public abstract Builder toBuilder();

    public static Builder builder() {
        return Builder.create();
    }

    @AutoValue.Builder
    public interface Builder extends BuilderPrototype<SampleCustomBuilderValue, Builder>, SampleCustomBuilderValuePrototypeBuilder<Builder>{
        public static Builder create() {
            return new AutoValue_SampleCustomBuilderValue.Builder();
        }

        @Override
        Builder intValue(int intValue);

        @Override
        Builder doubleValue(double doubleValue);
    }

    @Override public abstract int intValue();
    @Override public abstract double doubleValue();

}
