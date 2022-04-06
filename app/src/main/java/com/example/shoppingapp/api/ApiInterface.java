package com.example.shoppingapp.api;

import com.example.shoppingapp.model.Cart;
import com.example.shoppingapp.model.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login.php")
    Call<Users> sendLoginForUsers(@Field("phone") String phone , @Field("password") String password);

    @FormUrlEncoded
    @POST("register.php")
    Call<Users> sendRegisterForUsers(@Field("email") String email, @Field("phone") String phone , @Field("password") String password);

    @FormUrlEncoded
    @POST("sendComment.php")
    Call<Message> sendComment(
            @Field("id_product") String id_product,
            @Field("user_email") String user_email ,
            @Field("description") String description,
            @Field("date") String date,
            @Field("rating") String rating,
            @Field("positive") String positive,
            @Field("negative") String negative,
            @Field("title") String title
    );

    @FormUrlEncoded
    @POST("sendToCart.php")
    Call<Message> sendToCart(@Field("id_product") String id_product , @Field("user_email") String user_email);

    @GET("getCountCart.php")
    Call<Message> getCountCart(@Query("user_email") String user_email);

    @GET("getListCart.php")
    Call<List<Cart>> getListCart(@Query("user_email") String user_email);

    @GET("deleteCartProduct.php")
    Call<Message> deleteCart(@Query("cart_id") String cart_id);

    @GET("getPayOff.php")
    Call<List<Cart>> updateCart(@Query("user_email") String user_email , @Query("token_pay_off") String token_pay_off);

    @GET("getOrderProduct.php")
    Call<List<Order>> getListOrderProduct(@Query("user_email") String user_email );



}
