/*
 * Decompiled with CFR 0_118.
 */
package com.thoughtworks.twist.ssl.utils;

import java.util.Date;

public class Clock {
    private static Date now;
    private static Date longLongAgo;

    public static Date now() {
        if (now == null) {
            return new Date();
        }
        return now;
    }

    public static void reset() {
        now = null;
        longLongAgo = null;
    }

    public static void now(Date now) {
        Clock.now = now;
    }

    public static void longLongAgo(Date longLongAgo) {
        Clock.longLongAgo = longLongAgo;
    }

    public static Date longLongAgo() {
        if (longLongAgo == null) {
            return new Date(0);
        }
        return longLongAgo;
    }

    public static Date yearsFromNow(int years) {
        Date date = Clock.now();
        date.setYear(date.getYear() + years);
        return date;
    }

    public static Date yearsAgo(int ago) {
        return Clock.yearsFromNow(- ago);
    }
}

