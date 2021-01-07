package com.skypan.myapplication.Retrofit;

import java.util.ArrayList;

public class Inform {
    ArrayList<inform_content> driver_context;
    ArrayList<inform_content> passenger_context;

    class inform_content {
        private String status;
        private String event_id;
        private String text;
    }
}
