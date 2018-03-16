package com.jcpallavicino.sample.myrecyclerviewsample.activity;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jcpallavicino.sample.myrecyclerviewsample.R;
import com.jcpallavicino.sample.myrecyclerviewsample.utils.Contact;
import com.jcpallavicino.sample.myrecyclerviewsample.utils.ContactDetail;
import com.jcpallavicino.sample.myrecyclerviewsample.utils.DataHandler;
import com.jcpallavicino.sample.myrecyclerviewsample.utils.OnItemClickListener;
import com.jcpallavicino.sample.myrecyclerviewsample.utils.RestClient;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by juan.pallavicino on 15/3/2018.
 */

public class ContactDetailActivity extends AppCompatActivity{

    private DataHandler DataHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_detail);
        String id = getIntent().getStringExtra("id");

        getContactData(id);
        //setContactLayout();

    }

    private void getContactData(String id) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://private-d0cc1-iguanafixtest.apiary-mock.com/contacts/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RestClient restClient = retrofit.create(RestClient.class);
        Call<ContactDetail> call1 = restClient.getContactDetail(id);

        call1.enqueue(new Callback<ContactDetail>() {
            @Override
            public void onResponse(Call<ContactDetail> call2, Response<ContactDetail> response) {
                switch (response.code()) {
                    case 200:
                        Log.i("REST SUCCESS", "Responce Code 200");

                        ContactDetail data = response.body();
                        //Llenar el Data Handler
                        DataHandler = new DataHandler(data);
                        setContactLayout(DataHandler);
                        break;
                    case 401:

                        break;
                    default:
                        Log.i("REST OTHER", response.message());
                        break;
                }
            }

            @Override
            public void onFailure(Call<ContactDetail> call, Throwable t) {
                Log.e("REST ERROR", t.toString());
                Toast.makeText(ContactDetailActivity.this, "ERROR EN EL SERVICIO DETAIL, INTENTE MAS TARDE", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setContactLayout(DataHandler DataHandler){

        ImageView image = (ImageView)findViewById(R.id.imagen_contacto);
        if (DataHandler.largeImageURL != null && !DataHandler.largeImageURL.isEmpty()){
            Picasso.with(ContactDetailActivity.this).load(DataHandler.largeImageURL).fit().into(image);
        }else{
            image.setVisibility(View.GONE);
        }

        TextView nombreContacto = (TextView)findViewById(R.id.nombre_contacto);
        if (DataHandler.name != null && !DataHandler.name.isEmpty()){
            nombreContacto.setText(DataHandler.name);
        }else{
            nombreContacto.setVisibility(View.GONE);
        }

        TextView compania = (TextView)findViewById(R.id.company_contacto);
        if (!DataHandler.companyName.isEmpty() || DataHandler.companyName != "null" || DataHandler.companyName != null){
            compania.setText(DataHandler.companyName);
        }else{
            compania.setVisibility(View.GONE);
        }

        TextView phone_home_label = (TextView)findViewById(R.id.phone_home_label);
        TextView phone_home_data = (TextView)findViewById(R.id.phone_home_data);
        TextView phone_home_legend = (TextView)findViewById(R.id.phone_home_legend);
        if (DataHandler.home != null && !DataHandler.home.isEmpty()){
            phone_home_label.setText("PHONE");
            phone_home_data.setText(DataHandler.home);
            phone_home_legend.setText("Home");
        }else{
            phone_home_label.setVisibility(View.GONE);
            phone_home_data.setVisibility(View.GONE);
            phone_home_legend.setVisibility(View.GONE);
        }

        TextView phone_mobile_label = (TextView)findViewById(R.id.phone_mobile_label);
        TextView phone_mobile_data = (TextView)findViewById(R.id.phone_mobile_data);
        TextView phone_mobile_legend = (TextView)findViewById(R.id.phone_mobile_legend);
        if (DataHandler.mobile != null && !DataHandler.mobile.isEmpty()){
            phone_mobile_label.setText("PHONE");
            phone_mobile_data.setText(DataHandler.mobile);
            phone_mobile_legend.setText("Mobile");
        }else{
            phone_mobile_label.setVisibility(View.GONE);
            phone_mobile_data.setVisibility(View.GONE);
            phone_mobile_legend.setVisibility(View.GONE);
        }

        TextView phone_work_label = (TextView)findViewById(R.id.phone_work_label);
        TextView phone_work_data = (TextView)findViewById(R.id.phone_work_data);
        TextView phone_work_legend = (TextView)findViewById(R.id.phone_work_legend);
        if (DataHandler.work != null && !DataHandler.work.isEmpty()){
            phone_work_label.setText("PHONE");
            phone_work_data.setText(DataHandler.work);
            phone_work_legend.setText("Work");
        }else{
            phone_work_label.setVisibility(View.GONE);
            phone_work_data.setVisibility(View.GONE);
            phone_work_legend.setVisibility(View.GONE);
        }

        TextView address_label = (TextView)findViewById(R.id.address_label);
        TextView address = (TextView)findViewById(R.id.address);
        if(DataHandler.street != null && !DataHandler.street.isEmpty()){
            address_label.setText("ADDRESS");
            address.setText(DataHandler.street);
        }else{
            address_label.setVisibility(View.GONE);
            address.setVisibility(View.GONE);
        }

        TextView city = (TextView)findViewById(R.id.city);
//        if (!DataHandler.city.isEmpty()||!DataHandler.state.isEmpty()||!DataHandler.city.isEmpty()||!DataHandler.zipCode.isEmpty()){
//            String dataAddress = DataHandler.city +", "+ DataHandler.state +" "+ DataHandler.zipCode + ", " + DataHandler.country;
//            city.setText(dataAddress);
//        }else{
            city.setVisibility(View.GONE);
//        }

        TextView birthdate_label = (TextView)findViewById(R.id.birthdate_label);
        TextView birthdate = (TextView)findViewById(R.id.birthdate);
        if (DataHandler.birthdate != null && !DataHandler.birthdate.isEmpty()){
            birthdate_label.setText("BIRTHDAY");
            birthdate.setText(DataHandler.birthdate);
        }else{
            birthdate_label.setVisibility(View.GONE);
            birthdate.setVisibility(View.GONE);
        }

        TextView email_label = (TextView)findViewById(R.id.email_label);
        TextView email = (TextView)findViewById(R.id.email);
//        if (!DataHandler.emailAddress.isEmpty()){
//            email_label.setText("EMAIL");
//            email.setText(DataHandler.emailAddress);
//        }else{
            email_label.setVisibility(View.GONE);
            email.setVisibility(View.GONE);
//        }
    }


}
