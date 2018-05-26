package com.zxzhang.gallery.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zxzhang.gallery.R;
import com.zxzhang.gallery.activity.PhotoActivity;
import com.zxzhang.gallery.data.AlbumBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 张昭锡 on 2018/5/25.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder>{
    private static final String TAG = "AlbumAdapter";
    private Context mContext;
    private List<AlbumBean>mAlbumList;
    private HashMap<String,List<String>>mAlbumMap;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView albumView;
        ImageView albumImage;
        TextView albumName;
        public ViewHolder(View view){
            super(view);
            albumView = (CardView)view;
            albumImage = (ImageView)view.findViewById(R.id.album_image);
            albumName = (TextView)view.findViewById(R.id.album_name);
        }
    }

    public AlbumAdapter(List<AlbumBean>albumList, HashMap<String,List<String>>albumMap){
        mAlbumList = albumList;
        mAlbumMap = albumMap;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.album_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);

        holder.albumView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                List<String>photoPathList = mAlbumMap.get(mAlbumList.get(position).getAlbumFolderName());
                Log.d(TAG, "onClick: AlbumAdapter " + photoPathList.size());
                Intent intent = new Intent(mContext, PhotoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("photoPathList",(ArrayList)photoPathList);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AlbumBean albumBean = mAlbumList.get(position);
        holder.albumName.setText(albumBean.getAlbumFolderName());
        Glide.with(mContext)
                .load(albumBean.getAlbumFolderPath())
                .into(holder.albumImage);
    }

    @Override
    public int getItemCount() {
        return mAlbumList.size();
    }
}
