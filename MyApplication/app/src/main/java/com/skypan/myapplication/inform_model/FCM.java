package com.skypan.myapplication.inform_model;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class FCM {
    public String token;

    public void send_notify(String token) {
//        FirebaseInstanceId.getInstance()
//                .getInstanceId()
//                .addOnCompleteListener(task -> {
//                    if (!task.isSuccessful()) {
//                        Log.d("FCMDemo", "getInstanceId failed", task.getException());
//                        return;
//                    }
//                    // Get new Instance ID token
//                    String token = task.getResult().getToken();
//                    tkn = token;
//                    Log.d("FCMDemo", "token: " + token);
//                });
        this.token = token;
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

                json.put("to", token);


                JSONObject info = new JSONObject();
                info.put("title", "TechnoWeb");   // Notification title
                info.put("body", "Hello Test notification"); // Notification body

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