package com.jcpallavicino.sample.myrecyclerviewsample.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jcpallavicino.sample.myrecyclerviewsample.R;

import java.util.ArrayList;
import java.util.List;

import com.jcpallavicino.sample.myrecyclerviewsample.Utils.Contact;

import com.jcpallavicino.sample.myrecyclerviewsample.Utils.RestClient;
import com.jcpallavicino.sample.myrecyclerviewsample.Utils.Result;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> image_titles = new ArrayList<>();
    ArrayList<String> image_url = new ArrayList<>();
    ArrayList<Result> resultForAdapter = new ArrayList<>();
    MyAdapter view = new MyAdapter(MainActivity.this, resultForAdapter );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDataUsingRetrofit();


    }

    public void getDataUsingRetrofit() {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.imagegallery);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),1);
        recyclerView.setLayoutManager(layoutManager);

        loadJSON();
//        ArrayList<CreateList> createLists = prepareData();
//        MyAdapter adapter = new MyAdapter(getApplicationContext(), createLists);
//        recyclerView.setAdapter(adapter);
    }

    private void loadJSON(){
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://private-d0cc1-iguanafixtest.apiary-mock.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RestClient restClient = retrofit.create(RestClient.class);
        Call<List<Contact>> call = restClient.getData();

        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                switch (response.code()) {
                    case 200:
                        Log.i("REST SUCCESS", "Responce Code 200");
                        List<Contact> data = response.body();
                        view.notifyDataSetChanged();

                        break;
                    case 401:

                        break;
                    default:

                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                Log.e("REST ERROR", t.toString());
            }
        });
    }

//    @Override
//    public void onFailure(Call<JSONResponse> call, Throwable t) {
//        Log.d("Error",t.getMessage());
//    }
//    });
//        }
//    }


}
