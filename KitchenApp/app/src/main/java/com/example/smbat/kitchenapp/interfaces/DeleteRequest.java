package com.example.smbat.kitchenapp.interfaces;


import com.example.smbat.kitchenapp.constants.Constants;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Path;

public interface DeleteRequest {

    @DELETE(Constants.DELETE_ENDPOINT)
    Call<Void> deleteItem(@Path(Constants.ID) int itemId);
}
