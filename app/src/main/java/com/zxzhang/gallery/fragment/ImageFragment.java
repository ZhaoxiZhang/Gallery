package com.zxzhang.gallery.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.zxzhang.gallery.R;

import static android.content.ContentValues.TAG;

public class ImageFragment extends Fragment {
    private String mImagePath;
    private PhotoView mPvImage;


    public static ImageFragment newInstance(String imagePath){
        ImageFragment imageFragment = new ImageFragment();

        Bundle bundle = new Bundle();
        bundle.putString("imagePath",imagePath);
        imageFragment.setArguments(bundle);

        return imageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImagePath = getArguments().getString("imagePath");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_image, container, false);
        mPvImage = (PhotoView)view.findViewById(R.id.image_view);

        Glide.with(getContext()).load(mImagePath)
                .into(mPvImage);

        return view;
    }

}
