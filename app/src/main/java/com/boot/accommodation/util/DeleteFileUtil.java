package com.boot.accommodation.util;

import android.os.AsyncTask;

public class DeleteFileUtil extends AsyncTask<String, Void, Exception> {

    public static final int NUM_DATE_DELETE_FILE = 10;
    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Exception doInBackground(String... params) {
        try {
            // Delete file if date create > numday
            FileUtil.deleteTakenPhoto(NUM_DATE_DELETE_FILE);
        } catch (Exception e) {
            return e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Exception result) {
    }
}
