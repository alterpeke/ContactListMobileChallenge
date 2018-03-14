package com.jcpallavicino.sample.myrecyclerviewsample.Activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jcpallavicino.sample.myrecyclerviewsample.R;
import com.jcpallavicino.sample.myrecyclerviewsample.Utils.Contact;
import com.jcpallavicino.sample.myrecyclerviewsample.Utils.Phone;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juan.pallavicino on 26/9/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Contact> list;
    private ArrayList<Contact> galleryList;
    private Context context;

    public MyAdapter(Context context, ArrayList<Contact> galleryList) {
        this.galleryList = galleryList;
        this.context = context;
    }

    public void swap(List<Contact> newList) {
        list.clear();
        list.addAll(newList);
        notifyDataSetChanged();

    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyAdapter.ViewHolder viewHolder, int i) {

        Picasso.with(this.context)
                .load(galleryList.get(i).getThumb())
                .into(viewHolder.img);

        viewHolder.name.setText(galleryList.get(i).getLastName()+" "+galleryList.get(i).getFirstName());

        List<Phone> fonos = galleryList.get(i).getPhones();

        for (int j=0; j<fonos.size(); j++) {
            if ((fonos.get(j).getType().contains("Home")) && !(fonos.get(j).getNumber()==null)) {
                viewHolder.phone.setText(fonos.get(j).getNumber());
            }
        }

    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name, phone;
        private ImageView img;

        public ViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.album_name);
            phone = (TextView) view.findViewById(R.id.artist_name);
            img = (ImageView) view.findViewById(R.id.img);

        }


    }
}
