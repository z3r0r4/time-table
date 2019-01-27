package com.zero.zero.timetable.hmtl_fetcher.receive;

import android.util.Log;

import com.zero.zero.timetable.hmtl_fetcher.process.DataExtractor;
import com.zero.zero.timetable.hmtl_fetcher.process.SimpleBase64Encoder;
import com.zero.zero.timetable.hmtl_fetcher.process.SubstitutionSchedule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTMLFetcher {
    public static boolean fetchOnInitialization = true;
    public static boolean initialized = false;
    private static int phase = 0;
    private URL url = null;
    private InputStream istream = null;
    private BufferedReader buffer = null;
    private String wrapper = "center";
    private String username = "";
    private String password = "";
    private String Token = getToken();
    private boolean requiresAuthentication = true;
    private static final String TAG = "HTMLFetcher";

    public HTMLFetcher(String url) {
        requiresAuthentication = false;
        if (fetchOnInitialization)
            fetch(url);
        else
            try {
                this.url = new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
    }

    public HTMLFetcher(String url, String username, String password) {
        setAuthentication(username, password);
        if (fetchOnInitialization)
            fetch(url);
        else
            try {
                this.url = new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
    }

    public final void fetch() {
        try {
            Log.i(TAG,"Fetching from the UrlSource: "+this.url.toString());
            HttpURLConnection connection = (HttpURLConnection)this.url.openConnection();

            if (requiresAuthentication) {
                connection.setRequestProperty("Authorization", "Basic " + Token);
            }

            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // everything ok
                istream = connection.getInputStream();
                buffer = new BufferedReader(new InputStreamReader(istream));
                // process stream
            } else {
                System.out.println("ERROR! RESPONSE CODE: " + connection.getResponseCode());
            }
        } catch (MalformedURLException e) {
            System.out.println("The Url that was entered seems to be wrong or expired!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("The fetching-process was unsuccessfull!");
            e.printStackTrace();
        }
    }

    public final void fetch(String url) {
        try {
            this.url = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) this.url.openConnection();

            if (requiresAuthentication) {
                connection.setRequestProperty("Authorization", "Basic " + Token);
            }

            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // everything ok
                istream = connection.getInputStream();
                buffer = new BufferedReader(new InputStreamReader(istream));
                // process stream
            } else {
                System.out.println("ERROR! RESPONSE CODE: " + connection.getResponseCode());
            }
        } catch (MalformedURLException e) {
            System.out.println("The Url that was entered seems to be wrong or expired!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("The fetching-process was unsuccessfull!");
            e.printStackTrace();
        }
    }

    public void setAuthentication(String username, String password) {
        this.username = username;
        this.password = password;
        Token = getToken();
    }

    public void setUsername(String username) {
        this.username = username;
        Token = getToken();
    }

    public void setPassword(String password) {
        this.password = password;
        Token = getToken();
    }

    public void enableAuthentication() {
        requiresAuthentication = true;
    }

    public void disableAuthentication() {
        requiresAuthentication = false;
    }

    public void logHTMLtoConsole() {
        DataExtractor sort = new DataExtractor();
        SubstitutionSchedule plan = sort.extractInformation(buffer, wrapper);
        plan.log();

        try {
            istream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fetch();
    }

    public void logHTMLtoConsole(String Class) {
        DataExtractor sort = new DataExtractor();
        SubstitutionSchedule plan = sort.extractInformation(buffer, wrapper);
        plan.log(Class);

        try {
            istream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fetch();
    }

    public SubstitutionSchedule getSubstitutionSchedule() {
        DataExtractor sort = new DataExtractor();
        SubstitutionSchedule schedule = sort.extractInformation(buffer, wrapper);
        fetch();
        return schedule;
    }

    private final String getToken() {
        String token = SimpleBase64Encoder.encode(username + ":" + password);
        token = token.replaceAll("\n", "");
        token = token.replaceAll("\t", "");
        token = token.replaceAll(" ", "");
        return token;
    }
}
