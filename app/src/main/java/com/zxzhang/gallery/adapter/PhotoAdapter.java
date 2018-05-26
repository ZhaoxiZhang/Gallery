package com.zxzhang.gallery.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zxzhang.gallery.R;
import com.zxzhang.gallery.activity.ImageActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张昭锡 on 2018/5/26.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder>{
    private List<String>mPhotoList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView photoView;
        ImageView photoImage;
        public ViewHolder(View view){
            super(view);
            photoView = (CardView)view;
            photoImage = (ImageView)view.findViewById(R.id.photo_image);
        }
    }

    public PhotoAdapter(Context context,List<String>photoList){
        mContext = context;
        mPhotoList = photoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.photo_item,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();

                Intent intent = new Intent(mContext, ImageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position",position);
                bundle.putStringArrayList("imagePathList",(ArrayList)mPhotoList);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(mContext).load(mPhotoList.get(position)).into(holder.photoImage);
    }

    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }

}
