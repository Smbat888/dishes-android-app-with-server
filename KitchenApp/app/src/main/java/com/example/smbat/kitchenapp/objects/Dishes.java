package com.example.smbat.kitchenapp.objects;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Dishes {
    @SerializedName("data")
    private List<Dish> dishes;


    public List<Dish> getDishes() {
        return dishes;
    }
}
