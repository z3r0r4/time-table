package com.zero.zero.timetable.HTMLFetcher.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;

public class SubstitutionSchedule {
    private String title = "";
    private ArrayList<String[]> data = null;
    private ArrayList<String[]> infos = null;
    public Calendar calendar = null;

    public SubstitutionSchedule(ArrayList<String[]> data, ArrayList<String[]> infos, String title) {
        this.data = data;
        this.title = title;
        this.infos = infos;
        this.calendar = Calendar.getInstance();
        calendar = Calendar.getInstance();
    }

    public static String stringify(ArrayList<String[]> data, int index) {
        return Arrays.deepToString(data.get(index));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String[]> getData() {
        return data;
    }

    public void setData(ArrayList<String[]> data) {
        this.data = data;
    }

    public ArrayList<String[]> getData(String Identifier) {
        ArrayList<String[]> result = new ArrayList<String[]>();
        Iterator<String[]> dataIterator = data.iterator();
        while (dataIterator.hasNext()) {
            String[] row = dataIterator.next();
            if (Arrays.asList(row).contains(Identifier))
                result.add(row);
        }
        return result;
    }

    public ArrayList<String[]> getData(String... Identifier) {
        ArrayList<String[]> result = new ArrayList<String[]>();
        Iterator<String[]> dataIterator = data.iterator();
        while (dataIterator.hasNext()) {
            String[] row = dataIterator.next();
            Boolean isSearchedObject = true;
            for (int i = 0; i < Identifier.length; i++) {
                if (!Arrays.asList(row).contains(Identifier[i])) {
                    isSearchedObject = false;
                    break;
                }
            }
            if (isSearchedObject)
                result.add(row);
        }
        return result;
    }

    public ArrayList<String[]> getData_any(String... Identifier) {
        ArrayList<String[]> result = new ArrayList<String[]>();
        Iterator<String[]> dataIterator = data.iterator();
        while (dataIterator.hasNext()) {
            String[] row = dataIterator.next();
            Boolean isSearchedObject = false;
            for (int i = 0; i < Identifier.length; i++) {
                if (Arrays.asList(row).contains(Identifier[i])) {
                    isSearchedObject = true;
                    break;
                }
            }
            if (isSearchedObject)
                result.add(row);
        }
        return result;
    }

    public String stringify(String Identifier) {
        String result = new String();
        ArrayList<String[]> data = getData(Identifier);
        Iterator<String[]> dataIterator = data.iterator();
        while (dataIterator.hasNext()) {
            String[] currentString = dataIterator.next();
            result.concat(Arrays.deepToString(currentString) + "\n");
        }
        return result;
    }

    public void log() {
        System.out.println(title);
        Iterator<String[]> infoIterator = infos.iterator();
        while (infoIterator.hasNext()) {
            String[] row = infoIterator.next();
            System.out.println(Arrays.toString(row));
        }
        Iterator<String[]> dataIterator = data.iterator();
        while (dataIterator.hasNext()) {
            String[] row = dataIterator.next();
            System.out.println(Arrays.toString(row));
        }
        System.out.println("---------\n\n\n---------");
    }

    public void log(String CLASS) {
        System.out.println(title);
        Iterator<String[]> dataIterator = data.iterator();
        while (dataIterator.hasNext()) {
            String[] row = dataIterator.next();
            if (row[0].equals(CLASS))
                System.out.println(Arrays.toString(row));
        }
        System.out.println("---------\n\n\n---------");
    }

    public ArrayList<String[]> getInfos() {
        return infos;
    }

    public void setInfos(ArrayList<String[]> infos) {
        this.infos = infos;
    }

    public boolean monthIsEven() {
        return calendar.get(calendar.MONTH) % 2 == 0;
    }

    public float daysSinceLastUpdate() {
        return -this.calendar.compareTo(Calendar.getInstance()) / 86400;
    }

}
