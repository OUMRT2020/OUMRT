package com.skypan.myapplication.Retrofit;

import java.util.ArrayList;

public class Inform {
    ArrayList<inform_content> driver_context;
    ArrayList<inform_content> passenger_context;

    public class inform_content {
        private String status;
        private String event_name;
        private String text;

        public String getStatus() {
            return status;
        }

        public String getEvent_name() {
            return event_name;
        }

        public String getText() {
            return text;
        }
    }

    public ArrayList<inform_content> getDriver_context() {
        return driver_context;
    }

    public ArrayList<inform_content> getPassenger_context() {
        return passenger_context;
    }
}
