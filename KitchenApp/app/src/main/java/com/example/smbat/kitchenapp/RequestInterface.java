package com.example.smbat.kitchenapp;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {

    @GET("api/dishis/")
    Call<Dishis> getJson();
}