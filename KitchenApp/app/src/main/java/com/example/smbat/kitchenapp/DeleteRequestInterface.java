package com.example.smbat.kitchenapp;


import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Path;

public interface DeleteRequestInterface {

    @DELETE("/api/removeDish/{id}")
    Call<Void> deleteItem(@Path("id") int itemId);
}
