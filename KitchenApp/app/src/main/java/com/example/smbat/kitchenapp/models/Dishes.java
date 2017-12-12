package com.example.smbat.kitchenapp.models;


import com.example.smbat.kitchenapp.constants.Constants;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Dishes {
    @SerializedName(Constants.DATA)
    private List<Dish> dishes;


    public List<Dish> getDishes() {
        return dishes;
    }
}
