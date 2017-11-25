package com.example.smbat.kitchenapp;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DishesListAdapter extends RecyclerView.Adapter<DishesListAdapter.DishViewHolder> {

    private Context context;
    private ArrayList<Dish> dishis;

    public DishesListAdapter(Context context, ArrayList<Dish> events) {
        this.context = context;
        this.dishis = events;
    }

    @Override
    public DishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DishViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(DishViewHolder holder, int position) {
        holder.image.setImageResource(R.drawable.food);
        holder.title.setText(dishis.get(position).getTitle());
        holder.description.setText(dishis.get(position).getDescription());
        holder.userName.setText("Edgar");
    }

    @Override
    public int getItemCount() {
        return dishis.size();
    }

    public void removeItem(int position) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DeleteRequestInterface request = retrofit.create(DeleteRequestInterface.class);
        Call<Void> deleteRequest = request.deleteItem(dishis.get(position).getId());
        deleteRequest.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // use response.code, response.headers, etc.
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // handle failure
            }
        });
        dishis.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    class DishViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView description;
        private TextView userName;
        private ImageView image;
        RelativeLayout viewBackground;
        RelativeLayout viewForeground;


        DishViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.dish_card_item, parent, false));
            title = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            userName = itemView.findViewById(R.id.userName);
            image = itemView.findViewById(R.id.thumbnail);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ScrollingActivity.class);
                    intent.putExtra("DISH_ID_KEY", dishis.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
            userName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ScrollingActivity.class);
                    intent.putExtra("USER_ID_KEY", dishis.get(getAdapterPosition()).getOwner());
                    context.startActivity(intent);
                }
            });
        }
    }
}
