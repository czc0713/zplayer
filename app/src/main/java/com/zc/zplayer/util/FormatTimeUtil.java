package com.zc.zplayer.util;

import java.util.Formatter;
import java.util.Locale;

public class FormatTimeUtil {
    private StringBuilder formatBuilder;
    private Formatter formatter;
    private final String HRS_MIN_SEC_FORMAT = "%d:%02d:%02d";
    private final String MIN_SEC_FORMAT = "%02d:%02d";

    public FormatTimeUtil(){
        formatBuilder = new StringBuilder();
        formatter = new Formatter(formatBuilder, Locale.getDefault());
    }

    public String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours   = totalSeconds / 3600;
        formatBuilder.setLength(0);
        if (hours > 0) {
            return formatter.format(HRS_MIN_SEC_FORMAT, hours, minutes, seconds).toString();
        } else {
            return formatter.format(MIN_SEC_FORMAT, minutes, seconds).toString();
        }
    }
}
