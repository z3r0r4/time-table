package com.zero.zero.timetable.HTMLFetcher.process;

import java.util.regex.Pattern;

public class HTMLExtractionTags {
    public static final Pattern div = Pattern.compile("<(div)(\\b[^>]*>)(.*?)</\\1>"),
            tr = Pattern.compile("<(tr)(\\b[^>]*>)(.*?)</\\1>"),
            td = Pattern.compile("<(td)(\\b[^>]*>)(.*?)</\\1>"),
            th = Pattern.compile("<(th)(\\b[^>]*>)(.*?)</\\1>");

    private HTMLExtractionTags() {
    }
}
