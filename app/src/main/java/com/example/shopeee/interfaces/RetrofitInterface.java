package com.example.shopeee.interfaces;

import com.example.shopeee.repository.Item;
import com.example.shopeee.repository.LoginResult;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @POST("/login")
    Call<LoginResult> executeLogin(@Body HashMap<String, String> map);

    @POST("/signup")
    Call<Void> executeSignup (@Body HashMap<String, String> map);

    @GET("items")
    Call<List<Item>> fetchItems(@Header("Authorization") String token, @Query("userID") String userID);

}