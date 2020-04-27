package ru.yaneg.graduation_of_topjava_springboot.ui.converter;

import org.springframework.format.Formatter;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeFormatters {

    public static class LocalDateFormatter implements Formatter<LocalDate> {

        @Override
        public LocalDate parse(String text, Locale locale) {
            return parseLocalDate(text);
        }

        @Override
        public String print(LocalDate lt, Locale locale) {
            return lt.format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }

    public static class LocalTimeFormatter implements Formatter<LocalTime> {

        @Override
        public LocalTime parse(String text, Locale locale) {
            return parseLocalTime(text);
        }

        @Override
        public String print(LocalTime lt, Locale locale) {
            return lt.format(DateTimeFormatter.ISO_LOCAL_TIME);
        }
    }

    private  static @Nullable LocalDate parseLocalDate(@Nullable String str) {
        return StringUtils.isEmpty(str) ? null : LocalDate.parse(str);
    }

    private static @Nullable LocalTime parseLocalTime(@Nullable String str) {
        return StringUtils.isEmpty(str) ? null : LocalTime.parse(str);
    }
}
