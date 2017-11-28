package com.example.smbat.kitchenapp.interfaces;


import com.example.smbat.kitchenapp.objects.Dishes;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetRequest {

    @GET("api/dishes/")
    Call<Dishes> getJson();
}