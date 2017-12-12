package com.example.smbat.kitchenapp.interfaces;


import com.example.smbat.kitchenapp.constants.Constants;
import com.example.smbat.kitchenapp.models.Dishes;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetRequest {

    @GET(Constants.GET_ENDPOINT)
    Call<Dishes> getJson();
}