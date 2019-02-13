package com.slimgears.util.autovalue.apt;

import com.slimgears.util.autovalue.annotations.AutoValuePrototype;
import com.slimgears.util.autovalue.annotations.Reference;

@AutoValuePrototype
interface TestEntityPrototype {
    String text();
    String description();
    @Reference TestReferencedEntity referencedEntity();
}