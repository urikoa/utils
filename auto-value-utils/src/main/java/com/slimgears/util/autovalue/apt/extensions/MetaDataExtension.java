package com.slimgears.util.autovalue.apt.extensions;

import com.google.auto.service.AutoService;
import com.slimgears.apt.data.TypeInfo;
import com.slimgears.util.autovalue.annotations.HasMetaClass;
import com.slimgears.util.autovalue.annotations.HasMetaClassWithKey;
import com.slimgears.util.autovalue.annotations.UseMetaDataExtension;
import com.slimgears.util.autovalue.apt.Context;

import java.util.Collections;
import java.util.Objects;

@AutoService(Extension.class)
@SupportedAnnotations(UseMetaDataExtension.class)
public class MetaDataExtension implements Extension {
    private final Extension extension = CompositeExtension.of(new WithKey(), new WithoutKey());

    private static class WithKey implements Extension {
        @Override
        public boolean isApplicable(Context context) {
            return context.hasKey();
        }

        @Override
        public Iterable<TypeInfo> getInterfaces(Context context) {
            return Collections.singletonList(TypeInfo.of(HasMetaClassWithKey.class)
                    .toBuilder()
                    .typeParams(
                            Objects.requireNonNull(context.keyProperty()).type().asBoxed(),
                            context.targetClassWithParams())
                    .build());
        }
    }

    private static class WithoutKey implements Extension {
        @Override
        public boolean isApplicable(Context context) {
            return !context.hasKey();
        }

        @Override
        public Iterable<TypeInfo> getInterfaces(Context context) {
            return Collections.singletonList(TypeInfo.of(HasMetaClass.class)
                    .toBuilder()
                    .typeParams(context.targetClassWithParams())
                    .build());
        }
    }

    @Override
    public String generateClassBody(Context context) {
        return context.evaluateResource("metadata-body.java.vm");
    }

    @Override
    public Iterable<TypeInfo> getInterfaces(Context context) {
        return extension.getInterfaces(context);
    }
}
