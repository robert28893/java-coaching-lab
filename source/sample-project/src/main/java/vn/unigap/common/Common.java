package vn.unigap.common;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

public class Common {
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static String subString(String str, int len) {
        if (str == null)
            return null;
        return str.substring(0, Math.min(len, str.length()));
    }

    public static String toUpperCase(String s) {
        if (s == null)
            return null;
        return s.toUpperCase();
    }

    public static String toLowerCase(String s) {
        if (s == null)
            return null;
        return s.toLowerCase();
    }

    public static Date currentTime() {
        return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime currentDateTime() {
        return LocalDateTime.now();
    }
}
