/*
 * Decompiled with CFR 0_118.
 */
package com.thoughtworks.twist.ssl.utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class StringUtils {
    public static String join(Iterable<String> strs, String delimiter) {
        StringBuffer buffer = new StringBuffer();
        Iterator<String> i = strs.iterator();
        while (i.hasNext()) {
            buffer.append(i.next());
            if (!i.hasNext()) continue;
            buffer.append(delimiter);
        }
        return buffer.toString();
    }

    public static String join(String[] strs, String delimiter) {
        return StringUtils.join(Arrays.asList(strs), delimiter);
    }

    public static List<String> split(String source, String delimiter) {
        return Arrays.asList(source.split(delimiter));
    }
}

