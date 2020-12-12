package com.skypan.myapplication.Retrofit;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitManagerAPI {
    @POST("new-event")
    Call<Ack> newEvent(@Body Event event);

    @POST("delete-event")
    Call<Ack> deleteEvent(@Body Event event);

//    @POST("alter-event")
//    Call<Ack> alterEvent(@Body Event event);

    @GET("main-activity")
    Call<List<Event>> getMain(
            @Query("user_id") String user_id,
            @Query("status") String status);

    @GET("query-event")
    Call<List<Event>> getSearchEvents(
            @Query("pt_start") String pt_start,
            @Query("pt_end") String pt_end,
            @Query("driver_name") String driver_name,
            @Query("time") Date date,
            @Query("is_helmet") boolean is_helmet,
            @Query("is_free") boolean is_free,
            @Query("sex") int sex
    );

    @POST("request")
    Call<Ack> sendRequest(@Body Request request);

    @POST("register")
    Call<String> register(@Body User user);

    @POST("login")
    Call<String> login(@Field("password") String password,
                       @Field("email") String email);


    @GET("posts")
    Call<List<Post>> getPosts();
//    @POST("alter-user")
//    Call<Ack> alterUser(@Body Request request);

    @GET("query-user")
    Call<User> queryUser(@Query("user_id") String user_id);

}
