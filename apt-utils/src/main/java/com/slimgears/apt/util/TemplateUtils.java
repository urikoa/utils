package com.slimgears.apt.util;

import com.google.common.base.Strings;

import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("WeakerAccess")
public class TemplateUtils {
    public final static TemplateUtils instance = new TemplateUtils();
    private final static Pattern camelCasePattern = Pattern.compile("([a-z])([A-Z])");

    public static String camelCaseToDash(String camelCase) {
        return camelCasePattern.matcher(camelCase).replaceAll("$1-$2").toLowerCase();
    }

    public static String toCamelCase(String firstPart, String... otherParts) {
        return firstPart + Stream.of(otherParts).map(TemplateUtils::capitalize).collect(Collectors.joining());
    }

    public static String capitalize(String str) {
        return Strings.isNullOrEmpty(str) ? str : Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    public static Function<String, String> postProcessImports(ImportTracker importTracker) {
        return postProcessImports(importTracker, type -> type);
    }

    public static Function<String, String> postProcessImports(ImportTracker importTracker, Function<String, String> typeMapper) {
        return code -> substituteImports(importTracker, code, typeMapper);
    }

    private static String substituteImports(ImportTracker importTracker, String code, Function<String, String> typeMapper) {
        StringBuilder builder = new StringBuilder();
        int len = code.length();
        int prevPos = 0;
        int pos = 0;
        while ((pos = code.indexOf("$[", pos)) >= 0) {
            int count = 1;
            int endPos = pos + 2;
            while (count > 0 && endPos < len) {
                if (code.charAt(endPos) == '[') ++count;
                else if (code.charAt(endPos) == ']') --count;
                ++endPos;
            }

            String type = typeMapper.apply(code.substring(pos + 2, endPos - 1));
            builder.append(code, prevPos, pos);
            builder.append(importTracker.use(type));
            prevPos = pos = endPos;
        }
        builder.append(code, prevPos, len);
        return builder.toString();
    }

    public static String preProcessWhitespace(String template) {
        return template.replace("\r\n", "\n").replace("\r", "\n")
                .replaceAll("(?s)(##\\n) +([^ ])", "$1$2")
                /*.replaceAll("(?s)(\\n) +(#[^#])", "$1$2")*/;
    }

    public static String postProcessWhitespace(String template) {
        return template
                .replace("\r\n", "\n")
                .replace("\r", "\n")
                .replaceAll("(?s)([^\\s]+[ ])[ ]+([^ ])", "$1$2")
                .replaceAll("(?s)\\n\\s*\\n\\s*\\n", "\n\n");
    }
}
