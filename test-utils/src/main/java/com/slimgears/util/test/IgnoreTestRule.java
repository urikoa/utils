package com.slimgears.util.test;

import org.junit.runners.model.FrameworkMethod;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface IgnoreTestRule<A extends Annotation> {
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.ANNOTATION_TYPE)
    @interface Qualifier {
        Class<? extends IgnoreTestRule<? extends Annotation>> value();
    }

    boolean isIgnored(A annotation, FrameworkMethod method);
}
