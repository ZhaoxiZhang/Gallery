package com.zxzhang.gallery.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.zxzhang.gallery.R;
import com.zxzhang.gallery.fragment.ImageFragment;

import java.util.List;

public class ImageActivity extends AppCompatActivity {
    private static final String TAG = "ImageActivity";
    private Toolbar mToolbar;
    private Intent mIntent;
    private ViewPager mVpImage;
    private List<String>mImagePathList;
    private int imagePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        mVpImage = (ViewPager)findViewById(R.id.vp_image);
        initToolbar();

        mIntent = getIntent();
        mImagePathList = mIntent.getStringArrayListExtra("imagePathList");
        imagePosition = mIntent.getIntExtra("position",0);

        mVpImage.setAdapter(new ImageAdapter((getSupportFragmentManager())));

        mVpImage.setCurrentItem(imagePosition);
        mVpImage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mToolbar.setTitle((position + 1) + " " + "/" + " " + mImagePathList.size());
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initToolbar(){
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return true;
    }

    private class ImageAdapter extends FragmentStatePagerAdapter{

        public ImageAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return ImageFragment.newInstance(mImagePathList.get(position));
        }

        @Override
        public int getCount() {
            return mImagePathList.size();
        }
    }
}
