package com.zero.zero.timetable.HTMLFetcher;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.zero.zero.timetable.HTMLFetcher.process.SubstitutionSchedule;
import com.zero.zero.timetable.HTMLFetcher.receive.HTMLFetcher;

import java.util.concurrent.ExecutionException;

import static android.support.constraint.Constraints.TAG;

public class OVPEasyFetcher {
    public static SubstitutionSchedule schedule = null;
    private static Context contextRef;
    private static ProgressDialog progressDialog;

    public static void initializeContext(Context ctx) {
        contextRef = ctx;
    }

    public static void clearContext() {
        contextRef = null;
        progressDialog = null;
    }

    public static SubstitutionSchedule getSchedule(String url, String username, String password) {
        if(schedule != null) {
            return schedule;
        }

        HTTPRequestTask task = new HTTPRequestTask();
        task.execute(url, username, password);
        SubstitutionSchedule result = null;
        try {
            result = OVPEasyFetcher.schedule = task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void init(String url, String username, String password) {
        HTTPRequestTask task = new HTTPRequestTask();
        task.execute(url, username, password);

        try {
            OVPEasyFetcher.schedule = task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static class HTTPRequestTask extends AsyncTask<String, Integer, SubstitutionSchedule> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (contextRef == null) {
                System.out.println("Context has not been initialized!");
            }
            // display a progress dialog for good user experiance
            progressDialog = new ProgressDialog(OVPEasyFetcher.contextRef);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
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
                Log.d("OVPEasyFetcher", "Couldn't fetch!");
            }

            return schedule;
        }

        @Override
        protected void onPostExecute(SubstitutionSchedule schedule) {
            if (OVPEasyFetcher.progressDialog != null) {
                progressDialog.dismiss();
            }
            if(schedule != null) {
                schedule.log();
                Log.d(TAG, "onPostExecute: Done!");

                /*
                something like:

                TableGenerator.initializeTable(schedule);

                parse the SubstitutionSchedule to a seperate class to generate the Table when the async task is done
                */

            } else {
                Log.d(TAG, "onPostExecute: Error while fetching!");
            }


        }

    }
}
