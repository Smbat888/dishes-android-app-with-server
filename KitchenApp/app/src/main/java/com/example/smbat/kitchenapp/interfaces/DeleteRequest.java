package com.example.smbat.kitchenapp.interfaces;


import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Path;

public interface DeleteRequest {

    @DELETE("/api/removeDish/{id}")
    Call<Void> deleteItem(@Path("id") int itemId);
}
