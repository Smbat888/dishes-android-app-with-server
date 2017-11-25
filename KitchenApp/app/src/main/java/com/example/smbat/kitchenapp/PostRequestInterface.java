package com.example.smbat.kitchenapp;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PostRequestInterface {

    @POST("/api/createDish/")
    @FormUrlEncoded
    Call<Dish> savePost(@Field("title") String title,
            @Field("description") String description,
            @Field("image") String image,
            @Field("userId") int userId);
}
