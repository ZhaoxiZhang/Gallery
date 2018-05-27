package com.zxzhang.gallery.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zxzhang.gallery.R;

public class ImageFragment extends Fragment {
    private String mImagePath;
    private ImageView mIvImage;

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
        mIvImage = (ImageView)view.findViewById(R.id.image);

        Glide.with(getContext()).load(mImagePath)
                .into(mIvImage);

        return view;
    }

}
