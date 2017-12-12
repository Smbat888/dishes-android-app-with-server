package com.example.smbat.kitchenapp.interfaces;


import com.example.smbat.kitchenapp.constants.Constants;
import com.example.smbat.kitchenapp.models.Dish;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PostRequest {

    @POST(Constants.POST_ENDPOINT)
    @FormUrlEncoded
    Call<Dish> savePost(@Field(Constants.TITLE) String title,
                        @Field(Constants.DESCRIPTION) String description,
                        @Field(Constants.IMAGE) String image,
                        @Field(Constants.USER_ID) int userId);
}
