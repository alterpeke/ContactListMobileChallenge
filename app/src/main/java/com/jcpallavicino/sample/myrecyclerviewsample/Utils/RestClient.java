package com.jcpallavicino.sample.myrecyclerviewsample.utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by juan.pallavicino on 25/2/2018.
 */

public interface RestClient {
    @GET("contacts")
    Call<List<Contact>> getData();

    @GET("/contacts/{id}")
    Call<ContactDetail> getContactDetail(@Path("id") String id);
}
