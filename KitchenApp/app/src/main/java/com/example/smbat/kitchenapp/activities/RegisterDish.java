package com.example.smbat.kitchenapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.smbat.kitchenapp.R;
import com.example.smbat.kitchenapp.constants.Constants;
import com.example.smbat.kitchenapp.interfaces.PostRequest;
import com.example.smbat.kitchenapp.models.Dish;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterDish extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_dish);
        createDish();
    }

    private void createDish() {
        Button registerDish = (Button)findViewById(R.id.register_button);
        EditText title = (EditText)findViewById(R.id.dish_title);
        EditText description = (EditText)findViewById(R.id.dish_description);
        registerDish.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addDish(title.getText().toString(), description.getText().toString());
                    }
                }
        );
    }

    private void addDish(String title, String description) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.HOST_NAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostRequest requestInterface = retrofit.create(PostRequest.class);
        requestInterface.savePost(title, description, "some_image.jpg", 1).enqueue(new Callback<Dish>() {
            @Override
            public void onResponse(Call<Dish> call, Response<Dish> response) {

                if(response.isSuccessful()) {
                    Log.i("HEY", "post submitted to API." + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Dish> call, Throwable t) {
                Log.e("HEY", "Unable to submit post to API.");
            }
        });
    }
}
