package com.zxzhang.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by 张昭锡 on 2018/5/25.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder>{
    private List<ImageBean>mImageList;
    private Point mPoint = new Point(0,0);
    private RecyclerView mRvGallery;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder{
        GalleryImageView galleryImage;
        TextView galleryTextTitle;
        TextView galleryTextCount;

        public ViewHolder(View itemView) {
            super(itemView);
            galleryImage = (GalleryImageView)itemView.findViewById(R.id.main_galleryimage);
            galleryTextTitle = (TextView)itemView.findViewById(R.id.main_gallerytitle);
            galleryTextCount = (TextView)itemView.findViewById(R.id.main_gallerycount);
        }
    }

    public GalleryAdapter(List<ImageBean>imageList,RecyclerView RvView){
        mImageList = imageList;
        mRvGallery = RvView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ImageBean imageBean = mImageList.get(position);
        holder.galleryTextTitle.setText(imageBean.getFolderPath());
        //holder.galleryTextCount.setText(imageBean.getImagesCnt());
        Glide.with(mContext).load(imageBean.getTopImagePath()).into(holder.galleryImage);
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

}
