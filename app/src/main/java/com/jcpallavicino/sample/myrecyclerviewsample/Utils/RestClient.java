package com.jcpallavicino.sample.myrecyclerviewsample.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by juan.pallavicino on 25/2/2018.
 */

public interface RestClient {
    @GET("contacts")
    Call<List<Contact>> getData();

}