package com.zero.zero.timetable.HTMLFetcher.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class DataExtractor {

    private Matcher matcher = null;
    private Boolean isImportantInformation = false;
    private String title = null;

    public SubstitutionSchedule extractInformation(BufferedReader br, String wrapper) {
        ArrayList<String[]> SubstSchedule = new ArrayList<String[]>();
        ArrayList<String[]> infos = new ArrayList<String[]>();
        String line = "";

        try {
            while ((line = br.readLine()) != null) {
                if (line.contains("<" + wrapper + ">")) {
                    isImportantInformation = true;
                    continue;
                } else if (line.contains("</" + wrapper + ">")) {
                    isImportantInformation = false;
                }
                if (isImportantInformation) {
                    if (title == null) {
                        matcher = HTMLExtractionTags.div.matcher(line);
                        title = matcher.replaceAll("$3");
                    } else if (line.contains("<th")) {

                    } else if (line.contains("<tr")) {
                        matcher = HTMLExtractionTags.tr.matcher(line);
                        line = matcher.replaceAll("$3").replaceAll("&nbsp;", " ");
                        matcher = HTMLExtractionTags.td.matcher(line);
                        String[] data = matcher.replaceAll("$3#").split("#");
                        if (data.length >= 5) {
                            SubstSchedule.add(data);
                        } else {
                            infos.add(data);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new SubstitutionSchedule(SubstSchedule, infos, title);
    }

}
