package com.zero.zero.timetable.HTMLFetcher;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.zero.zero.timetable.HTMLFetcher.process.SubstitutionSchedule;
import com.zero.zero.timetable.HTMLFetcher.receive.HTMLFetcher;
import com.zero.zero.timetable.MyTimeTableManagement.MyTimeTableFragment;

import java.util.concurrent.ExecutionException;

public class OVPEasyFetcher {
    public SubstitutionSchedule schedule = null;
    private static String TAG = "OVPEasyFetcher";
    private static Context contextRef;
    private static ProgressDialog progressDialog;

    public OVPEasyFetcher() {}

    public static void initializeContext(Context ctx) {
        contextRef = ctx;
    }

    public static void clearContext() {
        contextRef = null;
        progressDialog = null; }

    public SubstitutionSchedule getSchedule(String url, String username, String password) {
        if (schedule != null) {
            return schedule;
        }

        HTTPRequestTask task = new HTTPRequestTask();
        task.execute(url, username, password);
        SubstitutionSchedule result = null;
        try {
            result = this.schedule = task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    public SubstitutionSchedule getSchedule(){
        return this.schedule;
    }

    public void init(String url, String username, String password, MyTimeTableFragment TimeTableRef) {
        HTTPRequestTask task = new HTTPRequestTask();
        task.execute(url, username, password);

        try {
            this.schedule = task.get();
            if(this.schedule != null) {
                TimeTableRef.fill(this.schedule);
            } else {
                Log.d(TAG, "The SubstitutionSchedule couldn't be received!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private class HTTPRequestTask extends AsyncTask<String, Integer, SubstitutionSchedule> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (contextRef == null) {
                Log.d(TAG, "Context has not been initialized!"); } else {

            }
        }

        @Override
        protected SubstitutionSchedule doInBackground(String... params) {
            SubstitutionSchedule schedule = null;
            try {
                boolean fetch_setting = HTMLFetcher.fetchOnInitialization;
                HTMLFetcher.fetchOnInitialization = false;

                HTMLFetcher fetcher = new HTMLFetcher(params[0], params[1], params[2]);
                fetcher.fetch();

                schedule = fetcher.getSubstitutionSchedule();
                HTMLFetcher.fetchOnInitialization = fetch_setting;
            } catch (NullPointerException e) {
                e.printStackTrace();
                Log.d(TAG, "Couldn't fetch!");
            }

            return schedule;
        }

        @Override
        protected void onPostExecute(SubstitutionSchedule schedule) {
            if (OVPEasyFetcher.progressDialog != null) {
                progressDialog.dismiss();
            }
            if (schedule != null) {
                Log.d(TAG, "onPostExecute: Done!");

            } else {
                Log.d(TAG, "onPostExecute: Error while fetching!");
            }

        }
    }
}
