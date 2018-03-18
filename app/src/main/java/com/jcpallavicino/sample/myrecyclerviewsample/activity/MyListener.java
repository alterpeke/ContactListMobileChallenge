package com.jcpallavicino.sample.myrecyclerviewsample.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jcpallavicino.sample.myrecyclerviewsample.R;
import com.jcpallavicino.sample.myrecyclerviewsample.utils.Contact;
import com.jcpallavicino.sample.myrecyclerviewsample.utils.OnItemClickListener;
import com.jcpallavicino.sample.myrecyclerviewsample.utils.RestClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by juan.pallavicino on 18/3/2018.
 */

public class MyListener {

    public interface MyInterfaceListener{
        // These methods are the different events and
        // need to pass relevant arguments related to the event triggered
        public void onObjectReady(String title);
        // or when data has been loaded
        public void onDataLoaded(ArrayList<Contact> data);
    }

    private MyInterfaceListener listener;

    public MyListener(){
        this.listener = null;

    }

    public void setListener(MyInterfaceListener listener) {
        this.listener = listener;
        loadJSON();
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

                        ArrayList<Contact> data = (ArrayList<Contact>) response.body();
                        listener.onDataLoaded(data);

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
                //Toast.makeText(MainActivity.this, "ERROR EN EL SERVICIO, INTENTE MAS TARDE", Toast.LENGTH_LONG).show();
            }
        });
    }
}
