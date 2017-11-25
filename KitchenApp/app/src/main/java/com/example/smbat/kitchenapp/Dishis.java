package com.example.smbat.kitchenapp;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Dishis {
    @SerializedName("data")
    private List<Dish> dishis;


    public List<Dish> getDishis() {
        return dishis;
    }
}
