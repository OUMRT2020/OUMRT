package com.skypan.myapplication.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitManagerAPI {
    @GET("passenger_main")
    Call<List<Event>> getPassengerMain(
            @Query("user_id") String user_id,
            @Query("status") String status
    );

    @GET("search_event")
    Call<List<Event>> getSearchEvents(
            @Query("pt_start") String pt_start,
            @Query("pt_end") String pt_end,
            @Query("driver_name") String driver_name,
            @Query("is_helmet") boolean is_helmet,
            @Query("is_free") boolean is_free,
            @Query("sex") int sex
    );

    @POST("Request")
    Call<Request> postRequest(@Body Request request);

    @POST("newEvent")
    Call<Event> postEvent(@Body Event event);
}
