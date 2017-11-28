package com.example.smbat.kitchenapp.interfaces;


import com.example.smbat.kitchenapp.objects.Dish;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PostRequest {

    @POST("/api/createDish/")
    @FormUrlEncoded
    Call<Dish> savePost(@Field("title") String title,
                        @Field("description") String description,
                        @Field("image") String image,
                        @Field("userId") int userId);
}
