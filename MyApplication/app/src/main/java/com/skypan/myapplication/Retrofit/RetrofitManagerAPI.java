package com.skypan.myapplication.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitManagerAPI {
    @POST("new-event")
    Call<Ack> newEvent(@Body Event event);

    @FormUrlEncoded
    @POST("delete-event")
    Call<Ack> deleteEvent(@Field("event_id") String event_id,
                          @Field("operation") String operation);//operation = delete finish drop

    @POST("alter-event")
    Call<Ack> alterEvent(@Body Event event);

    @FormUrlEncoded
    @POST("accept-request")
    Call<Ack> acceptRequest(@Field("user_id") String user_id,
                            @Field("event_id") String event_id);

    @FormUrlEncoded
    @POST("reject-event")
    Call<Ack> rejectRequest(@Field("user_id") String user_id,
                            @Field("event_id") String event_id,
                            @Field("reason") String reason);

    @GET("query_driver/{id}")
    Call<List<Event>> getDriverMain(
            @Path("id") String user_id);

    @GET("query_passenger/{id}")
    Call<List<Event>> getPassengerMain(
            @Path("id") String user_id);

    @GET("query-event")
    Call<List<Event>> getSearchEvents(
            @Query("user_id") String user_id,
            @Query("driver_name") String driver_name,
            @Query("start") String pt_start,
            @Query("end") String pt_end,
            @Query("time") String date,
            @Query("is_self_helmet") boolean is_helmet,
            @Query("is_free") boolean is_free,
            @Query("driver_sex") int sex
    );

    @POST("request")
    Call<Ack> sendRequest(@Body Request request);

    @POST("register")
    Call<Ack> register(@Body Custom_register custom_register);

    @FormUrlEncoded
    @POST("login")
    Call<User> login(@Field("password") String password,
                     @Field("mail") String email);

    @POST("alter-user")
    Call<Ack> alterUser(@Body User user);

    @GET("query-user")
//查詢個人資料
    Call<User> queryUser(@Query("user_id") String user_id);

    @FormUrlEncoded
    @POST("alert")
//檢查請求是否需警告
    Call<Ack> alert(@Field("user_id") String user_id,
                    @Field("time") String time);

    @FormUrlEncoded
    @POST("alert_Interval")
//檢查新增是否需警告
    Call<Ack> alertInterval(@Field("user_id") String user_id,
                            @Field("query_TimeStart") String start_time,
                            @Field("query_TimeEnd") String end_time);

    public String AUTH = "6d9345743ae6821";

    @Headers("Authorization: Client-ID " + AUTH)
    @FormUrlEncoded
    @POST("image")
    Call<ImageResponse> postImg(@Field("image") String base64);

    @FormUrlEncoded
    @POST("accountExist")
    Call<Ack> checkEmail(@Field("mail") String email);

    @FormUrlEncoded
    @POST("get-inform")
    Call<Inform> getInforms(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("search-past")
    Call<List<Past_Event>> searchPast(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("score")
    Call<Ack> Rating(@Field("user_id") String user_id,
                      @Field("event_id") String event_id,
                      @Field("score") String score);
    @FormUrlEncoded
    @POST("newPassword")
    Call<Ack> alter_password(@Field("mail") String mail,
                     @Field("password") String password);
}
