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
import com.jcpallavicino.sample.myrecyclerviewsample.Utils.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juan.pallavicino on 26/9/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Result> list;
    private ArrayList<CreateList> galleryList;
    private Context context;

    public MyAdapter(Context context, ArrayList<Result> galleryList) {
//        this.galleryList = galleryList;
//        this.context = context;
    }

    public void swap(List<Result> newList) {
        list.clear();
        list.addAll(newList);
        notifyDataSetChanged();

    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyAdapter.ViewHolder viewHolder, int i) {

        Picasso.with(context)
         .load(galleryList.get(i)
         .getImage_ID())
         .into(viewHolder.img);

        viewHolder.title.setText(galleryList.get(i).getImage_title());
        viewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Image Details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private ImageView img;

        public ViewHolder(View view) {
            super(view);

            title = (TextView)view.findViewById(R.id.title);
            img = (ImageView) view.findViewById(R.id.img);

        }


    }
}
