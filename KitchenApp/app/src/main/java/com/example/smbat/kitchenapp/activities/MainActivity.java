package com.example.smbat.kitchenapp.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.smbat.kitchenapp.R;
import com.example.smbat.kitchenapp.adapters.DishesListAdapter;
import com.example.smbat.kitchenapp.helpers.RecyclerItemTouchHelper;
import com.example.smbat.kitchenapp.interfaces.PostRequest;
import com.example.smbat.kitchenapp.interfaces.GetRequest;
import com.example.smbat.kitchenapp.objects.Dish;
import com.example.smbat.kitchenapp.objects.Dishes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements
        SwipeRefreshLayout.OnRefreshListener,
        RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    public static final String BASE_URL = "http://192.168.0.102:3000/";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private ArrayList<Dish> dishes;
    private DishesListAdapter dishesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initRecyclerView();
        initSwipeRefreshLayout();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDish();
            }
        });
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.itemsRecyclerView);
        loadJSON();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
    }

    private void loadJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetRequest request = retrofit.create(GetRequest.class);
        Call<Dishes> call = request.getJson();
        call.enqueue(new Callback<Dishes>() {
            @Override
            public void onResponse(Call<Dishes> call, Response<Dishes> response) {
                Dishes jsonResponse = response.body();
                dishes = new ArrayList<>(jsonResponse.getDishes());
                dishesListAdapter = new DishesListAdapter(MainActivity.this, dishes);
                mRecyclerView.setAdapter(dishesListAdapter);
            }

            @Override
            public void onFailure(Call<Dishes> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }

    private void addDish() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostRequest requestInterface = retrofit.create(PostRequest.class);
        requestInterface.savePost("Some Title 1", "Some Description 1", "some_image.jpg", 1).enqueue(new Callback<Dish>() {
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

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dishes.clear();
                dishesListAdapter = null;
                loadJSON();
                mRecyclerView.scrollToPosition(dishes.size() - 1);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof DishesListAdapter.DishViewHolder) {
            // get the removed item name to display it in snack bar
            String name = dishes.get(viewHolder.getAdapterPosition()).getTitle();

            // backup of removed item for undo purpose
            final Dish deletedItem = dishes.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            dishesListAdapter.removeItem(viewHolder.getAdapterPosition());
        }
    }
}
