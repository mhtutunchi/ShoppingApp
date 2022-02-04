package com.example.shoppingapp.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("login.php")
    Call<Users> sendLoginForUsers(@Field("phone") String phone , @Field("password") String password);

    @FormUrlEncoded
    @POST("register.php")
    Call<Users> sendRegisterForUsers(@Field("email") String email, @Field("phone") String phone , @Field("password") String password);

}
