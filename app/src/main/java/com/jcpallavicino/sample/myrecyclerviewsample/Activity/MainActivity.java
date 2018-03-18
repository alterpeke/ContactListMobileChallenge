package com.jcpallavicino.sample.myrecyclerviewsample.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jcpallavicino.sample.myrecyclerviewsample.R;

import java.util.ArrayList;
import java.util.List;

import com.jcpallavicino.sample.myrecyclerviewsample.utils.Contact;

import com.jcpallavicino.sample.myrecyclerviewsample.utils.OnItemClickListener;
import com.jcpallavicino.sample.myrecyclerviewsample.utils.RestClient;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ArrayList<Contact> resultForAdapter = new ArrayList<>();
    MyAdapter view = new MyAdapter(MainActivity.this, resultForAdapter);
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = (ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.VISIBLE);
        MyListener listener = new MyListener();
        listener.setListener(new MyListener.MyInterfaceListener() {
            @Override
            public void onObjectReady(String title) {

            }

            @Override
            public void onDataLoaded(ArrayList<Contact> data) {
                getDataUsingRetrofit(data);
            }
        });

        //getDataUsingRetrofit();
        //spinner.setVisibility(View.GONE);


    }

    public void getDataUsingRetrofit(ArrayList<Contact> data) {

        //loadJSON();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.imagegallery);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),1);
        recyclerView.setLayoutManager(layoutManager);
        resultForAdapter = data;
        MyAdapter view = new MyAdapter(MainActivity.this, resultForAdapter );
        view.setOnClick(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this, "Item seleccionado: "+position, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getBaseContext(), ContactDetailActivity.class);
                intent.putExtra("id", resultForAdapter.get(position).getUserId());
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(view);
        spinner.setVisibility(View.GONE);
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
                        //List<Contact> data = response.body();
                        ArrayList<Contact> data = (ArrayList<Contact>) response.body();

                        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.imagegallery);
                        recyclerView.setHasFixedSize(true);

                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),1);
                        recyclerView.setLayoutManager(layoutManager);
                        resultForAdapter = data;
                        MyAdapter view = new MyAdapter(MainActivity.this, resultForAdapter );
                            view.setOnClick(new OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    Toast.makeText(MainActivity.this, "Item seleccionado: "+position, Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getBaseContext(), ContactDetailActivity.class);
                                    intent.putExtra("id", resultForAdapter.get(position).getUserId());
                                    startActivity(intent);
                                }
                            });

                        recyclerView.setAdapter(view);
                        spinner.setVisibility(View.GONE);
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
                Toast.makeText(MainActivity.this, "ERROR EN EL SERVICIO, INTENTE MAS TARDE", Toast.LENGTH_LONG).show();
            }
        });
    }


}
