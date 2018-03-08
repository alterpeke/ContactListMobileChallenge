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

import com.jcpallavicino.sample.myrecyclerviewsample.Utils.Contact;

import com.jcpallavicino.sample.myrecyclerviewsample.Utils.RestClient;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> image_titles = new ArrayList<>();
    ArrayList<String> image_url = new ArrayList<>();


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
        Call<Contact> call = restClient.getData();

        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                switch (response.code()) {
                    case 200:
                        Contact data = response.body();
                        //view.notifyDataSetChanged(data.getResults());
                        break;
                    case 401:

                        break;
                    default:

                        break;
                }
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {
                Log.e("error", t.toString());
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
