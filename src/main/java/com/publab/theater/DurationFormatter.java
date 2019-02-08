package com.publab.theater;

import org.springframework.format.Formatter;

import java.util.Locale;

public class DurationFormatter implements Formatter<Integer> {

    @Override
    public Integer parse(final String text, final Locale locale) {
        return Integer.parseInt(text);
    }

    @Override
    public String print(final Integer object, final Locale locale) {
        return String.format("%d ч %02d мин", object / 60, object % 60);
    }

}
