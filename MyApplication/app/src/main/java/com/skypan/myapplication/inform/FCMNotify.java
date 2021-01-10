package com.skypan.myapplication.inform;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class FCMNotify {
    private String tkn,title,message;


    public FCMNotify(String tkn,String title,String message) {
        this.tkn = tkn;
        this.title = title;
        this.message = message;
        new Notify().execute();
    }

    public class Notify extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {


            try {

                URL url = new URL("https://fcm.googleapis.com/fcm/send");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Authorization", "key=AAAAqVoFzIE:APA91bEcIeHjC4P7cpJb-QcvPUZQGLTA39ea5VcFeO7AvN0itMVHunj2ewKsIfcXHMOD7rpRTg1MwX9dZoH49oa8EslgQophRcMX4bSw5eHp9tBY5ZRHWm4Ow-39KDh7UnWjZyrok1RB");
                conn.setRequestProperty("Content-Type", "application/json");

                JSONObject json = new JSONObject();

                json.put("to", tkn);


                JSONObject info = new JSONObject();
                info.put("title", title);   // Notification title
                info.put("body", message); // Notification body
                json.put("notification", info);

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(json.toString());
                wr.flush();
                conn.getInputStream();

            } catch (Exception e) {
                Log.d("Error", "" + e);
            }


            return null;
        }
    }
}