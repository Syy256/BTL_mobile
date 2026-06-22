package com.example.watchstoreapp.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class FormatUtils {
    public static String formatPrice(double price) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
        return nf.format((long) price) + " đ";
    }

    public static String formatDate(String datetime) {
        if (datetime == null || datetime.isEmpty()) return "";
        try {
            if (datetime.length() >= 10) return datetime.substring(8, 10) + "/" + datetime.substring(5, 7) + "/" + datetime.substring(0, 4);
        } catch (Exception ignored) {}
        return datetime;
    }

    public static String formatDateTime(String datetime) {
        if (datetime == null || datetime.isEmpty()) return "";
        try {
            String date = formatDate(datetime);
            String time = datetime.length() >= 16 ? datetime.substring(11, 16) : "";
            return date + (time.isEmpty() ? "" : " " + time);
        } catch (Exception ignored) {}
        return datetime;
    }
}
