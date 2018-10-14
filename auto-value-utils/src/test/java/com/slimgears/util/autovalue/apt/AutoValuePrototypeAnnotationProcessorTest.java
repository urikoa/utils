package com.slimgears.util.autovalue.apt;

import com.google.auto.value.processor.AutoValueProcessor;
import com.slimgears.apt.util.AnnotationProcessingTester;
import org.junit.Test;
import org.slf4j.event.Level;

public class AutoValuePrototypeAnnotationProcessorTest {
    @Test
    public void testBasicPrototype() {
        tester()
                .inputFiles(
                        "SampleFieldAnnotation.java",
                        "SampleValuePrototype.java")
                .expectedSources(
                        "SampleValue.java",
                        "SampleValuePrototypeBuilder.java")
                .test();
    }

    @Test
    public void testGenericPrototype() {
        tester()
                .inputFiles(
                        "SampleInterface.java",
                        "SampleGenericInterface.java",
                        "SampleGenericPrototype.java")
                .expectedSources(
                        "SampleInterfaceBuilder.java",
                        "SampleGenericInterfaceBuilder.java",
                        "SampleGeneric.java")
                .test();
    }

    @Test
    public void testComparableGenericPrototype() {
        tester()
                .inputFiles(
                        "SampleComparableGenericInterface.java",
                        "SampleComparableGenericPrototype.java")
                .expectedSources(
                        "SampleComparableGeneric.java",
                        "SampleComparableGenericPrototypeBuilder.java")
                .test();
    }

    @Test
    public void testGenericFieldPrototype() {
        tester()
                .inputFiles("SampleGenericFieldPrototype.java")
                .expectedSources(
                        "SampleGenericFieldPrototypeBuilder.java",
                        "SampleGenericField.java")
                .test();
    }

    @Test
    public void testPrototypeReferences() {
        tester()
                .inputFiles("SampleAPrototype.java", "SampleBPrototype.java")
                .expectedSources("SampleBPrototypeBuilder.java")
                .test();
    }

    private AnnotationProcessingTester tester() {
        return AnnotationProcessingTester.create()
                .verbosity(Level.TRACE)
                .processedWith(new AutoValuePrototypeAnnotationProcessor(), new AutoValueProcessor());
    }
}
