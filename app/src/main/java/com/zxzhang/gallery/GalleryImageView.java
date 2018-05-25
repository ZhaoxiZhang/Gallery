package com.zxzhang.gallery;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by 张昭锡 on 2018/5/25.
 */

public class GalleryImageView extends ImageView{
    private OnMeasureListener onMeasureListener;

    public GalleryImageView(Context context) {
        super(context);
    }

    public GalleryImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GalleryImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GalleryImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setOnMeasureListener(OnMeasureListener onMeasureListener){
        this.onMeasureListener = onMeasureListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (onMeasureListener != null){
            onMeasureListener.onMeasureSize(getMeasuredWidth(),getMeasuredHeight());
        }
    }



    public interface OnMeasureListener{
        public void onMeasureSize(int width,int height);
    }
}
